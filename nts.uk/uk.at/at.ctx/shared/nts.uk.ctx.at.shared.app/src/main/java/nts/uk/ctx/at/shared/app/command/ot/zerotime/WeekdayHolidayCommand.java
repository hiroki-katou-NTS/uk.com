package nts.uk.ctx.at.shared.app.command.ot.zerotime;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.WeekdayHoliday;

/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class WeekdayHolidayCommand {
	/** 会社ID */
	private String companyId;

	/** 変更前の残業枠NO */
	private BigDecimal overworkFrameNo;

	/** 変更後の法定内休出NO */
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
