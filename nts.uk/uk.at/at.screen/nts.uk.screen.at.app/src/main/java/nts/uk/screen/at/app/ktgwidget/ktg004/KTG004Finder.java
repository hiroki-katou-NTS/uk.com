
package nts.uk.screen.at.app.ktgwidget.ktg004;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.AnnualLeaveRemainingNumberImport;
import nts.uk.ctx.at.function.dom.employmentfunction.checksdailyerror.ChecksDailyPerformanceErrorRepository;
import nts.uk.ctx.at.record.pub.monthly.GetMonthlyRecordPub;
import nts.uk.ctx.at.record.pub.monthly.MonthlyRecordValuesExport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService.RequireM3;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.DetailedWorkStatusSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetType;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.TopPageDisplayYearMonthEnum;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.WorkStatusItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG004_勤務状況.B：設定ダイアログ.ユースケース.起動する.起動する
 * @author thanhPV
 */
@Stateless
public class KTG004Finder {

	@Inject
	private ApproveWidgetRepository approveWidgetRepo;
	
	@Inject
	private SpecialHolidayRepository specialHolidayRepository;
	
	@Inject 
	private ClosureRepository closureRepo;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	@Inject 
	private ShareEmploymentAdapter shareEmploymentAdapter;
	
	@Inject
	private RoleExportRepo roleExportRepo;
	
	@Inject
	private ChecksDailyPerformanceErrorRepository checksDailyPerformanceErrorRepo;
	
	@Inject
	private GetTheNumberOfVacationsLeft getTheNumberOfVacationsLeft;
	
	@Inject
	private GetMonthlyRecordPub getMonthlyRecordPub;
	
	/** 起動する */
	public WorkStatusSettingDto getApprovedDataWidgetStart() {
		
		String companyId = AppContexts.user().companyId();
		
		List<ItemsSettingDto> itemsSetting = new ArrayList<>();
	
		//指定するウィジェットの設定を取得する(Get the settings of the specified widget)
		Optional<StandardWidget> standardWidget = approveWidgetRepo.findByWidgetTypeAndCompanyId(StandardWidgetType.WORK_STATUS, companyId);
		
		//ドメインモデル「特別休暇」を取得する(Get the domain model "special leave")
		List<SpecialHoliday> specialHolidays = specialHolidayRepository.findByCompanyId(companyId);
		
		if(standardWidget.isPresent()) {
			for (DetailedWorkStatusSetting item : standardWidget.get().getDetailedWorkStatusSettingList()) {
				if(item.getItem().value > WorkStatusItem.HDSP20_DISPLAY_ATR.value) {
					itemsSetting.add(new ItemsSettingDto(item, ""));
				}else {
					Optional<SpecialHoliday> sphl = specialHolidays.stream().filter(c -> item.getItem().value == c.getSpecialHolidayCode().v()).findAny();
					if(sphl.isPresent()) {
						itemsSetting.add(new ItemsSettingDto(new DetailedWorkStatusSetting(item.getDisplayType(), EnumAdaptor.valueOf(item.getItem().value, WorkStatusItem.class)), sphl.get().getSpecialHolidayName().v()));
					}
				}
			}
		}else {
			for (int i = WorkStatusItem.DAY_ERR_DISPLAY_ATR.value; i <= WorkStatusItem.CARE_DISPLAY_ATR.value; i++) {
				itemsSetting.add(new ItemsSettingDto(new DetailedWorkStatusSetting(NotUseAtr.NOT_USE, EnumAdaptor.valueOf(i, WorkStatusItem.class)), ""));
			}
			for (SpecialHoliday specialHoliday : specialHolidays) {
				itemsSetting.add(new ItemsSettingDto(new DetailedWorkStatusSetting(NotUseAtr.NOT_USE, EnumAdaptor.valueOf(specialHoliday.getSpecialHolidayCode().v(), WorkStatusItem.class)), specialHoliday.getSpecialHolidayName().v()));
			}
		}
		
		return new WorkStatusSettingDto(itemsSetting, standardWidget.isPresent() ? standardWidget.get().getName().v() : null);
	}
	
	public void getData(KTG004InputDto param) {
		String cid = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		TopPageDisplayYearMonthEnum topPageYearMonthEnum = EnumAdaptor.valueOf(param.getYearMonth(),TopPageDisplayYearMonthEnum.class);
		this.startWorkStatus(cid, employeeId, topPageYearMonthEnum);
	}
	
