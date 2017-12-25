package nts.uk.ctx.at.shared.app.command.calculation.holiday.time;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.WeekdayHoliday;

@Data
@AllArgsConstructor
public class WeekdayHolidayCommand {
	/** 会社ID */
	private String companyId;

	/** 変更前の残業枠NO */
	private int overworkFrameNo;

	/** 変更後の残業枠NO */
	private int weekdayNo;

	/** 変更後の法定外休出NO */
	private int excessHolidayNo;

	/** 変更後の祝日休出NO */
	private int excessSphdNo;

	public WeekdayHoliday toDomain(String companyId) {
		return WeekdayHoliday.createFromJavaType(companyId, this.overworkFrameNo, this.weekdayNo, this.excessHolidayNo,
				this.excessSphdNo);
	}
}
