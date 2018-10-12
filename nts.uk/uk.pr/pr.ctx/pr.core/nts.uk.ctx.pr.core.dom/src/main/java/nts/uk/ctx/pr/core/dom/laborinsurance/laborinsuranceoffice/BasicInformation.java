package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 基本情報
*/
@AllArgsConstructor
@Getter
public class BasicInformation extends DomainObject
{
    
    /**
    * メモ
    */
    private Optional<Memo> notes;
    
    /**
    * 代表者職位
    */
    private RepresentativePosition representativePosition;
    
    /**
    * 代表者名
    */
    private Optional<RepresentativeName> representativeName;
    
    /**
    * 住所
    */
    private LaborInsuranceOfficeAddress streetAddress;
    
    public BasicInformation(String notes, String representativePosition, String representativeName, String phoneNumber, String postalCode, String address1, String addressKana1, String address2, String addressKana2) {
        this.representativeName = representativeName == null ? Optional.empty() : Optional.of(new RepresentativeName(representativeName));
        this.representativePosition = new RepresentativePosition(representativePosition);
        this.streetAddress = new LaborInsuranceOfficeAddress(address1, address2, addressKana1, addressKana2, phoneNumber, postalCode);
        this.notes = notes == null ? Optional.empty() : Optional.of(new Memo(notes));
    }
    
}