	//勤務状況を起動する
	public AcquisitionResultOfWorkStatusOutput startWorkStatus(String cid, String employeeId, TopPageDisplayYearMonthEnum topPageYearMonthEnum) {
		AcquisitionResultOfWorkStatusOutput result = new AcquisitionResultOfWorkStatusOutput(); 
		
		//Get the settings of the specified widget - 指定するウィジェットの設定を取得する 
		WorkStatusSettingDto setting = this.getApprovedDataWidgetStart();
		result.setItemsSetting(setting.getItemsSetting());
		result.setName(setting.getName());
		
		//Get the processing deadline for employees - 社員に対応する処理締めを取得する
		RequireM3 require = ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter);
		Closure closure = ClosureService.getClosureDataByEmployee(require, new CacheCarrier(), employeeId, GeneralDate.today());
		
		//セット項目：//Set item:
		//勤務状況の取得結果．締めID＝取得したドメインモデル「締め」．締めID
		result.setClosureId(closure.getClosureId().value);
		
		//Calculate the period of the specified year and month - 指定した年月の期間を算出する
		DatePeriod datePeriod = ClosureService.getClosurePeriod(closure, closure.getClosureMonth().getProcessingYm());
		
		//セット項目：
		//勤務状況の取得結果．当月の締め情報．処理年月＝取得したドメインモデル「締め」．当月
		//勤務状況の取得結果．当月の締め情報．締め期間＝取得した締め期間
		//勤務状況の取得結果．表示する年月の締め情報＝勤務状況の取得結果．当月の締め情報
		result.setClosingThisMonth(new CurrentClosingPeriod(closure.getClosureMonth().getProcessingYm().v(), datePeriod.start(), datePeriod.end()));
		result.setClosingDisplay(result.getClosingThisMonth());
		
		//Get the target period of the top page - トップページの対象期間を取得する 
		CurrentClosingPeriod currentClosingPeriod = this.getTargetPeriodOfTopPage(result.getClosureId(), result.getClosingThisMonth(), Optional.empty(), topPageYearMonthEnum);
		if(topPageYearMonthEnum == TopPageDisplayYearMonthEnum.NEXT_MONTH_DISPLAY) {
			result.setClosingDisplay(currentClosingPeriod);
		}
		//Get work status data - 勤務状況のデータを取得する
		result.setAttendanceInfor(this.getWorkStatusData(cid, employeeId, result.getItemsSetting(), result.getClosingThisMonth()));
		
		//Get the number of vacations left - 休暇残数を取得する
		//result.setRemainingNumberInfor(this.getTheNumberOfVacationsLeft(cid, employeeId, result.getItemsSetting(), datePeriod));
		
		//Determine if the login person is the person in charge - ログイン者が担当者か判断する
		result.setDetailedWorkStatusSettings(roleExportRepo.getWhetherLoginerCharge().isEmployeeCharge());
		
