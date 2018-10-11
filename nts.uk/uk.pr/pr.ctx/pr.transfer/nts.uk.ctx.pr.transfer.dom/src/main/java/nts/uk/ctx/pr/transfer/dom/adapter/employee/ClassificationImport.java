package nts.uk.ctx.pr.transfer.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 所属分類
 */
@Value
@AllArgsConstructor
public class ClassificationImport {

    /**
     * The classification code.
     */
    private String classificationCode; // 分類コード

    /**
     * The classification name.
     */
    private String classificationName; // 分類名称
}
