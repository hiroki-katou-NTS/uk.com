package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCreateDataInputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class InterimRemainOfMonthProccessImpl implements InterimRemainOfMonthProccess{
	@Inject
	private RemainCreateInforByRecordData recordDataService;
	@Inject
	private InterimRemainOffPeriodCreateData periodCreateDataService;
	@Inject
	private ComSubstVacationRepository subRepos;
	@Inject
	private CompensLeaveComSetRepository leaveSetRepos;
	@Override
	public Map<GeneralDate, DailyInterimRemainMngData> createInterimRemainDataMng(String cid, String sid,
			DatePeriod dateData) {
		//(Imported)「残数作成元情報(実績)」を取得する
		List<RecordRemainCreateInfor> lstRecordData = recordDataService.lstRecordRemainData(cid, sid, dateData);		
		//アルゴリズム「指定期間の暫定残数管理データを作成する」を実行する
		InterimRemainCreateDataInputPara inputPara = new InterimRemainCreateDataInputPara(cid, sid, dateData, lstRecordData, Collections.emptyList(),
				Collections.emptyList(), false);
		//雇用履歴と休暇管理設定を取得する
		Optional<ComSubstVacation> comSetting = subRepos.findById(cid);
		CompensatoryLeaveComSetting leaveComSetting = leaveSetRepos.find(cid);
		CompanyHolidayMngSetting comHolidaySetting = new CompanyHolidayMngSetting(cid, comSetting, leaveComSetting);
		return periodCreateDataService.createInterimRemainDataMng(inputPara, comHolidaySetting);
	}
}
