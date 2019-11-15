package nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class EmpInsRptTxtSettingCommand {
    /**
     * 会社ID
     */
    public String cid;

    /**
     * ユーザID
     */
    public String userId;

    /**
     * 事業所区分
     */
    private int officeAtr;

    /**
     * FD番号
     */
    private int fdNumber;

    /**
     * 改行コード
     */
    private int lineFeedCode;
}
