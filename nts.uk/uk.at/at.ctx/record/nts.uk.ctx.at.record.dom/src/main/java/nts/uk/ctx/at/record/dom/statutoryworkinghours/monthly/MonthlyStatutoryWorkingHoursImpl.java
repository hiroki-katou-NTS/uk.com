package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryWorkingHours;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryWorkingHoursImpl;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

@Stateless
public class MonthlyStatutoryWorkingHoursImpl implements MonthlyStatutoryWorkingHours{
	
	@Inject
	private UsageUnitSettingRepository usageUnitSettingRepository;
	
	@Inject
	private DailyStatutoryWorkingHours dailyStatutoryWorkingHours;
	
	@Inject
	private GetShainMonthlyLaborTime getShainMonthlySetting;
	
	@Inject
	private GetWorkingPlaceMonthlyLaborTime getWorkingPlaceMonthlyLaborTime;
	
	@Inject
	private GetEmploymentMonthlyLaborTime getEmploymentMonthlyLaborTime;
	
	@Inject
	private GetCompanyMonthlyLaborTime getCompanyMonthlyLaborTime;
	
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;
	
	@Inject
	private ShainFlexSettingRepository shainFlexSettingRepository;
	
	@Inject
	private WkpFlexSettingRepository wkpFlexSettingRepository;
	
	@Inject
	private EmpFlexSettingRepository empFlexSettingRepository;
	
	@Inject
	private ComFlexSettingRepository comFlexSettingRepository;
	
	/*require用*/
	@Inject
	private ComNormalSettingRepository comNormalSettingRepository;
	@Inject
	private ComDeforLaborSettingRepository comDeforLaborSettingRepository;
	@Inject
	private EmpNormalSettingRepository empNormalSettingRepository;
	@Inject
	private EmpDeforLaborSettingRepository empDeforLaborSettingRepository;
	@Inject
	private WkpNormalSettingRepository wkpNormalSettingRepository;
	@Inject
	private WkpDeforLaborSettingRepository wkpDeforLaborSettingRepository;
	@Inject
	private ShainNormalSettingRepository shainNormalSettingRepository;
	@Inject
	private ShainDeforLaborSettingRepository shainDeforLaborSettingRepository;
	@Inject
	private ShainRegularWorkTimeRepository shainRegularWorkTimeRepository;
	@Inject
	private ShainTransLaborTimeRepository shainTransLaborTimeRepository;
	@Inject
	private WkpRegularLaborTimeRepository wkpRegularLaborTimeRepository;
	@Inject
	private WkpTransLaborTimeRepository wkpTransLaborTimeRepository;
	@Inject
	private EmpRegularWorkTimeRepository empRegularWorkTimeRepository;
	@Inject
	private EmpTransWorkTimeRepository empTransWorkTimeRepository;
	@Inject
	private ComRegularLaborTimeRepository comRegularLaborTimeRepository;
	@Inject
	private ComTransLaborTimeRepository comTransLaborTimeRepository;
	/*require用*/
	
	
	/**
	 * 週、月の法定労働時間を取得(通常、変形用)
	 * @return
	 */
	@Override
	public Optional<MonAndWeekStatutoryTime> getMonAndWeekStatutoryTime(String companyId,
			  												  String employmentCd,
			  												  String employeeId,
			  												  GeneralDate baseDate,
			  												  YearMonth yearMonth,
			  												  WorkingSystem workingSystem) {
		val require = requireImpl();

		val cacheCarrier = new CacheCarrier(); 
		
		return getMonAndWeekStatutoryTimeRequire(
				require, 
				cacheCarrier, 
				companyId,
				employmentCd, 
				employeeId, 
				baseDate, 
				yearMonth, 
				workingSystem);
	}
	
	@Override
	public Optional<MonAndWeekStatutoryTime> getMonAndWeekStatutoryTimeRequire(
															  Require require,
															  CacheCarrier cacheCarrier,
															  String companyId,
			  												  String employmentCd,
			  												  String employeeId,
			  												  GeneralDate baseDate,
			  												  YearMonth yearMonth,
			  												  WorkingSystem workingSystem) {
		//enum<労働制> = フレックス勤務、計算対象外　の場合
		if (workingSystem.isFlexTimeWork() || workingSystem.isExcludedWorkingCalculate()) {
			return Optional.empty();
		}
		//週の時間を取得
		MonthlyEstimateTime weeklyTime = getWeeklylyEstimateTime(require, cacheCarrier, companyId,employmentCd,employeeId,baseDate,workingSystem);
		//月の時間を取得
		MonthlyEstimateTime monthlyTime = getMonthlyEstimateTime(require, cacheCarrier, companyId,employmentCd,employeeId,baseDate,yearMonth,workingSystem);
		
		return Optional.of(new MonAndWeekStatutoryTime(weeklyTime,monthlyTime));
	}
	
