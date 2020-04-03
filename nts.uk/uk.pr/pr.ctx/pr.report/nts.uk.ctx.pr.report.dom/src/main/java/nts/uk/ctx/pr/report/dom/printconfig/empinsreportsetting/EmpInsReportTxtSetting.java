package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 雇用保険届テキスト出力設定
 */
@Getter
public class EmpInsReportTxtSetting extends AggregateRoot {
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
    private OfficeCls officeAtr;

    /**
     * FD番号
     */
    private FdNumber fdNumber;

    /**
     * 改行コード区分
     */
    private LineFeedCodeAtr lineFeedCodeAtr;

    public EmpInsReportTxtSetting(String cid, String userId, int officeAtr, String fdNumber, int lineFeedCode) {
        this.cid = cid;
        this.userId = userId;
        this.officeAtr = EnumAdaptor.valueOf(officeAtr, OfficeCls.class);
        this.fdNumber = new FdNumber(fdNumber);
        this.lineFeedCodeAtr = EnumAdaptor.valueOf(lineFeedCode, LineFeedCodeAtr.class);
    }

}
