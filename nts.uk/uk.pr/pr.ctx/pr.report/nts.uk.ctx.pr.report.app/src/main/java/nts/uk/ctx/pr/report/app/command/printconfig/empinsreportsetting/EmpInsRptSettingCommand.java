package nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class EmpInsRptSettingCommand {
    /**
     * 提出氏名
     */
    private int submitNameAtr;

    /**
     * 出力順
     */
    private int outputOrderAtr;

    /**
     * 事業所区分
     */
    private int officeClsAtr;

    /**
     * マイナンバー印字区分
     */
    private int myNumberClsAtr;

    /**
     * 変更後氏名印字区分
     */
    private int nameChangeClsAtr;
}
