package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;

@AllArgsConstructor
public class WithDrawalReqSetDto {
	 /* * 会社ID */
	 public String companyId;
	
	 /* * 勤務時間変更の許可 */
	 public int permissionDivision;
	
	 /* * 勤務種類矛盾チェック */
	 public int appliDateContrac;
	
	 /* * 実績の表示 */
	 public int useAtr;
	
	 /* * 法内法外矛盾チェック  */
	 public int checkUpLimitHalfDayHD;
	
	 /* * コメント */
	 public String pickUpComment;
	
	 /* * 太字  */
	 public int pickUpBold;
	
	 /* * 文字色  */
	 public String pickUpLettleColor;
	
	 /* * コメント  */
	 public String deferredComment;
	
	 /* * 太字  */
	 public int deferredBold;
	
	 /* * 文字色 */
	 public String deferredLettleColor;
	
	 /* * 就業時間帯選択の利用  */
	 public int deferredWorkTimeSelect;
	
	 /* * 同時申請必須  */
	 public int simulAppliReq;
	
	 /* * 振休先取許可  */
	 public int lettleSuperLeave;
 
	 public static WithDrawalReqSetDto fromDomain(WithDrawalReqSet withDrawalReqSet){
		  return new WithDrawalReqSetDto(
		    withDrawalReqSet.getCompanyId(), 
		    withDrawalReqSet.getPermissionDivision().value, 
		    withDrawalReqSet.getAppliDateContrac().value,
		    withDrawalReqSet.getUseAtr().value,
		    withDrawalReqSet.getCheckUpLimitHalfDayHD().value,
		    withDrawalReqSet.getPickUpComment().isPresent() ? withDrawalReqSet.getPickUpComment().get().v() : "",
		    withDrawalReqSet.getPickUpBold().value,
		    withDrawalReqSet.getPickUpLettleColor(),
		    withDrawalReqSet.getDeferredComment().isPresent() ? withDrawalReqSet.getDeferredComment().get().v() : "",
		    withDrawalReqSet.getDeferredBold().value,
		    withDrawalReqSet.getDeferredLettleColor(),
		    withDrawalReqSet.getDeferredWorkTimeSelect().value,
		    withDrawalReqSet.getSimulAppliReq().value,
		    withDrawalReqSet.getLettleSuperLeave().value);
	 }
}