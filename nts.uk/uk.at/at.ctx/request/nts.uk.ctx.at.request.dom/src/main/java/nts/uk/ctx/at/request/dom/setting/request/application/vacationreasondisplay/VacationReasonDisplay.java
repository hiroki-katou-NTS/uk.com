package nts.uk.ctx.at.request.dom.setting.request.application.vacationreasondisplay;

import lombok.Value;
import nts.uk.ctx.at.request.dom.setting.request.application.common.VacationAppType;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;

@Value
public class VacationReasonDisplay {
	private String companyID;
	/**
	 * 休暇申請の種類
	 */
	private VacationAppType vacationAppType;
	/**
	 * 定型理由の表示
	 */
	private AppDisplayAtr dispFixedReasonFlg;
	/**
	 * 申請理由の表示
	 */
	private AppDisplayAtr dispAppReasonFlg;

	public VacationReasonDisplay(String companyID, VacationAppType vacationAppType, AppDisplayAtr dispFixedReasonFlg,
			AppDisplayAtr dispAppReasonFlg) {
		super();
		this.companyID = companyID;
		this.vacationAppType = vacationAppType;
		this.dispFixedReasonFlg = dispFixedReasonFlg;
		this.dispAppReasonFlg = dispAppReasonFlg;
	}

}
