package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 社会保険事業所住所
 */
@Getter
public class SocialInsuranceBusinessAddress extends DomainObject {

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

    /**
     * 電話番号
     */
    private Optional<PhoneNumber> phoneNumber;

    /**
     * 社会保険事業所住所
     *
     * @param postalCode   郵便番号
     * @param address1     住所1
     * @param addressKana1 住所カナ1
     * @param address2     住所2
     * @param addressKana2 住所カナ2
     * @param phoneNumber  電話番号
     */
    public SocialInsuranceBusinessAddress(String postalCode, String address1, String addressKana1, String address2, String addressKana2, String phoneNumber) {
        this.postalCode = Objects.isNull(postalCode) ? Optional.empty() : Optional.of(new PostalCode(postalCode));
        this.address1 = Objects.isNull(address1) ? Optional.empty() : Optional.of(new Address1(address1));
        this.addressKana1 = Objects.isNull(addressKana1) ? Optional.empty() : Optional.of(new AddressKana1(addressKana1));
        this.address2 = Objects.isNull(address2) ? Optional.empty() : Optional.of(new Address2(address2));
        this.addressKana2 = Objects.isNull(addressKana2) ? Optional.empty() : Optional.of(new AddressKana2(addressKana2));
        this.phoneNumber = Objects.isNull(phoneNumber) ? Optional.empty() : Optional.of(new PhoneNumber(phoneNumber));
    }

	public SocialInsuranceBusinessAddress() {
		super();
	}
    
    
    
}
