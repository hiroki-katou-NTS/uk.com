package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
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
	private boolean displayWorkType;
	
	/**
	 * 勤務種類リスト
	 */
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
	private Optional<BusinessTripAppWorkType> opBusinessTripAppWorkType;
	
}
