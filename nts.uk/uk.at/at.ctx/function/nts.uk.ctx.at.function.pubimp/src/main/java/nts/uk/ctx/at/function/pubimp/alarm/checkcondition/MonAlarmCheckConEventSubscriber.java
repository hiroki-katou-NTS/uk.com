package nts.uk.ctx.at.function.pubimp.alarm.checkcondition;

import java.util.stream.Collectors;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.AttendanceItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErAlAtdItemConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErAlConAttendanceItemAdapterDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckConEvent;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.MonAlarmCheckConEventPub;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.AttendanceItemConAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ErAlAtdItemConAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ErAlConAttendanceItemAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ExtraResultMonthlyDomainEventPubDto;

public class MonAlarmCheckConEventSubscriber implements DomainEventSubscriber<MonAlarmCheckConEvent> {

	@Override
	public Class<MonAlarmCheckConEvent> subscribedToEventType() {
		return MonAlarmCheckConEvent.class;
	}

	@Override
	public void handle(MonAlarmCheckConEvent domainEvent) {
		MonAlarmCheckConEventPub p = convertToMonAlarmCheckConEvent(domainEvent);
		p.toBePublished();
	}
	
	private MonAlarmCheckConEventPub convertToMonAlarmCheckConEvent(MonAlarmCheckConEvent export) {
		return new MonAlarmCheckConEventPub(
				export.getMonAlarmCheckConID(),
				export.isCheckUpdate(),
				export.isCheckAdd(),
				export.isCheckDelete(),
				export.getListExtraResultMonthly().stream()
				.map(c->convertToExtraResultMonthlyDomainEventDto(c)).collect(Collectors.toList())
				);
	}
	
	private ExtraResultMonthlyDomainEventPubDto convertToExtraResultMonthlyDomainEventDto(ExtraResultMonthlyDomainEventDto export) {
		return new ExtraResultMonthlyDomainEventPubDto(
				export.getErrorAlarmCheckID(),
				export.getSortBy(),
				export.getNameAlarmExtraCon(),
				export.isUseAtr(),
				export.getTypeCheckItem(),
				export.isMessageBold(),
				export.getMessageColor(),
				export.getDisplayMessage(),
				convertToAttendanceItemCon(export.getCheckConMonthly())
				);
	}
	
	
	private AttendanceItemConAdapterPubDto convertToAttendanceItemCon (AttendanceItemConAdapterDto export) {
		return new AttendanceItemConAdapterPubDto(
				export.getOperatorBetweenGroups(),
				convertToErAlConAttendanceItem(export.getGroup1()),
				convertToErAlConAttendanceItem(export.getGroup2()),
				export.isGroup2UseAtr()
				);
	}
	
	private ErAlConAttendanceItemAdapterPubDto convertToErAlConAttendanceItem(ErAlConAttendanceItemAdapterDto export) {
		return new  ErAlConAttendanceItemAdapterPubDto(
				export.getAtdItemConGroupId(),
				export.getConditionOperator(),
				export.getLstErAlAtdItemCon().stream().map(c->convertToErAlAtdItemCon(c)).collect(Collectors.toList())
				);
	}
	private ErAlAtdItemConAdapterPubDto convertToErAlAtdItemCon(ErAlAtdItemConAdapterDto export) {
		return new ErAlAtdItemConAdapterPubDto(
				export.getTargetNO(),
				export.getConditionAtr(),
				export.isUseAtr(),
				export.getUncountableAtdItem(),
				export.getCountableAddAtdItems(),
				export.getCountableSubAtdItems(),
				export.getConditionType(),
				export.getCompareOperator(),
				export.getSingleAtdItem(),
				export.getCompareStartValue(),
				export.getCompareEndValue()
				);
	}
	

}
