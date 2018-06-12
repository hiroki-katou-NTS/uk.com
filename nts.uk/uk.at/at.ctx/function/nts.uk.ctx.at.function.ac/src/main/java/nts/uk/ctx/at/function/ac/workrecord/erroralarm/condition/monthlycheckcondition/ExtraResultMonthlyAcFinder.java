package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.WorkRecordExtraConAcFinder;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.ExtraResultMonthlyFunAdapter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.ExtraResultMonthlyPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.ExtraResultMonthlyPubEx;

@Stateless
public class ExtraResultMonthlyAcFinder implements ExtraResultMonthlyFunAdapter {

	@Inject
	private ExtraResultMonthlyPub repo;

	@Override
	public List<ExtraResultMonthlyDomainEventDto> getListExtraResultMonByListEralID(List<String> listEralCheckID) {
		List<ExtraResultMonthlyDomainEventDto> data = repo.getListExtraResultMonByListEralID(listEralCheckID)
				.stream().map(c -> convertToExport(c)).collect(Collectors.toList());
		return data;
	}

	private ExtraResultMonthlyDomainEventDto convertToExport(ExtraResultMonthlyPubEx export) {
		return new ExtraResultMonthlyDomainEventDto(export.getErrorAlarmCheckID(), export.getSortBy(), export.getNameAlarmExtraCon(), export.isUseAtr(), export.getTypeCheckItem(), export.isMessageBold(), export.getMessageColor(),
				export.getDisplayMessage(), WorkRecordExtraConAcFinder.convertToAttendanceItemCon(export.getCheckConMonthly()));
	}
}
