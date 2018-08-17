package nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.event.DomainEvent;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MulMonAlarmCondEvent extends DomainEvent {
	/** ID */
	private String mulMonAlarmCondID;
	
	private boolean checkUpdate;
	
	private boolean checkAdd;
	
	private boolean checkDelete;
	
	private List<MulMonCheckCondDomainEventDto> listMulMonCheckConds = new ArrayList<>();
	
	private List<String> listEralCheckIDOld = new ArrayList<>();

	public MulMonAlarmCondEvent(String mulMonAlarmCondID, boolean checkUpdate, boolean checkAdd, boolean checkDelete,
			List<MulMonCheckCondDomainEventDto> listMulMonCheckConds, List<String> listEralCheckIDOld) {
		super();
		this.mulMonAlarmCondID = mulMonAlarmCondID;
		this.checkUpdate = checkUpdate;
		this.checkAdd = checkAdd;
		this.checkDelete = checkDelete;
		this.listMulMonCheckConds = listMulMonCheckConds;
		this.listEralCheckIDOld = listEralCheckIDOld;
	}

}
