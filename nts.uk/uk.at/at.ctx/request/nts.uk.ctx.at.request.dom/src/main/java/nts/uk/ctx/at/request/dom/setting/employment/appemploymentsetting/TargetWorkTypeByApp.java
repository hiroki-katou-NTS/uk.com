package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.雇用別.雇用別申請承認設定.申請別対象勤務種類
 * @author Doan Duy Hung
 *
 */
@Getter
public class TargetWorkTypeByApp {
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 表示する勤務種類を設定する
	 */
	@Setter
	private boolean displayWorkType;
	
	/**
	 * 勤務種類リスト
	 */
	@Setter
	private List<String> workTypeLst;
	
	/**
	 * 振休振出区分
	 */
	private Optional<BreakOrRestTime> opBreakOrRestTime;
	
	/**
	 * 休暇種類を利用しない
	 */
	private Optional<Boolean> opHolidayTypeUse;
	
	/**
	 * 休暇申請種類
	 */
	private Optional<HolidayAppType> opHolidayAppType;
	
	/**
	 * 出張申請勤務種類
	 */
	@Setter
	private Optional<BusinessTripAppWorkType> opBusinessTripAppWorkType;
	
	public TargetWorkTypeByApp(ApplicationType appType, boolean displayWorkType, List<String> workTypeLst,
			Optional<BreakOrRestTime> opBreakOrRestTime, Optional<Boolean> opHolidayTypeUse,
			Optional<HolidayAppType> opHolidayAppType, Optional<BusinessTripAppWorkType> opBusinessTripAppWorkType) {
		this.appType = appType;
		this.displayWorkType = displayWorkType;
		this.workTypeLst = workTypeLst;
		this.opBreakOrRestTime = opBreakOrRestTime;
		this.opHolidayTypeUse = opHolidayTypeUse;
		this.opHolidayAppType = opHolidayAppType;
		this.opBusinessTripAppWorkType = opBusinessTripAppWorkType;
	}
	
	public static TargetWorkTypeByApp createNew(int appType, boolean displayWorkType, List<String> workTypeLst,
			Integer opBreakOrRestTime, Boolean opHolidayTypeUse,
			Integer opHolidayAppType, Integer opBusinessTripAppWorkType) {
		return new TargetWorkTypeByApp(
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				displayWorkType, 
				workTypeLst, 
				opBreakOrRestTime == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opBreakOrRestTime, BreakOrRestTime.class)), 
				opHolidayTypeUse == null ? Optional.empty() : Optional.of(opHolidayTypeUse), 
				opHolidayAppType == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opHolidayAppType, HolidayAppType.class)), 
				opBusinessTripAppWorkType == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opBusinessTripAppWorkType, BusinessTripAppWorkType.class)));
	}
	
}
