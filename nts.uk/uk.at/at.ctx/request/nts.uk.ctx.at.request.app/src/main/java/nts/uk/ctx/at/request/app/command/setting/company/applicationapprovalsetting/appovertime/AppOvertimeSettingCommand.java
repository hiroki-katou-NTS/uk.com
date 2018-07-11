package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Data;

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
}
