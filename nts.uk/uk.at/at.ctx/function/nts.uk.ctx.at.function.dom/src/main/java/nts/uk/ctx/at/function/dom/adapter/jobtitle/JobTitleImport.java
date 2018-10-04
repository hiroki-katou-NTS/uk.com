package nts.uk.ctx.at.function.dom.adapter.jobtitle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class JobTitleImport {

	/** The company id. */
	private String companyId;

	/** The job title id. */
	private String jobTitleId;

	/** The job title code. */
	private String jobTitleCode;

	/** The job title name. */
	private String jobTitleName;

	/** The sequence code. */
	private String sequenceCode;

	/** The start date. */
	private GeneralDate startDate;

	/** The end date. */
	private GeneralDate endDate;

	/** The is manager. #100055 */
	private boolean isManager;
}
