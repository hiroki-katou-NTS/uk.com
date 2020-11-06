
package nts.uk.screen.at.app.ktgwidget.ktg004;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService.RequireM3;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.DetailedWorkStatusSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetType;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.TopPageDisplayYearMonthEnum;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.WorkStatusItem;
import nts.uk.screen.at.app.workrule.closure.CurrentClosurePeriod;
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
	public void startWorkStatus(String cid, String employeeId, TopPageDisplayYearMonthEnum topPageYearMonthEnum) {
		
		//Get the settings of the specified widget - 指定するウィジェットの設定を取得する 
		WorkStatusSettingDto setting = this.getApprovedDataWidgetStart();
		
		//Get the processing deadline for employees - 社員に対応する処理締めを取得する
		RequireM3 require = ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter);
		Closure closure = ClosureService.getClosureDataByEmployee(require, new CacheCarrier(), employeeId, GeneralDate.today());
		
		//Calculate the period of the specified year and month - 指定した年月の期間を算出する
		DatePeriod datePeriod = ClosureService.getClosurePeriod(closure, closure.getClosureMonth().getProcessingYm());
		
		//Get the target period of the top page - トップページの対象期間を取得する 
		
		
		//Get work status data - 勤務状況のデータを取得する
		
		//Get the number of vacations left - 休暇残数を取得する
		
		//Determine if the login person is the person in charge - ログイン者が担当者か判断する
		Boolean employeeCharge = roleExportRepo.getWhetherLoginerCharge().isEmployeeCharge();
	} 
	
	//トップページの対象期間を取得する
	public CurrentClosurePeriod getTargetPeriodOfTopPage(ClosureId closureId, CurrentMonth currentMonth, YearMonth nextMonth, TopPageDisplayYearMonthEnum topPageYearMonth) {
		if(topPageYearMonth == TopPageDisplayYearMonthEnum.THIS_MONTH_DISPLAY) {
			
		}else if(topPageYearMonth == TopPageDisplayYearMonthEnum.NEXT_MONTH_DISPLAY){
			
		}
		
	}
}