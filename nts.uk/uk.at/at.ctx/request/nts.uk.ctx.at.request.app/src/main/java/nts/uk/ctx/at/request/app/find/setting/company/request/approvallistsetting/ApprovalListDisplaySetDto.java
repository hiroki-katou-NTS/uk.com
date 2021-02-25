package nts.uk.ctx.at.request.app.find.setting.company.request.approvallistsetting;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.WeekNumberDays;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;

/**
 * 
 * @author hoatt
 *
 */
@Value
public class ApprovalListDisplaySetDto {
	/**事前申請の超過メッセージ*/
	private int advanceExcessMessDisAtr;
	/**休出の事前申請*/
	private int hwAdvanceDisAtr;
	/**休出の実績*/
	private int hwActualDisAtr;
	/**実績超過メッセージ*/
	private int actualExcessMessDisAtr;
	/**残業の事前申請*/
	private int otAdvanceDisAtr;
	/**残業の実績*/
	private int otActualDisAtr;
	/**申請対象日に対して警告表示*/
	private Integer warningDateDisAtr;
	/**申請理由*/
	private int appReasonDisAtr;
	
	public static ApprovalListDisplaySetDto fromDomain(ApprovalListDisplaySetting domain){
		return new ApprovalListDisplaySetDto(domain.getAdvanceExcessMessDisAtr().value,
				domain.getHwAdvanceDisAtr().value, domain.getHwActualDisAtr().value,
				domain.getActualExcessMessDisAtr().value, domain.getOtAdvanceDisAtr().value,
				domain.getOtActualDisAtr().value, domain.getWarningDateDisAtr().v(),
				domain.getAppReasonDisAtr().value);
	}
	
	public ApprovalListDisplaySetting toDomain() {
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
