package nts.uk.ctx.at.function.pub.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.event.DomainEvent;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.MulMonCheckCondDomainEventPubDto;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MulMonAlarmCondEventPub extends DomainEvent {
	/** ID */
	private String mulMonAlarmCondID;
	
	private boolean checkUpdate;
	
	private boolean checkAdd;
	
	private boolean checkDelete;
	
	private List<MulMonCheckCondDomainEventPubDto> listMulMonCheckConds = new ArrayList<>();
	
	private List<String> listEralCheckIDOld = new ArrayList<>();

	public MulMonAlarmCondEventPub(String mulMonAlarmCondID, boolean checkUpdate, boolean checkAdd, boolean checkDelete,
			List<MulMonCheckCondDomainEventPubDto> listMulMonCheckConds, List<String> listEralCheckIDOld) {
		super();
		this.mulMonAlarmCondID = mulMonAlarmCondID;
		this.checkUpdate = checkUpdate;
		this.checkAdd = checkAdd;
		this.checkDelete = checkDelete;
		this.listMulMonCheckConds = listMulMonCheckConds;
		this.listEralCheckIDOld = listEralCheckIDOld;
	}

}