	/**
	 * 週、月の法定労働時間を取得(フレックス用)
	 * @return
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public MonthlyFlexStatutoryLaborTime getFlexMonAndWeekStatutoryTime(String companyId,
				String employmentCd,
				String employeeId,
				GeneralDate baseDate,
				YearMonth yearMonth
				) {
		val require = requireImpl();
		
		val cacheCarrier = new CacheCarrier();
		
		return getFlexMonAndWeekStatutoryTimeRequire(require, cacheCarrier, companyId, employmentCd, employeeId, baseDate, yearMonth);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public MonthlyFlexStatutoryLaborTime getFlexMonAndWeekStatutoryTimeRequire(
																		Require require,
																		CacheCarrier cacheCarrier,
																		String companyId,
			  															String employmentCd,
			  															String employeeId,
			  															GeneralDate baseDate,
			  															YearMonth yearMonth
			  															) {
		//取得処理
		val list = getFlexMonthlyUnit(require, cacheCarrier, companyId,employmentCd,employeeId,baseDate,yearMonth);
		if(!list.isPresent()) {
			return MonthlyFlexStatutoryLaborTime.zeroMonthlyFlexStatutoryLaborTime();
		}
		MonthlyUnit statutorySetting = list.get().getStatutoryList().stream().filter(m -> m.getMonth().v() == yearMonth.month()).findFirst().orElse(null);
		MonthlyUnit specifiedSetting = list.get().getSpecifiedList().stream().filter(m -> m.getMonth().v() == yearMonth.month()).findFirst().orElse(null);
		MonthlyUnit weekAveSetting = list.get().getWeekAveList().stream().filter(m -> m.getMonth().v() == yearMonth.month()).findFirst().orElse(null);
		MonthlyEstimateTime statutorytime = new MonthlyEstimateTime(0);
		MonthlyEstimateTime specified = new MonthlyEstimateTime(0);
		MonthlyEstimateTime weekAve = new MonthlyEstimateTime(0);
		if(statutorySetting!=null) {
			statutorytime = statutorySetting.getMonthlyTime();
		}
		if(specifiedSetting!=null) {
			specified = specifiedSetting.getMonthlyTime();
		}
		if (weekAveSetting != null){
			weekAve = weekAveSetting.getMonthlyTime();
		}
		return new MonthlyFlexStatutoryLaborTime(statutorytime,specified,weekAve);
	}
	
	
	/**
	 * 週の時間を取得
	 * @return
	 */
	private MonthlyEstimateTime getWeeklylyEstimateTime(Require require,
													   CacheCarrier cacheCarrier,
													   String companyId,
			  										   String employmentCd,
			  										   String employeeId,
			  										   GeneralDate baseDate,
			  										   WorkingSystem workingSystem
			  										   ) {
		Optional<WorkingTimeSetting> weeklyTime = dailyStatutoryWorkingHours.getWorkingTimeSettingRequire(
				require, cacheCarrier, companyId, employmentCd, employeeId, baseDate, workingSystem);
		//取得に失敗した場合
		if(!weeklyTime.isPresent()) {
			return new MonthlyEstimateTime(0);
		}
		return new MonthlyEstimateTime(weeklyTime.get().getWeeklyTime().getTime().valueAsMinutes());
	}
	
	
	/**
	 * 月の時間を取得
	 * @return
	 */
	private MonthlyEstimateTime getMonthlyEstimateTime(Require require,
													  CacheCarrier cacheCarrier,
													  String companyId,
			  										  String employmentCd,
			  										  String employeeId,
			  										  GeneralDate baseDate,
			  										  YearMonth yearMonth,
			  										  WorkingSystem workingSystem) {
		
		MonthlyUnit monthlyUnit = getMonthlyUnit(require,
												 cacheCarrier,
												 companyId,
												 employmentCd,
												 employeeId,
												 baseDate,
												 yearMonth,
												 workingSystem).stream()
															   .filter(m -> m.getMonth().v() == yearMonth.month())
															   .findFirst().orElse(null);
		//取得に失敗した場合
		if(monthlyUnit==null) {
			return new MonthlyEstimateTime(0);
		}																	
		return monthlyUnit.getMonthlyTime();
	}
	
	
	
