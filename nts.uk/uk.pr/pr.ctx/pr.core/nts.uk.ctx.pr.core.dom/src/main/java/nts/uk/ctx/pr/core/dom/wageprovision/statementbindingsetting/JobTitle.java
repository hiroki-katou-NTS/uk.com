package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobTitle {

    /** The company id. */
    private String companyId;

    /** The job title id. */
    private String jobTitleId;

    /** The job title code. */
    private String jobTitleCode;

    /** The job title name. */
    private String jobTitleName;

}
