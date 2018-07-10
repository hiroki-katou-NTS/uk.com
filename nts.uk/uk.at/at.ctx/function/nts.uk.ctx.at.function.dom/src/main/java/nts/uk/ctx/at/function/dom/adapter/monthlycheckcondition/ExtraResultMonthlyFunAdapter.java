package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition;

import java.util.List;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;


public interface ExtraResultMonthlyFunAdapter {
	List<ExtraResultMonthlyDomainEventDto> getListExtraResultMonByListEralID(List<String> listEralCheckID);
}
