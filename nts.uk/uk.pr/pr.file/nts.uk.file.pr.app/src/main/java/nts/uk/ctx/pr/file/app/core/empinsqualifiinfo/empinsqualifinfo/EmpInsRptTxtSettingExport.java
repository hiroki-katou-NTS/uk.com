package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.Value;

@Value
public class EmpInsRptTxtSettingExport {
    /**
     * 会社ID
     */
    private String cid;

    /**
     * ユーザID
     */
    private String userId;

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
