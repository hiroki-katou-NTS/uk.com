package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import java.util.List;

import lombok.Getter;

@Getter
public class DayOffManagementData {
	
	private List<DaysOffMana> daysOffMana;
	private String employeeId;
	private String leaveId;
	
	public DayOffManagementData(List<DaysOffMana> daysOffMana, String employeeId, String leaveId) {
		super();
		this.daysOffMana = daysOffMana;
		this.employeeId = employeeId;
		this.leaveId = leaveId;
	}
	
	
	
}
