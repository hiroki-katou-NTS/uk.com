package nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReason;

@Getter
@AllArgsConstructor
public class DisplayReasonDto {
	/**
	 * 休暇申請の種類
	 */
	private int typeOfLeaveApp;
	
	/**
	 * 定型理由の表示
	 */
	private int displayFixedReason;
	
	/**
	 * 申請理由の表示
	 */
	private int displayAppReason;
	
	public static DisplayReasonDto fromDomain(DisplayReason domain){
		return new DisplayReasonDto(domain.getTypeOfLeaveApp().value, 
									domain.getDisplayFixedReason().value, 
									domain.getDisplayAppReason().value);
	}
}
