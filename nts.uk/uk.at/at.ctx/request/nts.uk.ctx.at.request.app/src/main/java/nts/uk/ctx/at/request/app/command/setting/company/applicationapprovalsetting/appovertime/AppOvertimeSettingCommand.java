package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.BreakReflect;
import nts.uk.ctx.at.request.dom.application.UseAtr;

@Data
@AllArgsConstructor
public class AppOvertimeSettingCommand {
    private String cid;
    /**
	 * 勤種変更可否フラグ
	 */
    private int workTypeChangeFlag;
    /**
	 * フレックス超過利用設定
	 */
    private int flexJExcessUseSetAtr;
    /**
     * 実績超過打刻優先設定
     */
    private int priorityStampSetAtr;
    /**
	 * 勤務種類、就業時間帯、勤務時間
	 */
	private int preTypeSiftReflectFlg;
	/**
	 * 残業時間
	 */
	private int preOvertimeReflectFlg;
	/**
	 * 勤務種類、就業時間帯
	 */
	private int postTypeSiftReflectFlg;
	/**
	 * 勤務時間
	 */
	private int postWorktimeReflectFlg;
	/**
	 * 休憩時間
	 */
	private int postBreakReflectFlg;
	/**
	 * 休憩区分
	 */
	private int restAtr;
}
