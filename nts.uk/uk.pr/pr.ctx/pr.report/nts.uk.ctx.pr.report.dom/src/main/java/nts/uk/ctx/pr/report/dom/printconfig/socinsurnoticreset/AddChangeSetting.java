package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.util.Optional;

/**
* 住所変更届設定
*/
@AllArgsConstructor
@Getter
public class AddChangeSetting extends DomainObject {
    
    /**
    * 短期在留者
    */
    private int shortResident;
    
    /**
    * 海外居住者
    */
    private int livingAbroadAtr;
    
    /**
    * 住民票住所以外居所
    */
    private int residenceOtherResidentAtr;
    
    /**
    * その他
    */
    private int otherAtr;
    
    /**
    * その他理由
    */
    private Optional<AddressChangeNotify> otherReason;
    
    public AddChangeSetting(int shortResident, int livingAbroadAtr, int residenceOtherResidentAtr, int otherAtr, String otherReason) {
        this.shortResident = shortResident;
        this.livingAbroadAtr = livingAbroadAtr;
        this.residenceOtherResidentAtr = residenceOtherResidentAtr;
        this.otherAtr = otherAtr;
        this.otherReason = otherReason == null ? Optional.empty() : Optional.of(new AddressChangeNotify(otherReason));
    }
    
}
