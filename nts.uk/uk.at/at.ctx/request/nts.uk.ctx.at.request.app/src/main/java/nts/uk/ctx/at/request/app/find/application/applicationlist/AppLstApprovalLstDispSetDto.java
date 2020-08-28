package nts.uk.ctx.at.request.app.find.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppLstApprovalLstDispSet;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppLstApprovalLstDispSetDto {
	
	/**
	 * 開始日表示
	 */
	private String startDateDisp;
	
	/**
	 * 事前事後区分表示
	 */
	private int prePostAtrDisp;
	
	/**
	 * 終了日表示
	 */
	private String endDateDisp;
	
	/**
	 * 所属職場名表示
	 */
	private int workplaceNameDisp;
	
	/**
	 * 申請対象日に対して警告表示
	 */
	private int appDateWarningDisp;
	

	public static AppLstApprovalLstDispSetDto fromDomain(AppLstApprovalLstDispSet displaySet) {
		return new AppLstApprovalLstDispSetDto(displaySet.getStartDateDisp().toString(), 
				displaySet.getPrePostAtrDisp(), 
				displaySet.getEndDateDisp().toString(), 
				displaySet.getWorkplaceNameDisp(), 
				displaySet.getAppDateWarningDisp());
	}
	
	// AnhNM add to domain
	public AppLstApprovalLstDispSet toDomain() {
		return new AppLstApprovalLstDispSet(GeneralDate.fromString(startDateDisp, "yyyy/MM/dd"),
				prePostAtrDisp,
				GeneralDate.fromString(endDateDisp, "yyyy/MM/dd"),
				workplaceNameDisp,
				appDateWarningDisp);
	}
}
