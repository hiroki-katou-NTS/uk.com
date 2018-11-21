package nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
