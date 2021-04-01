package de.timo_reymann.mjml_support.inspection

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.htmlInspections.HtmlLocalInspectionTool
import com.intellij.psi.xml.XmlTag
import de.timo_reymann.mjml_support.model.MjmlTagProvider

class InvalidParentTagInspection : HtmlLocalInspectionTool() {
    override fun checkTag(tag: XmlTag, holder: ProblemsHolder, isOnTheFly: Boolean) {
        val mjmlTag = MjmlTagProvider.getByXmlElement(tag) ?: return
        if (tag.parent is XmlTag) {
            val parentTagName = (tag.parent as XmlTag).name
            if (!mjmlTag.allowedParentTags.contains(parentTagName)) {
                holder.registerProblem(
                    tag,
                    "${mjmlTag.tagName} cannot be used inside ${parentTagName}, only inside ${mjmlTag.allowedParentTags.joinToString(", ")}",
                    ProblemHighlightType.WARNING
                )
            }
        }
    }
}
