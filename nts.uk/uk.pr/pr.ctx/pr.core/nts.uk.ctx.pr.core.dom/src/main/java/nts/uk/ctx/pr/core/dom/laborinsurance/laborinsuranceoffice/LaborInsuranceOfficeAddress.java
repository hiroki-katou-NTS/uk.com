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
* 労働保険事業所住所
*/
@AllArgsConstructor
@Getter
public class LaborInsuranceOfficeAddress extends DomainObject
{
    
    /**
    * 電話番号
    */
    private Optional<PhoneNumber> phoneNumber;
    
    /**
    * 郵便番号
    */
    private Optional<PostalCode> postalCode;
    
    /**
    * 住所1
    */
    private Optional<Address1> address1;
    
    /**
    * 住所カナ1
    */
    private Optional<AddressKana1> addressKana1;
    
    /**
    * 住所2
    */
    private Optional<Address2> address2;
    
    /**
    * 住所カナ2
    */
    private Optional<AddressKana2> addressKana2;
    
    public LaborInsuranceOfficeAddress(String address1, String address2, String addressKana1,  String addressKana2, String phoneNumber, String postalCode) {
        this.postalCode = postalCode == null ? Optional.empty() : Optional.of(new PostalCode(postalCode));
        this.address1 = address1 == null ? Optional.empty() : Optional.of(new Address1(address1));
        this.addressKana1 = addressKana1 == null ? Optional.empty() : Optional.of(new AddressKana1(addressKana1));
        this.address2 = address2 == null ? Optional.empty() : Optional.of(new Address2(address2));
        this.addressKana2 = addressKana2 == null ? Optional.empty() : Optional.of(new AddressKana2(addressKana2));
        this.phoneNumber = phoneNumber == null ? Optional.empty() : Optional.of(new PhoneNumber(phoneNumber));
    }
}
