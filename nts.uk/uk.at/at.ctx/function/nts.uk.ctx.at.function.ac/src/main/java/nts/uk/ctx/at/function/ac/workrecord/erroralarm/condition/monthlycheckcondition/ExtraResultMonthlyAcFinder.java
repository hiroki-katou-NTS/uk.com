package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.WorkRecordExtraConAcFinder;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.AgreementCheckCon36FunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.ExtraResultMonthlyFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.SpecHolidayCheckConFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonFunAdapter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.ExtraResultMonthlyPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.ExtraResultMonthlyPubEx;

@Stateless
public class ExtraResultMonthlyAcFinder implements ExtraResultMonthlyFunAdapter {

	@Inject
	private ExtraResultMonthlyPub repo;
	
	@Inject
	private SpecHolidayCheckConFunAdapter specHolidayCheckConRepo;
	
	@Inject
	private CheckRemainNumberMonFunAdapter checkRemainNumberMonRepo;
	
	@Inject
	private AgreementCheckCon36FunAdapter agreementCheckCon36Repo;

	@Override
	public List<ExtraResultMonthlyDomainEventDto> getListExtraResultMonByListEralID(List<String> listEralCheckID) {
		List<ExtraResultMonthlyDomainEventDto> data = repo.getListExtraResultMonByListEralID(listEralCheckID)
				.stream().map(c -> convertToExport(c)).collect(Collectors.toList());
		for(ExtraResultMonthlyDomainEventDto extraResultMonthly : data) {
			//get SpecHolidayCheckCon
			extraResultMonthly.setSpecHolidayCheckCon(specHolidayCheckConRepo.getSpecHolidayCheckConById(extraResultMonthly.getErrorAlarmCheckID()));
			//get CheckRemainNumberMon
			extraResultMonthly.setCheckRemainNumberMon(checkRemainNumberMonRepo.getByEralCheckID(extraResultMonthly.getErrorAlarmCheckID()));
			//get list AgreementCheckCon36
			extraResultMonthly.setAgreementCheckCon36(agreementCheckCon36Repo.getAgreementCheckCon36ById(extraResultMonthly.getErrorAlarmCheckID()));
		}
		return data;
	}

	private ExtraResultMonthlyDomainEventDto convertToExport(ExtraResultMonthlyPubEx export) {
		return new ExtraResultMonthlyDomainEventDto(export.getErrorAlarmCheckID(), export.getSortBy(), export.getNameAlarmExtraCon(), export.isUseAtr(), export.getTypeCheckItem(), export.isMessageBold(), export.getMessageColor(),
				export.getDisplayMessage(),
				export.getCheckConMonthly()==null?null:
				WorkRecordExtraConAcFinder.convertToAttendanceItemCon(export.getCheckConMonthly()));
	}
}
