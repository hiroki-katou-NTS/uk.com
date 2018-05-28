package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;

import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryWorkingHours;
import nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	
	//リポジトリ：労働条件
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	//リポジトリ；法定労働
	@Inject
	private DailyStatutoryWorkingHours dailyStatutoryWorkingHours;
	
	@Inject
	private CalculateDailyRecordService calculate;
	
	@Override
	public List<IntegrationOfDaily> calculate(List<IntegrationOfDaily> integrationOfDaily,DatePeriod date) {
		
		//会社共通の設定を
		val companyCommonSetting = new ManagePerCompanySet(holidayAddtionRepository.findByCompanyId(AppContexts.user().companyId()),
														   holidayAddtionRepository.findByCId(AppContexts.user().companyId()),
														   specificWorkRuleRepository.findCalcMethodByCid(AppContexts.user().companyId()),
														   compensLeaveComSetRepository.find(AppContexts.user().companyId()),
														   divergenceTimeRepository.getAllDivTime(AppContexts.user().companyId()));

		EmploymentCode nowEmpCode = new EmploymentCode("");
		String employeeId = "";
		
		/*労働条件取得*/
		val personalInfo = workingConditionItemRepository.getBySidAndPeriodOrderByStrDWithDatePeriod(employeeId, date);
		//今の日付の労働条件
		Optional<Entry<DateHistoryItem, WorkingConditionItem>> nowCondition = personalInfo.getItemAtDate(date.start());
		if(!nowCondition.isPresent()) return integrationOfDaily;
		
		/*return用のクラス*/
		List<IntegrationOfDaily> returnList = new ArrayList<>(); 
		
		for(IntegrationOfDaily targetIntegration:integrationOfDaily) {
			
			if(!nowCondition.get().getKey().contains(targetIntegration.getAffiliationInfor().getYmd())) {
				//労働条件
				/*社員毎に取得するデータ(労働条件)*/
				nowCondition = personalInfo.getItemAtDate(targetIntegration.getAffiliationInfor().getYmd());
				if(!nowCondition.isPresent()) continue;
				//↑で取得したデータのセット
				companyCommonSetting.setPersonInfo(Optional.of(nowCondition.get().getValue()));
			}
			//社員、日付毎に取得した法定労働時間
			if(!targetIntegration.getAffiliationInfor().getEmploymentCode().equals(nowEmpCode)) {
				nowEmpCode = targetIntegration.getAffiliationInfor().getEmploymentCode(); 
				val dailyUnit = dailyStatutoryWorkingHours.getDailyUnit(AppContexts.user().companyId(),
																		nowEmpCode.toString(),
																		employeeId,
																		targetIntegration.getAffiliationInfor().getYmd(),
																		nowCondition.get().getValue().getLaborSystem());
				companyCommonSetting.setDailyUnit(dailyUnit);
			}
			returnList.add(calculate.calculate(targetIntegration, companyCommonSetting));
		}
		return returnList;
	}
}