	/**
	 * 取得する単位を取得
	 * @return
	 */
	private List<MonthlyUnit> getMonthlyUnit(Require require,
											CacheCarrier cacheCarrier,
											String companyId,
											String employmentCd,
											String employeeId,
											GeneralDate baseDate,
											YearMonth yearMonth,
											WorkingSystem workingSystem){
		
		/*労働時間と日数の設定の利用単位の設定*/
		val usageUnitSetting = require.findByCompany(companyId);
		if (!usageUnitSetting.isPresent()) {
			return new ArrayList<>();
		}
	
		if(usageUnitSetting.get().isEmployee()) {//社員の労働時間を管理する場合
			val result = getShainMonthlySetting.getShainWorkingTimeSettingRequire(require, companyId,employeeId,workingSystem,yearMonth);
			if(!result.isEmpty()) {
				return result;
			}
		}
		if(usageUnitSetting.get().isWorkPlace()) {//職場の労働時間を管理する場合
			//職場の設定を取得
			val result = getWorkingPlaceMonthlyLaborTime.getWkpWorkingTimeSettingRequire(require, cacheCarrier,
					companyId,employeeId,baseDate,yearMonth,workingSystem);
			if(!result.isEmpty()) {
				return result;
			}
		}
		if(usageUnitSetting.get().isEmployment()) {//雇用の労働時間を管理する場合
			//雇用別設定の取得
			val result = getEmploymentMonthlyLaborTime.getEmpWorkingTimeSettingRequire(require, companyId,employmentCd,yearMonth,workingSystem);
			if(!result.isEmpty()) {
				return result;
			}
		}
		//会社別設定の取得
		return getCompanyMonthlyLaborTime.getComWorkingTimeSettingRequire(require, companyId,yearMonth,workingSystem);
	}
	
	/**
	 * 取得処理(フレックス)
	 * @return
	 */
	private Optional<MonthlyFlexStatutoryLaborTimeList> getFlexMonthlyUnit(Require require,
																		  CacheCarrier cacheCarrier,
																		  String companyId,
																		  String employmentCd,
																		  String employeeId,
																		  GeneralDate baseDate,
																		  YearMonth yearMonth) {
		/*労働時間と日数の設定の利用単位の設定*/
		val usageUnitSetting = require.findByCompany(companyId);
		if (usageUnitSetting.isPresent()) {
			//取得処理
			if(usageUnitSetting.get().isEmployee()) {//社員の労働時間を管理する場合
				val result = require.findEmpFlexSetting(companyId, employeeId, yearMonth.year());
				if(result.isPresent()) {
					return Optional.of(new MonthlyFlexStatutoryLaborTimeList(
							result.get().getSpecifiedSetting(),
							result.get().getStatutorySetting(),
							result.get().getWeekAveSetting()));
				}
			}
			if(usageUnitSetting.get().isWorkPlace()) {//職場の労働時間を管理する場合
				//所属職場を含む上位階層の職場IDを取得
				List<String> workPlaceIdList = affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRootRequire(cacheCarrier, companyId, employeeId, baseDate);
				for(String workPlaceId:workPlaceIdList) {
					val result = require.findShainFlexSetting(companyId, workPlaceId, yearMonth.year());
					if(result.isPresent()) {
						return Optional.of(new MonthlyFlexStatutoryLaborTimeList(
								result.get().getSpecifiedSetting(),
								result.get().getStatutorySetting(),
								result.get().getWeekAveSetting()));
					}
				}
			}
			if(usageUnitSetting.get().isEmployment()) {//雇用の労働時間を管理する場合
				//雇用別設定の取得
				val result = require.findEmpFlexSetting(companyId, employmentCd, yearMonth.year());
				if(result.isPresent()) {
					return Optional.of(new MonthlyFlexStatutoryLaborTimeList(
							result.get().getSpecifiedSetting(),
							result.get().getStatutorySetting(),
							result.get().getWeekAveSetting()));
				}
			}
			//会社別設定の取得
			val result = require.findComFlexSetting(companyId, yearMonth.year());
			if(result.isPresent()) {
				return Optional.of(new MonthlyFlexStatutoryLaborTimeList(
						result.get().getSpecifiedSetting(),
						result.get().getStatutorySetting(),
						result.get().getWeekAveSetting()));
			}
		}	
		return Optional.empty();
	}
	
	
	public static interface Require extends GetCompanyMonthlyLaborTimeImpl.Require,
											GetEmploymentMonthlyLaborTimeImpl.Require,
											GetWorkingPlaceMonthlyLaborTimeImpl.Require,
											GetShainMonthlyLaborTimeImpl.Require,
											DailyStatutoryWorkingHoursImpl.Require{
//		usageUnitSettingRepository.findByCompany(companyId);
		Optional<UsageUnitSetting> findByCompany(String companyId);
//		shainFlexSettingRepository.find(companyId, employeeId, yearMonth.year());
		Optional<ShainFlexSetting> findShainFlexSetting(String cid, String empId, int year);
//		 empFlexSettingRepository.find(companyId, employmentCd, yearMonth.year());
		Optional<EmpFlexSetting> findEmpFlexSetting(String cid, String emplCode, int year);
//		comFlexSettingRepository.find(companyId, yearMonth.year());
		Optional<ComFlexSetting> findComFlexSetting(String companyId, int year);
	}
	
