package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.Data;
@Data
public class GrantPeriodicCommand {
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private String specialHolidayCode;
	
	/** 付与日数定期 */
	private int grantDay;
	
	/** 固定付与日数 */
	private int splitAcquisition;


	/** 付与日数定期方法 */
	private int grantPeriodicMethod;
}
