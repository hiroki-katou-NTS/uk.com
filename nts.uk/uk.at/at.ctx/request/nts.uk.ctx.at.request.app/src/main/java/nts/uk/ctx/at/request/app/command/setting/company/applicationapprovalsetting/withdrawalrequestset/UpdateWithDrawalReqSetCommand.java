package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.withdrawalrequestset;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateWithDrawalReqSetCommand {
	/** * 会社ID */
	private String companyId;

	/** * 勤務時間変更の許可 */
	private int permissionDivision;

	/** * 勤務種類矛盾チェック */
	private int appliDateContrac;

	/** * 実績の表示 */
	private int useAtr;

	/** * 法内法外矛盾チェック */
	private int checkUpLimitHalfDayHD;

	/** * コメント */
	private String pickUpComment;

	/** * 太字 */
	private int pickUpBold;

	/** * 文字色 */
	private String pickUpLettleColor;

	/** * コメント */
	private String deferredComment;

	/** * 太字 */
	private int deferredBold;

	/** * 文字色 */
	private String deferredLettleColor;

	/** * 就業時間帯選択の利用 */
	private int deferredWorkTimeSelect;

	/** * 同時申請必須 */
	private int simulAppliReq;

	/** * 振休先取許可 */
	private int lettleSuperLeave;
	
	/** *同時申請必須 */
	private int simutanAppRequired;
	
	/** * 振休先取許可 */
	private int lettleSuspensionLeave;
}
