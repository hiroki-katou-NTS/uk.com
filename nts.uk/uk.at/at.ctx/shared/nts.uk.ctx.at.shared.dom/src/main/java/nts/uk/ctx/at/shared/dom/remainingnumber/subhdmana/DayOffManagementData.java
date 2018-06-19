package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayOffManagementData {
	
	private List<DaysOffMana> daysOffMana;
	private String employeeId;
	private String leaveId;
	private String numberDayParam;
	
	public DayOffManagementData(List<DaysOffMana> daysOffMana, String employeeId, String leaveId, String numberDayParam) {
		super();
		this.daysOffMana = daysOffMana;
		this.employeeId = employeeId;
		this.leaveId = leaveId;
		this.numberDayParam = numberDayParam;
	}
	
	
	
}
