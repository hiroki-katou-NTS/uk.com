package nts.uk.ctx.at.function.dom.adapter.multimonth;

import java.util.List;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;


public interface MultiMonthFucAdapter {
	List<MulMonCheckCondDomainEventDto> getListMultiMonCondByListEralID(List<String> listEralCheckID);
}
