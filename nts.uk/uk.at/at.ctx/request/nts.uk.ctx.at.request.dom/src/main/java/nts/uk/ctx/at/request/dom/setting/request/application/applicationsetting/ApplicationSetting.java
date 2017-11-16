package nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.NumDaysOfWeek;
import nts.uk.ctx.at.request.dom.setting.request.application.common.PriorityFLg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.ReflectionFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;

/**
 * @author loivt
 * 申請設定/申請承認設定/申請表示設定/申請制限設定
 */
@Value
public class ApplicationSetting {
	/** 会社ID */
	private String companyID;
	/** 実績修正がロック状態なら申請できない */
	private AppCanAtr appActLockFlg;
	/** 就業確定済の場合申請できない */
	private AppCanAtr appEndWorkFlg;
	/** 日別実績が確認済なら申請できない */
	private AppCanAtr appActConfirmFlg;
	/** 時間外深夜の申請を利用する */
	private AppCanAtr appOvertimeNightFlg;
	/** 月別実績が確認済なら申請できない */
	private AppCanAtr appActMonthConfirmFlg;
	/** 申請理由が必須 */
	private RequiredFlg requireAppReasonFlg;
	/** 事前事後区分表示 */
	private AppDisplayAtr displayPrePostFlg;
	/** 就業時間帯の検索 */
	private AppDisplayAtr displaySearchTimeFlg;
	/** 登録時の手動メール送信の初期値 */
	private UseAtr manualSendMailAtr;

	/** 承認 */
	/** 承認ルートの基準日 */
	private BaseDateFlg baseDateFlg;
	/** 事前申請の超過メッセージ */
	private AppDisplayAtr advanceExcessMessDispAtr;
	/** 休出の事前申請 */
	private AppDisplayAtr hwAdvanceDispAtr;
	/** 休出の実績 */
	private AppDisplayAtr hwActualDispAtr;
	/** 実績超過メッセージ */
	private AppDisplayAtr actualExcessMessDispAtr;
	/** 残業の事前申請 */
	private AppDisplayAtr otAdvanceDispAtr;
	/** 残業の実績 */
	private AppDisplayAtr otActualDispAtr;
	/** 申請対象日に対して警告表示 */
	private NumDaysOfWeek warningDateDispAtr;
	/** 申請理由 */
	private AppDisplayAtr appReasonDispAtr;
	/** 承認時に申請内容を変更できる */
	private AppCanAtr appContentChangeFlg;
	/** 事前申請スケジュール反映 */
	private ReflectionFlg scheReflectFlg;
	/** 反映時刻優先 */
	private PriorityFLg priorityTimeReflectFlg;
	/** 外出打刻漏れの箇所へ出勤退勤時刻を反映する */
	private ReflectionFlg attendentTimeReflectFlg;

	public ApplicationSetting(String companyID, AppCanAtr appActLockFlg, AppCanAtr appEndWorkFlg,
			AppCanAtr appActConfirmFlg, AppCanAtr appOvertimeNightFlg, AppCanAtr appActMonthConfirmFlg,
			RequiredFlg requireAppReasonFlg, AppDisplayAtr displayPrePostFlg, AppDisplayAtr displaySearchTimeFlg,
			UseAtr manualSendMailAtr, BaseDateFlg baseDateFlg, AppDisplayAtr advanceExcessMessDispAtr,
			AppDisplayAtr hwAdvanceDispAtr, AppDisplayAtr hwActualDispAtr, AppDisplayAtr actualExcessMessDispAtr,
			AppDisplayAtr otAdvanceDispAtr, AppDisplayAtr otActualDispAtr, NumDaysOfWeek warningDateDispAtr,
			AppDisplayAtr appReasonDispAtr, AppCanAtr appContentChangeFlg,
			ReflectionFlg scheReflectFlg, PriorityFLg priorityTimeReflectFlg, ReflectionFlg attendentTimeReflectFlg) {
		super();
		this.companyID = companyID;
		this.appActLockFlg = appActLockFlg;
		this.appEndWorkFlg = appEndWorkFlg;
		this.appActConfirmFlg = appActConfirmFlg;
		this.appOvertimeNightFlg = appOvertimeNightFlg;
		this.appActMonthConfirmFlg = appActMonthConfirmFlg;
		this.requireAppReasonFlg = requireAppReasonFlg;
		this.displayPrePostFlg = displayPrePostFlg;
		this.displaySearchTimeFlg = displaySearchTimeFlg;
		this.manualSendMailAtr = manualSendMailAtr;
		this.baseDateFlg = baseDateFlg;
		this.advanceExcessMessDispAtr = advanceExcessMessDispAtr;
		this.hwAdvanceDispAtr = hwAdvanceDispAtr;
		this.hwActualDispAtr = hwActualDispAtr;
		this.actualExcessMessDispAtr = actualExcessMessDispAtr;
		this.otAdvanceDispAtr = otAdvanceDispAtr;
		this.otActualDispAtr = otActualDispAtr;
		this.warningDateDispAtr = warningDateDispAtr;
		this.appReasonDispAtr = appReasonDispAtr;
		this.appContentChangeFlg = appContentChangeFlg;
		this.scheReflectFlg = scheReflectFlg;
		this.priorityTimeReflectFlg = priorityTimeReflectFlg;
		this.attendentTimeReflectFlg = attendentTimeReflectFlg;
	}

}
