package nts.uk.ctx.at.shared.app.command.remainingnumber.paymana;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class UpdateSubstitutionOfHDManaDataCommand {

	// 振休データID
	private String subOfHDID;

	private String cid;

	// 社員ID
	private String sID;

	private String employeeId;

	private GeneralDate exprirationDate;

	// 年月日
	private GeneralDate dayoffDate;

	// 必要日数
	private Double requiredDays;

	// 未相殺日数
	private Double remainDays;
	
	private int closureId;

}
