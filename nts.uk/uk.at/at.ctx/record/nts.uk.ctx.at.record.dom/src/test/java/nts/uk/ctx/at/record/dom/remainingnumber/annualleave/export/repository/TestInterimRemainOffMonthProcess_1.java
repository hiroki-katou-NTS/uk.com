package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import java.util.Map;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;

/**
 * 月次処理用の暫定残数管理データを作成する
 * @author masaaki_jinno
 *
 */
public class TestInterimRemainOffMonthProcess_1 implements InterimRemainOffMonthProcess{
	
//	@Inject
//	private RemainCreateInforByRecordData recordDataService;
//	@Inject
//	private InterimRemainOffPeriodCreateData periodCreateData;
//	@Inject
//	private ComSubstVacationRepository subRepos;
//	@Inject
//	private CompensLeaveComSetRepository leaveSetRepos;	
	@Override
	public Map<GeneralDate, DailyInterimRemainMngData> monthInterimRemainData(String cid, String sid, DatePeriod dateData) {
//		//(Imported)「残数作成元情報(実績)」を取得する
//		List<RecordRemainCreateInfor> lstRecordData = recordDataService.lstRecordRemainData(cid, sid, dateData);
//		//アルゴリズム「指定期間の暫定残数管理データを作成する」を実行する
//		InterimRemainCreateDataInputPara inputPara = new InterimRemainCreateDataInputPara(cid, sid, dateData, lstRecordData, Collections.emptyList(), Collections.emptyList(), false);
//		//雇用履歴と休暇管理設定を取得する
//		Optional<ComSubstVacation> comSetting = subRepos.findById(cid);
//		CompensatoryLeaveComSetting leaveComSetting = leaveSetRepos.find(cid);
//		CompanyHolidayMngSetting comHolidaySetting = new CompanyHolidayMngSetting(cid, comSetting, leaveComSetting);
//		Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput = periodCreateData.createInterimRemainDataMng(inputPara, comHolidaySetting);
//		return mapDataOutput;
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}
}

