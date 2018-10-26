package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 賃金テーブル
*/
@Getter
public class WageTable extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cID;
    
    /**
    * 賃金テーブルコード
    */
    private WageTableCode wageTableCode;
    
    /**
    * 賃金テーブル名
    */
    private WageTableName wageTableName;
    
    /**
    * 要素設定
    */
    private ElementSetting elementSetting;
    
    /**
    * 備考情報
    */
    private Optional<Memo> remarkInformation;
    
    /**
    * 要素情報
    */
    private ElementInformation elementInformation;
    
    public WageTable(String cid, String wageTableCode, String wageTableName, Integer firstFixedElement, String firstAdditionalElement, Integer firstMasterNumericCls, Integer secondFixedElement, String secondAdditionalElement, Integer secondMasterNumericCls, Integer thirdFixedElement, String thirdAdditionalElement, Integer thirdMasterNumericCls, int elementSetting, String remarkInformation) {
        this.cID = cid;
        this.wageTableCode = new WageTableCode(wageTableCode);
        this.wageTableName = new WageTableName(wageTableName);
        this.elementInformation = new ElementInformation(firstFixedElement, firstAdditionalElement, firstMasterNumericCls, secondFixedElement, secondAdditionalElement, secondMasterNumericCls, thirdFixedElement, thirdAdditionalElement, thirdMasterNumericCls);
        this.elementSetting = EnumAdaptor.valueOf(elementSetting, ElementSetting.class);
        this.remarkInformation = remarkInformation == null ? Optional.empty() : Optional.of(new Memo(remarkInformation));
    }
    
}
