package nts.uk.ctx.at.shared.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分類
 */
@AllArgsConstructor
@Getter
public class ClassificationImport {

    /** The company id. */
    private String companyId;

    /** The classification code. */
    private String classificationCode;

    /** The classification name. */
    private String classificationName;

    /** The classification memo. */
    private String memo;
}
