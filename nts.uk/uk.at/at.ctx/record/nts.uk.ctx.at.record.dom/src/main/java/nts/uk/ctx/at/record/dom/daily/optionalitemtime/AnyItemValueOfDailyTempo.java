package nts.uk.ctx.at.record.dom.daily.optionalitemtime;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;

@Getter
public class AnyItemValueOfDailyTempo {

	// 社員ID
	private String employeeId;
	// 年月日
	private GeneralDate ymd;
	
	@Setter
	private List<AnyItemValue> items;

	public AnyItemValueOfDailyTempo(String employeeId, GeneralDate ymd, List<AnyItemValue> items) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.items = items;
	}
	
}
