package nts.uk.ctx.at.shared.app.command.calculation.holiday.time;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayHolidayAtten;

@Data
@AllArgsConstructor
public class OverdayHolidayAttenCommand {
	
	/** 会社ID */
	private String companyId;

	/** 変更前の休出枠NO */
	private int holidayWorkFrameNo;

	/** 変更後の残業枠NO */
	private int overWorkNo;
	
	public OverdayHolidayAtten toDomain(String companyId) {
		return OverdayHolidayAtten.createFromJavaType(companyId, this.holidayWorkFrameNo, this.overWorkNo);
	}
}
