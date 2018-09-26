package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPUnitUseSettingRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.ot.zerotime.ZeroTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employee.EmployeeWtSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employee.EmployeeWtSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 計算に必要なパラメータを取得する
 * @author keisuke_hoshina
 *
 */
@Stateless
public class CommonCompanySettingForCalcImpl implements CommonCompanySettingForCalc{

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
	
	@Inject
	private BPUnitUseSettingRepository bPUnitUseSettingRepository; 
	
	@Inject
	private ZeroTimeRepository zeroTimeRepository;
	
	@Inject
	private OptionalItemRepository optionalItemRepository;
	
	@Inject
	private FormulaRepository formulaRepository;
	
	@Inject
	private EmpConditionRepository empConditionRepository;
	
	@Inject
	private UsageUnitSettingRepository usageUnitSettingRepository;
	
	@Inject
	private EmployeeWtSettingRepository employeeWtSettingRepository;
	
	@Override
	public ManagePerCompanySet getCompanySetting() {
		
		String companyId = AppContexts.user().companyId();
		val optionalItems = optionalItemRepository.findAll(companyId);
		val usageSetting = usageUnitSettingRepository.findByCompany(companyId);
		return new ManagePerCompanySet(holidayAddtionRepository.findByCompanyId(companyId),
									  holidayAddtionRepository.findByCId(companyId),
									  specificWorkRuleRepository.findCalcMethodByCid(companyId),
									  compensLeaveComSetRepository.find(companyId),
									  divergenceTimeRepository.getAllDivTime(companyId),
									  errorAlarmWorkRecordRepository.getAllErAlCompanyAndUseAtr(companyId, true),
									  bPUnitUseSettingRepository.getSetting(companyId),
									  optionalItems,
									  formulaRepository.find(companyId),
									  empConditionRepository.findAll(companyId, optionalItems.stream().map(oi -> oi.getOptionalItemNo().v()).collect(Collectors.toList())),
									  zeroTimeRepository.findByCId(companyId),
									  specificWorkRuleRepository.findUpperLimitWkHourByCid(companyId),
									  usageSetting,
									  getEmployeeWTSetting(usageSetting, companyId));
	}
	
	private Optional<EmployeeWtSetting> getEmployeeWTSetting(Optional<UsageUnitSetting> usageSetting, String compID){
		if(usageSetting.isPresent() && usageSetting.get().isEmployee()){
			return Optional.ofNullable(employeeWtSettingRepository.find(compID));
		}
		return Optional.empty();
	}

}
