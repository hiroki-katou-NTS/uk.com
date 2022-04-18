package nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclareHolidayWorkFrameDto {
	/** 休出 */
	private HdwkFrameEachHdAtrDto holidayWork;
	/** 休出深夜 */
	private HdwkFrameEachHdAtrDto holidayWorkMn;
}
