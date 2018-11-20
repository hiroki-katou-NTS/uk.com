package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ClassificationImport {
    /** The classification code. */
    private String classificationCode; // 分類コード

    /** The classification name. */
    private String classificationName; // 分類名称
}
