package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.UseAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.CheckUper;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.ContractCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.Weight;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WorkUse;

@AllArgsConstructor
public class WithDrawalReqSetDto {
	/** * 会社ID */
	public String companyId;

	/** * 勤務時間変更の許可 */
	public AllowAtr permissionDivision;

	/** * 勤務種類矛盾チェック */
	public ContractCheck appliDateContrac;

	/** * 実績の表示 */
	public UseAtr useAtr;

	/** * 法内法外矛盾チェック */
	public CheckUper checkUpLimitHalfDayHD;

	/** * コメント */
	public String pickUpComment;

	/** * 太字 */
	public Weight pickUpBold;

	/** * 文字色 */
	public String pickUpLettleColor;

	/** * コメント */
	public String deferredComment;

	/** * 太字 */
	public Weight deferredBold;

	/** * 文字色 */
	public String deferredLettleColor;

	/** * 就業時間帯選択の利用 */
	public WorkUse deferredWorkTimeSelect;

	/** * 同時申請必須 */
	public AllowAtr simulAppliReq;

	/** * 振休先取許可 */
	public AllowAtr lettleSuperLeave;
	
	public static WithDrawalReqSetDto fromDomain(WithDrawalReqSet withDrawalReqSet){
		return new WithDrawalReqSetDto(
				withDrawalReqSet.getCompanyId(), 
				withDrawalReqSet.getPermissionDivision(), 
				withDrawalReqSet.getAppliDateContrac(),
				withDrawalReqSet.getUseAtr(),
				withDrawalReqSet.getCheckUpLimitHalfDayHD(),
				withDrawalReqSet.getPickUpComment(),
				withDrawalReqSet.getPickUpBold(),
				withDrawalReqSet.getPickUpLettleColor(),
				withDrawalReqSet.getDeferredComment(),
				withDrawalReqSet.getDeferredBold(),
				withDrawalReqSet.getDeferredLettleColor(),
				withDrawalReqSet.getDeferredWorkTimeSelect(),
				withDrawalReqSet.getSimulAppliReq(),
				withDrawalReqSet.getLettleSuperLeave());
	}
}
