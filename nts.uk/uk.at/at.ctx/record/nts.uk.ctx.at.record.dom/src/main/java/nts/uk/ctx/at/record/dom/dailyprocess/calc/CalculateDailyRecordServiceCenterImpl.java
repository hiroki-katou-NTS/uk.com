package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryWorkingHours;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CalculateDailyRecordServiceCenterImpl implements CalculateDailyRecordServiceCenter{
	//休暇加算設定
	@Inject
	private HolidayAddtionRepository holidayAddtionRepository;
	//総拘束時間
	@Inject
	private SpecificWorkRuleRepository specificWorkRuleRepository;
	//会社ごとの代休設定
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	//乖離
	@Inject 
	private DivergenceTimeRepository divergenceTimeRepository;
	//エラーアラーム設定
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepository;
	
	//リポジトリ：労働条件
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	//リポジトリ；法定労働
	@Inject
	private DailyStatutoryWorkingHours dailyStatutoryWorkingHours;
	
	@Inject
	private CalculateDailyRecordService calculate;
	

	
	@Override
	public List<IntegrationOfDaily> calculate(List<IntegrationOfDaily> integrationOfDaily) {
		
		if(integrationOfDaily.isEmpty()) return Collections.emptyList();
		//会社共通の設定を
		String companyId = AppContexts.user().companyId();
		val companyCommonSetting = new ManagePerCompanySet(holidayAddtionRepository.findByCompanyId(companyId),
														   holidayAddtionRepository.findByCId(companyId),
														   specificWorkRuleRepository.findCalcMethodByCid(companyId),
														   compensLeaveComSetRepository.find(companyId),
														   divergenceTimeRepository.getAllDivTime(companyId),
														   errorAlarmWorkRecordRepository.getAllErAlCompanyAndUseAtr(companyId, true));

		EmploymentCode nowEmpCode = new EmploymentCode("");
		String employeeId = "";
		
		//計算してほしい範囲を取得
		val startDate = integrationOfDaily.get(0).getAffiliationInfor().getYmd();
		val endDate  = integrationOfDaily.get(integrationOfDaily.size() - 1).getAffiliationInfor().getYmd();
		
		/*労働条件取得*/
		val personalInfo = workingConditionItemRepository.getBySidAndPeriodOrderByStrDWithDatePeriod(employeeId, new DatePeriod(startDate, endDate));
		if(personalInfo.getMappingItems().isEmpty())return integrationOfDaily;
		
		/*return用のクラス*/
		List<IntegrationOfDaily> returnList = new ArrayList<>(); 
		
		for(IntegrationOfDaily targetIntegration:integrationOfDaily) {
			//労働条件を取得
			val test = personalInfo.getItemAtDate(targetIntegration.getAffiliationInfor().getYmd());
			if(test.isPresent()) {
				//労働条件
				//↑で取得したデータのセット
				companyCommonSetting.setPersonInfo(Optional.of(test.get().getValue()));
				//社員、日付毎に取得した法定労働時間
				if(!targetIntegration.getAffiliationInfor().getEmploymentCode().equals(nowEmpCode)) {
					nowEmpCode = targetIntegration.getAffiliationInfor().getEmploymentCode(); 
					val dailyUnit = dailyStatutoryWorkingHours.getDailyUnit(AppContexts.user().companyId(),
																			nowEmpCode.toString(),
																			employeeId,
																			targetIntegration.getAffiliationInfor().getYmd(),
																			test.get().getValue().getLaborSystem());
					companyCommonSetting.setDailyUnit(dailyUnit);
					returnList.add(calculate.calculate(targetIntegration, companyCommonSetting));
				}
			}

		}
		return returnList;
	}
}