		return result;
	}
	

	/**
	 * @name トップページの対象期間を取得する
	 * @param closureId 締めID
	 * @param currentMonth 当月の締め情報
	 * @param nextMonth 翌月の締め情報
	 * @param topPageYearMonth 表示年月
	 * @return
	 */
	public CurrentClosingPeriod getTargetPeriodOfTopPage(Integer closureId, CurrentClosingPeriod currentMonth, Optional<CurrentClosingPeriod> nextMonth, TopPageDisplayYearMonthEnum topPageYearMonth) {
		//INPUT．表示年月＝当月表示
		if(topPageYearMonth == TopPageDisplayYearMonthEnum.THIS_MONTH_DISPLAY) {
			return currentMonth;
		}
		//INPUT．表示年月＝翌月表示
		else {
			//INPUT．翌月の締め情報をチェックする = Nullじゃない
			if(nextMonth.isPresent()) {
				return nextMonth.get();
			}else {
				DatePeriod datePeriod = ClosureService.getClosurePeriod(ClosureService.createRequireM1(closureRepo, closureEmploymentRepo), closureId, new YearMonth(currentMonth.getProcessingYm()));
				return new CurrentClosingPeriod(currentMonth.getProcessingYm(), datePeriod.start(), datePeriod.end());
			}
		}
	}
	
	
	/**
	 * @name 勤務状況のデータを取得する
	 * @param cid 会社ID
	 * @param employeeId 社員ID
	 * @param setting 勤務状況の詳細設定
	 * @param currentMonth 現在の締め期間
	 * @return 対象社員の勤怠情報
	 */
	public AttendanceInforDto getWorkStatusData(String cid, String employeeId, List<ItemsSettingDto> itemsSetting, CurrentClosingPeriod currentMonth) {
		AttendanceInforDto result = new AttendanceInforDto();
		Optional<ItemsSettingDto> dayErrDisplayAtr = itemsSetting.stream().filter(c->c.getItem() == WorkStatusItem.DAY_ERR_DISPLAY_ATR.value && c.isDisplayType()).findAny();
		if(dayErrDisplayAtr.isPresent()) {
			//sử lý 08
			//request list 192
			//アルゴリズム「08.日別実績のエラー有無表示」を実行する(Thực thi xử lý [08:hiển thị co hay không lỗi của dailyactual ])
			result.setDailyErrors(checksDailyPerformanceErrorRepo.checked(employeeId, currentMonth.getStartDate(), currentMonth.getEndDate()));
		}
		//条件：
		//INPUT．勤務状況の詳細設定．項目 IN (残業時間, フレックス時間, 休日出勤時間, 就業時間外深夜時間, 遅刻早退回数)
		/*
		 * 【パラメータ】 ・社員ID＝List＜INPUT．社員ID＞
		 * ・勤怠項目ID（List）＝List＜月別実績のトップページ表示用時間．残業合計時間(2063),
		 * 月別実績のトップページ表示用時間．フレックス合計時間(2065), フレックス繰越時間．フレックス繰越時間(19),
		 * 月別実績のトップページ表示用時間．休日出勤合計時間(2064), 月別実績の深夜時間．所定外深夜時間.時間．時間(319),
		 * 月別実績の遅刻早退．遅刻．回数(312), 月別実績の遅刻早退．早退．回数(315) の勤怠項目ID＞
		 */
		Map<String, List<MonthlyRecordValuesExport>> mapData = getMonthlyRecordPub.getRecordValues(
				Arrays.asList(employeeId),
				new YearMonthPeriod(new YearMonth(currentMonth.getProcessingYm()), new YearMonth(currentMonth.getProcessingYm())),
				Arrays.asList(2063, 2065, 19, 2064, 319, 312, 315));
		if (mapData.isEmpty()) {
			return result;
		}
		List<MonthlyRecordValuesExport> data = mapData.get(employeeId);
		if(!data.isEmpty()) {
			List<ItemValue> itemValues = data.get(0).getItemValues();
			for (ItemValue c : itemValues) {
				if(c.getItemId() == 2036) {
					result.setOverTime(c.getValue());
				}else if(c.getItemId() == 2065) {
					result.setFlexTime(c.getValue());
				}else if(c.getItemId() == 19) {
					result.setFlexCarryOverTime(c.getValue());
				}else if(c.getItemId() == 2064) {
					result.setHolidayTime(c.getValue());
				}else if(c.getItemId() == 319) {
					result.setNigthTime(c.getValue());
				}else if(c.getItemId() == 312) {
					result.setLate(c.getValue());
				}else if(c.getItemId() == 315) {
					result.setEarly(c.getValue());
				}
			}
		}
		
		return result;
	}
	
	/**
	 * @name 休暇残数を取得する
	 * @param cid 会社ID
	 * @param employeeId 社員ID
	 * @param setting 勤務状況の詳細設定
	 * @return 対象社員の残数情報
	 */
	public RemainingNumberInforDto getTheNumberOfVacationsLeft(String cid, String employeeId, List<ItemsSettingDto> itemsSetting, DatePeriod datePeriod) {
		
		GeneralDate systemDate = GeneralDate.today();
		
		RemainingNumberInforDto result = new RemainingNumberInforDto();
		
		//条件：
		//INPUT．勤務状況の詳細設定．項目＝年休残数
		Optional<ItemsSettingDto> hdpaidDisplayAtr = itemsSetting.stream().filter(c->c.getItem() == WorkStatusItem.HDPAID_DISPLAY_ATR.value && c.isDisplayType()).findAny();
		if(hdpaidDisplayAtr.isPresent()) {
			//アルゴリズム「15.年休残数表示」を実行する(Thực thi xử lý [15:Hiển thị số phép năm còn tồn])
			AnnualLeaveRemainingNumberImport data = getTheNumberOfVacationsLeft.annualLeaveResidualNumberIndication(employeeId, systemDate);
			result.setNumberOfAnnualLeaveRemain(new RemainingDaysAndTimeDto(data.getAnnualLeaveGrantPreDay(), new AttendanceTime(data.getAnnualLeaveGrantPreTime())));
		}
		
		//条件：
		//INPUT．勤務状況の詳細設定．項目＝積立年休残数
		Optional<ItemsSettingDto> hdSTKDisplayAtr = itemsSetting.stream().filter(c->c.getItem() == WorkStatusItem.HDSTK_DISPLAY_ATR.value && c.isDisplayType()).findAny();
		if(hdSTKDisplayAtr.isPresent()) {
			//アルゴリズム「16.積立年休残数表示」を実行する(Thực hiện [16:số phép năm tích lũy còn tồn])
			result.setNumberAccumulatedAnnualLeave(getTheNumberOfVacationsLeft.numberOfAccumulatedAnnualLeave(employeeId, systemDate));
		}
		
		//条件：
		//INPUT．勤務状況の詳細設定．項目＝代休残数
		Optional<ItemsSettingDto> hdComDisplayAtr = itemsSetting.stream().filter(c->c.getItem() == WorkStatusItem.HDCOM_DISPLAY_ATR.value && c.isDisplayType()).findAny();
		if(hdComDisplayAtr.isPresent()) {
			//アルゴリズム「18.代休残数表示」を実行する (Thực thi xử lý [18: hiển thị nghỉ bù phép năm còn tồn])
			result.setNumberOfSubstituteHoliday(new RemainingDaysAndTimeDto(getTheNumberOfVacationsLeft.numberOfAccumulatedAnnualLeave(employeeId, systemDate), new AttendanceTime(0)));
		}
		
		//条件：
		//INPUT．勤務状況の詳細設定．項目＝振休残数
		Optional<ItemsSettingDto> hdSubDisplayAtr = itemsSetting.stream().filter(c->c.getItem() == WorkStatusItem.HDSUB_DISPLAY_ATR.value && c.isDisplayType()).findAny();
		if(hdSubDisplayAtr.isPresent()) {
			//アルゴリズム「19.振休残数表示」を実行する(Thực thi xử lý [19:Hiển thi số ngày nghỉ bù còn tồn])
			result.setRemainingHolidays(getTheNumberOfVacationsLeft.numberOfAccumulatedAnnualLeave(employeeId, systemDate));
		}
		
		//条件：
		//INPUT．勤務状況の詳細設定．項目＝子の看護残数
		Optional<ItemsSettingDto> childCareDisplayAtr = itemsSetting.stream().filter(c->c.getItem() == WorkStatusItem.CHILD_CARE_DISPLAY_ATR.value && c.isDisplayType()).findAny();
		if(childCareDisplayAtr.isPresent()) {
			//アルゴリズム「21.子の介護休暇残数表示」を実行する ( thực thi xử lý [21:hiển thị số phép chăm con còn tồn ]
			result.setNursingRemainingNumberOfChildren(new RemainingDaysAndTimeDto(getTheNumberOfVacationsLeft.remainingNumberOfChildNursingLeave(cid, employeeId, datePeriod), new AttendanceTime(0)));
		}
		
		//条件：
		//INPUT．勤務状況の詳細設定．項目＝介護残数
		Optional<ItemsSettingDto> careDisplayAtr = itemsSetting.stream().filter(c->c.getItem() == WorkStatusItem.CARE_DISPLAY_ATR.value && c.isDisplayType()).findAny();
		if(careDisplayAtr.isPresent()) {
			//アルゴリズム「22.介護休暇残数表示」を実行する(Thực thi xử lý [22:Hiển thị số phép Nghỉ chăm nom còn tồn])
			result.setLongTermCareRemainingNumber(new RemainingDaysAndTimeDto(getTheNumberOfVacationsLeft.remainingNumberOfNursingLeave(cid, employeeId, datePeriod), new AttendanceTime(0)));
		}
		
		//アルゴリズム「23.特休残数表示」を実行する(Thực thi xử lý [23:hiển thị số phép đặc biệt còn lại])
		result.setSpecialHolidaysRemainings(getTheNumberOfVacationsLeft.remnantRepresentation(cid, employeeId, datePeriod));
		
		return result;
		
	}
	
	
	
}