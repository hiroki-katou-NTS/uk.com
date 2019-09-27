package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
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

    public NameNotificationSet(int other, int listed, Integer residentCard, int addressOverseas, int shortResident, String otherReason) {
        this.other = other;
        this.listed = listed;
        this.residentCard = residentCard != null ? EnumAdaptor.valueOf(residentCard, ResidentCardCls.class) : EnumAdaptor.valueOf(1, ResidentCardCls.class);
        this.addressOverseas = addressOverseas;
        this.shortResident = shortResident;
        this.otherReason = otherReason == null ? Optional.empty() : Optional.of(new ReasonsForRegisRoman(otherReason));
    }
}
