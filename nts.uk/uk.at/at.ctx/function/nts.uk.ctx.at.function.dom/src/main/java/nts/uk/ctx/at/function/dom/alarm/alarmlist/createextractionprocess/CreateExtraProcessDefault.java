package nts.uk.ctx.at.function.dom.alarm.alarmlist.createextractionprocess;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CreateExtraProcessDefault implements CreateExtraProcessService {

	@Inject
	private AlarmListExtraProcessStatusRepository repo;
	@Override
	public String createExtraProcess(String companyId) {
		int startTime = GeneralDateTime.now().hours()*60+GeneralDateTime.now().minutes();
		//ドメイン「アラームリスト抽出処理状況」を作成する
		AlarmListExtraProcessStatus alarmExtraProcessStatus = new AlarmListExtraProcessStatus(
				IdentifierUtil.randomUniqueId(), companyId, GeneralDate.today(),startTime,
				AppContexts.user().employeeId(), null, null,ExtractionState.PROCESSING);
		return repo.addAlListExtaProcess(alarmExtraProcessStatus);
	}

}
