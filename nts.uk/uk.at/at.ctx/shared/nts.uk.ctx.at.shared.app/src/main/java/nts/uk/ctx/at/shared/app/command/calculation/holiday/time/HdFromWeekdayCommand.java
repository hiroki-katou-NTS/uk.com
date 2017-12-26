package nts.uk.ctx.at.shared.app.command.calculation.holiday.time;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.HdFromWeekday;

@Data
@AllArgsConstructor
public class HdFromWeekdayCommand {
	
	/** 会社ID */
	private String companyId;

	/** 変更前の休出枠NO */
	private int holidayWorkFrameNo;

	/** 変更後の残業枠NO */
	private BigDecimal overWorkNo;
	
	public HdFromWeekday toDomain(String companyId) {
		return HdFromWeekday.createFromJavaType(companyId, this.holidayWorkFrameNo, this.overWorkNo);
	}
}
