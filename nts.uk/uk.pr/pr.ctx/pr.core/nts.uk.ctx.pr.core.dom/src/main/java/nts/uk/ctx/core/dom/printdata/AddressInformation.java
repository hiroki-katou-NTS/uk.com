package nts.uk.ctx.core.dom.printdata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.PostalCode;

/**
* 住所情報
*/
@AllArgsConstructor
@Getter
public class AddressInformation extends DomainObject
{
    
    /**
    * 住所１
    */
    private Optional<Address1> address1;
    
    /**
    * 住所２
    */
    private Optional<Address2> address2;
    
    /**
    * 住所カナ１
    */
    private Optional<AddressKana1> addressKana1;
    
    /**
    * 住所カナ２
    */
    private Optional<AddressKana2> addressKana2;
    
    /**
    * 電話番号
    */
    private Optional<PhoneNumber> phoneNumber;
    
    /**
    * 郵便番号
    */
    private Optional<PostalCode> postalCode;
    
    public AddressInformation(String address1, String address2, String addressKana1, String addressKana2, String phoneNumber, String postalCode) {
        this.address1 = address1 == null ? Optional.empty() : Optional.of(new Address1(address1));
        this.address2 = address2 == null ? Optional.empty() : Optional.of(new Address2(address2));
        this.addressKana1 = addressKana1 == null ? Optional.empty() : Optional.of(new AddressKana1(addressKana1));
        this.addressKana2 = addressKana2 == null ? Optional.empty() : Optional.of(new AddressKana2(addressKana2));
        this.postalCode = postalCode == null ? Optional.empty() : Optional.of(new PostalCode(postalCode));
        this.phoneNumber = phoneNumber == null ? Optional.empty() : Optional.of(new PhoneNumber(phoneNumber));
    }
    
}
