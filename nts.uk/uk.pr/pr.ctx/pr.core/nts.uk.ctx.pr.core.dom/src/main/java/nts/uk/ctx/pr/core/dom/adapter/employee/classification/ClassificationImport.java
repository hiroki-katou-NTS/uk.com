package nts.uk.ctx.pr.core.dom.adapter.employee.classification;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
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
