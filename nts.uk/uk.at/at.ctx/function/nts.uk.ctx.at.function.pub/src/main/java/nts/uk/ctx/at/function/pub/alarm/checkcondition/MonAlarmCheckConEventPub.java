package nts.uk.ctx.at.function.pub.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.event.DomainEvent;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ExtraResultMonthlyDomainEventPubDto;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MonAlarmCheckConEventPub extends DomainEvent {
	/** ID */
	private String monAlarmCheckConID;
	
	private boolean checkUpdate;
	
	private boolean checkAdd;
	
	private boolean checkDelete;

	private List<ExtraResultMonthlyDomainEventPubDto> listExtraResultMonthly = new ArrayList<>();

	public MonAlarmCheckConEventPub(String monAlarmCheckConID, boolean checkUpdate, boolean checkAdd, boolean checkDelete, List<ExtraResultMonthlyDomainEventPubDto> listExtraResultMonthly) {
		super();
		this.monAlarmCheckConID = monAlarmCheckConID;
		this.checkUpdate = checkUpdate;
		this.checkAdd = checkAdd;
		this.checkDelete = checkDelete;
		this.listExtraResultMonthly = listExtraResultMonthly;
	}

	
	
	
	
}
