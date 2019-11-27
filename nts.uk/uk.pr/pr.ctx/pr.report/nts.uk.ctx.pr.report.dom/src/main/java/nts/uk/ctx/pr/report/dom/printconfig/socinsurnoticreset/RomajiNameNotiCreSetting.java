package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * ローマ字氏名届作成設定
 */
@Getter
public class RomajiNameNotiCreSetting extends AggregateRoot {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * ユーザID
     */
    private String userId;

    /**
     * 住所出力区分
     */
    private BusinessDivision addressOutputClass;

    public RomajiNameNotiCreSetting(String cid, String userId , Integer addressOutputClass) {
        this.cid = cid;
        this.userId = userId;
        this.addressOutputClass = addressOutputClass != null ?  EnumAdaptor.valueOf(addressOutputClass, BusinessDivision.class) : EnumAdaptor.valueOf(0, BusinessDivision.class);
    }

}
