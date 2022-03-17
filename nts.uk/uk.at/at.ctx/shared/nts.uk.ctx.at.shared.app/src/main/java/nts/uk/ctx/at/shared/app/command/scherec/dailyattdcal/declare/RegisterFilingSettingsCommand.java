package nts.uk.ctx.at.shared.app.command.scherec.dailyattdcal.declare;

import lombok.Getter;
import nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare.DeclareHolidayWorkFrameDto;
import nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare.DeclareOvertimeFrameDto;

@Getter
public class RegisterFilingSettingsCommand {
	/** 会社ID */
	private String companyId;
	/** 申告利用区分 */
	private Integer usageAtr;
	/** 枠設定 */
	private Integer frameSet;
	/** 深夜時間自動計算 */
	private Integer midnightAutoCalc;
	/** 残業枠 */
	private DeclareOvertimeFrameDto overtimeFrame;
	/** 休出枠 */
	private DeclareHolidayWorkFrameDto holidayWorkFrame;
}
