package nts.uk.ctx.at.shared.app.command.calculation.holiday.time;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalcHoliday;

@Data
@AllArgsConstructor
public class OverdayCalcHolidayCommand {
	/** 会社ID */
	private String companyId;

	/**変更前の休出枠NO*/
	private int holidayWorkFrameNo;
	
	/** 変更後の法定内休出NO*/
	private int calcOverDayEnd;
	
	/** 変更後の法定外休出NO */
	private int statutoryHd;
	
	/** 変更後の祝日休出NO */
	private int excessHd;
	
	public OverdayCalcHoliday toDomain(String companyId) {
		return OverdayCalcHoliday.createFromJavaType(companyId, this.holidayWorkFrameNo, this.calcOverDayEnd, this.statutoryHd, this.excessHd);
	}
}
