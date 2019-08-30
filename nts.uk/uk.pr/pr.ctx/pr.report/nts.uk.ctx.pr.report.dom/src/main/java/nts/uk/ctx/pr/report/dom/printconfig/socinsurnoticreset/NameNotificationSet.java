package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.util.Optional;

/**
* ローマ字氏名届設定
*/
@AllArgsConstructor
@Getter
public class NameNotificationSet extends DomainObject {
    
    /**
    * その他
    */
    private int other;
    
    /**
    * ローマ字氏名が記載されていない
    */
    private int listed;
    
    /**
    * 住民票
    */
    private ResidentCardCls residentCard;
    
    /**
    * 海外に住所を有している
    */
    private int addressOverseas;
    
    /**
    * 短期在留者
    */
    private int shortResident;
    
    /**
    * その他理由内容
    */
    private Optional<ReasonsForRegisRoman> otherReason;
    
}