	public Require requireImpl() {
		return new MonthlyStatutoryWorkingHoursImpl.Require() {
			@Override
			public Optional<ShainNormalSetting> findShainNormalSetting(String cid, String empId, int year) {
				return shainNormalSettingRepository.find(cid, empId, year);
			}
			@Override
			public Optional<ShainDeforLaborSetting> findShainDeforLaborSetting(String cid, String empId, int year) {
				return shainDeforLaborSettingRepository.find(cid, empId, year);
			}
			@Override
			public Optional<WkpNormalSetting> findWkpNormalSetting(String cid, String wkpId, int year) {
				return wkpNormalSettingRepository.find(cid, wkpId, year);
			}
			@Override
			public Optional<WkpDeforLaborSetting> findWkpDeforLaborSetting(String cid, String wkpId, int year) {
				return wkpDeforLaborSettingRepository.find(cid, wkpId, year);
			}
			@Override
			public Optional<EmpNormalSetting> findEmpNormalSetting(String cid, String emplCode, int year) {
				return empNormalSettingRepository.find(cid, emplCode, year);
			}
			@Override
			public Optional<EmpDeforLaborSetting> findEmpDeforLaborSetting(String cid, String emplCode, int year) {
				return empDeforLaborSettingRepository.find(cid, emplCode, year);
			}
			@Override
			public Optional<ComNormalSetting> findComNormalSetting(String companyId, int year) {
				return comNormalSettingRepository.find(companyId,year);
			}
			@Override
			public Optional<ComDeforLaborSetting> findComDeforLaborSetting(String companyId, int year) {
				return comDeforLaborSettingRepository.find(companyId,year);
			}
			@Override
			public Optional<UsageUnitSetting> findByCompany(String companyId) {
				return usageUnitSettingRepository.findByCompany(companyId);
			}
			@Override
			public Optional<ShainFlexSetting> findShainFlexSetting(String cid, String empId, int year) {
				return shainFlexSettingRepository.find(cid, empId, year);
			}
			@Override
			public Optional<EmpFlexSetting> findEmpFlexSetting(String cid, String emplCode, int year) {
				return  empFlexSettingRepository.find(cid, emplCode, year);
			}
			@Override
			public Optional<ComFlexSetting> findComFlexSetting(String companyId, int year) {
				return comFlexSettingRepository.find(companyId, year);
			}
			@Override
			public Optional<EmpRegularLaborTime> findById(String cid, String employmentCode) {
				return empRegularWorkTimeRepository.findById(cid, employmentCode);
			}
			@Override
			public Optional<ComTransLaborTime> findcomTransLaborTime(String companyId) {
				return comTransLaborTimeRepository.find(companyId);
			}
			@Override
			public Optional<ComRegularLaborTime> findcomRegularLaborTime(String companyId) {
				return comRegularLaborTimeRepository.find(companyId);
			}
			@Override
			public Optional<WkpTransLaborTime> findWkpTransLaborTime(String cid, String wkpId) {
				return wkpTransLaborTimeRepository.find(cid, wkpId);
			}
			@Override
			public Optional<WkpRegularLaborTime> findWkpRegularLaborTime(String empId, String wkpId) {
				return wkpRegularLaborTimeRepository.find(empId, wkpId);
			}
			@Override
			public Optional<ShainRegularLaborTime> findShainRegularLaborTime(String Cid, String EmpId) {
				return shainRegularWorkTimeRepository.find(Cid, EmpId);
			}
			@Override
			public Optional<ShainTransLaborTime> findShainTransLaborTime(String cid, String empId) {
				return shainTransLaborTimeRepository.find(cid, empId);
			}
			@Override
			public Optional<EmpTransLaborTime> findEmpTransLaborTime(String cid, String emplId) {
				return empTransWorkTimeRepository.find(cid, emplId);
			}
		};
	}
	
}
