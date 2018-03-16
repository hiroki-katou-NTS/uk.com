package nts.uk.pub.spr.approvalroot.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class PersonInfoSpr {
	private String pid;

	private String businessName;

	private GeneralDate entryDate;

	private int gender;

	private GeneralDate birthDay;

	/** The employee id. */
	private String employeeId;

	/** The employee code. */
	private String employeeCode;

	private GeneralDate retiredDate;
}
