package nts.uk.ctx.at.request.app.find.setting.employment.appemploymentsetting;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.BreakOrRestTime;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.BusinessTripAppWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.TargetWorkTypeByApp;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class TargetWorkTypeByAppDto {
	
	/**
	 * 申請種類
	 */
	private int appType;
	
	/**
	 * 表示する勤務種類を設定する
	 */
	private boolean displayWorkType;
	
	/**
	 * 勤務種類リスト
	 */
	private List<String> workTypeLst;
	
	/**
	 * 振休振出区分
	 */
	private Integer opBreakOrRestTime;
	
	/**
	 * 休暇種類を利用しない
	 */
	private Boolean opHolidayTypeUse;
	
	/**
	 * 休暇申請種類
	 */
	private Integer opHolidayAppType;
	
	/**
	 * 出張申請勤務種類
	 */
	private Integer opBusinessTripAppWorkType;
	
	public static TargetWorkTypeByAppDto fromDomain(TargetWorkTypeByApp targetWorkTypeByApp) {
		return new TargetWorkTypeByAppDto(
				targetWorkTypeByApp.getAppType().value, 
				targetWorkTypeByApp.isDisplayWorkType(), 
				targetWorkTypeByApp.getWorkTypeLst(), 
				targetWorkTypeByApp.getOpBreakOrRestTime().map(x -> x.value).orElse(null), 
				targetWorkTypeByApp.getOpHolidayTypeUse().orElse(null), 
				targetWorkTypeByApp.getOpHolidayAppType().map(x -> x.value).orElse(null), 
				targetWorkTypeByApp.getOpBusinessTripAppWorkType().map(x -> x.value).orElse(null));
	}
	
	public TargetWorkTypeByApp toDomain() {
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
