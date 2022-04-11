package nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclareSetDto {
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
