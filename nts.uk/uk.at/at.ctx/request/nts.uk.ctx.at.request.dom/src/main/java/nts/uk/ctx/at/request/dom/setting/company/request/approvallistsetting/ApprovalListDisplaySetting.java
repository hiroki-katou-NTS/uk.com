package nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.WeekNumberDays;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;

/**
 * 承認一覧表示設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class ApprovalListDisplaySetting extends DomainObject {
	
	/**
	 * 事前申請の超過メッセージ
	 */
	private DisplayAtr advanceExcessMessDisAtr;
	
	/**
	 * 休出の事前申請
	 */
	private DisplayAtr hwAdvanceDisAtr;
	
	/**
	 * 休出の実績
	 */
	private DisplayAtr hwActualDisAtr;
	
	/**
	 * 実績超過メッセージ
	 */
	private DisplayAtr actualExcessMessDisAtr;
	
	/**
	 * 残業の事前申請
	 */
	private DisplayAtr otAdvanceDisAtr;
	
	/**
	 * 残業の実績
	 */
	private DisplayAtr otActualDisAtr;
	
	/**
	 * 申請対象日に対して警告表示
	 */
	private WeekNumberDays warningDateDisAtr;
	
	/**
	 * 申請理由
	 */
	private DisplayAtr appReasonDisAtr;
	
	public static ApprovalListDisplaySetting toDomain(Integer advanceExcessMessDisAtr, Integer hwAdvanceDisAtr, 
			Integer hwActualDisAtr, Integer actualExcessMessDisAtr, 
			Integer otAdvanceDisAtr, Integer otActualDisAtr, 
			Integer warningDateDisAtr, Integer appReasonDisAtr){
		return new ApprovalListDisplaySetting(
				EnumAdaptor.valueOf(advanceExcessMessDisAtr, DisplayAtr.class), 
				EnumAdaptor.valueOf(hwAdvanceDisAtr, DisplayAtr.class), 
				EnumAdaptor.valueOf(hwActualDisAtr, DisplayAtr.class), 
				EnumAdaptor.valueOf(actualExcessMessDisAtr, DisplayAtr.class), 
				EnumAdaptor.valueOf(otAdvanceDisAtr, DisplayAtr.class), 
				EnumAdaptor.valueOf(otActualDisAtr, DisplayAtr.class), 
				new WeekNumberDays(warningDateDisAtr), 
				EnumAdaptor.valueOf(appReasonDisAtr, DisplayAtr.class));
	}
	
}
