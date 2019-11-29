package nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class EmpInsRptTxtSettingCommand {

    /**
     * 事業所区分
     */
    private int officeAtr;

    /**
     * FD番号
     */
    private String fdNumber;

    /**
     * 改行コード
     */
    private int lineFeedCode;
}
