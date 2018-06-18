package nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.event.DomainEvent;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MonAlarmCheckConEvent extends DomainEvent {
	/** ID */
	private String monAlarmCheckConID;
	
	private boolean checkUpdate;
	
	private boolean checkAdd;
	
	private boolean checkDelete;
	
	private List<ExtraResultMonthlyDomainEventDto> listExtraResultMonthly = new ArrayList<>();
	
	private List<String> listEralCheckIDOld = new ArrayList<>();

	public MonAlarmCheckConEvent(String monAlarmCheckConID, boolean checkUpdate, boolean checkAdd, boolean checkDelete, List<ExtraResultMonthlyDomainEventDto> listExtraResultMonthly, List<String> listEralCheckIDOld) {
		super();
		this.monAlarmCheckConID = monAlarmCheckConID;
		this.checkUpdate = checkUpdate;
		this.checkAdd = checkAdd;
		this.checkDelete = checkDelete;
		this.listExtraResultMonthly = listExtraResultMonthly;
		this.listEralCheckIDOld = listEralCheckIDOld;
	}

}
