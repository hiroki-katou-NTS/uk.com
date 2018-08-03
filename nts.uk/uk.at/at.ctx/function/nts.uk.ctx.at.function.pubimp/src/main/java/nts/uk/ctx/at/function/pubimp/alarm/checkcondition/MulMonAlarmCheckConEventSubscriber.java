package nts.uk.ctx.at.function.pubimp.alarm.checkcondition;

import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErAlAtdItemConAdapterDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.MulMonAlarmCondEvent;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.MulMonAlarmCondEventPub;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.ErAlAtdItemConAdapterPubDto;
import nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto.MulMonCheckCondDomainEventPubDto;

@Stateless
public class MulMonAlarmCheckConEventSubscriber implements DomainEventSubscriber<MulMonAlarmCondEvent> {

	@Override
	public Class<MulMonAlarmCondEvent> subscribedToEventType() {
		return MulMonAlarmCondEvent.class;
	}

	@Override
	public void handle(MulMonAlarmCondEvent domainEvent) {
		MulMonAlarmCondEventPub p = convertToMulMonAlarmCheckConEvent(domainEvent);
		p.toBePublished();
	}
	
	private MulMonAlarmCondEventPub convertToMulMonAlarmCheckConEvent(MulMonAlarmCondEvent export) {
		return new MulMonAlarmCondEventPub(
				export.getMulMonAlarmCondID(),
				export.isCheckUpdate(),
				export.isCheckAdd(),
				export.isCheckDelete(),
				export.getListMulMonCheckConds().stream()
				.map(c->convertToMulMonCheckCondDomainEventDto(c)).collect(Collectors.toList()),
				export.getListEralCheckIDOld()
				);
	}
	
	private MulMonCheckCondDomainEventPubDto convertToMulMonCheckCondDomainEventDto(MulMonCheckCondDomainEventDto export) {
		return new MulMonCheckCondDomainEventPubDto(
				export.getErrorAlarmCheckID(),
				export.getNameAlarmMulMon(),
				export.isUseAtr(),
				export.getTypeCheckItem(),
				export.isMessageBold(),
				export.getMessageColor(),
				export.getDisplayMessage(),
				export.getErAlAtdItem() == null?null: convertToErAlAtdItemCon(export.getErAlAtdItem()),
				export.getContinuousMonths(),
				export.getTimes(),
				export.getCompareOperator());
	}
	
	private ErAlAtdItemConAdapterPubDto convertToErAlAtdItemCon(ErAlAtdItemConAdapterDto export) {
		return new ErAlAtdItemConAdapterPubDto(export.getTargetNO(), export.getConditionAtr(), export.isUseAtr(),
				export.getUncountableAtdItem(), export.getCountableAddAtdItems(), export.getCountableSubAtdItems(),
				export.getConditionType(), export.getCompareOperator(), export.getSingleAtdItem(),
				export.getCompareStartValue(), export.getCompareEndValue(), export.getInputCheckCondition());
	}
}
