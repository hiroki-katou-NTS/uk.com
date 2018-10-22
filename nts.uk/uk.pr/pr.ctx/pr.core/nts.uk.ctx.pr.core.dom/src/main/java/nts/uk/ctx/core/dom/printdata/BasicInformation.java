package nts.uk.ctx.core.dom.printdata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.Memo;

/**
* 基本情報
*/
@AllArgsConstructor
@Getter
public class BasicInformation extends DomainObject
{
    
    /**
    * カナ名
    */
    private KanaName kanaName;
    
    /**
    * メモ
    */
    private Optional<Memo> notes;
    
    /**
    * 会社代表者職位
    */
    private Optional<RepresentativePosition> clubRepresentativePosition;
    
    /**
    * 会社代表者名
    */
    private Optional<RepresentativeName> clubRepresentativeName;
    
    /**
    * 紐付け部門
    */
    private Optional<String> linkingDepartment;
    
    /**
    * 法人番号
    */
    private Optional<CorporateNumber> corporateNumber;
    
    /**
    * 住所情報
    */
    private AddressInformation addressInformation;
    
    public BasicInformation(String kanaName, String note, String representativePosition, String representativeName, String linkedDepartment, Integer corporateNumber,AddressInformation addressInformation) {
        this.kanaName = new KanaName(kanaName);
        this.corporateNumber = corporateNumber == null ? Optional.empty() : Optional.of(new CorporateNumber(corporateNumber));
        this.clubRepresentativeName = representativeName == null ? Optional.empty() : Optional.of(new RepresentativeName(representativeName));
        this.clubRepresentativePosition = representativePosition == null ? Optional.empty() : Optional.of(new RepresentativePosition(representativePosition));
        this.notes = note == null ? Optional.empty() : Optional.of(new Memo(note));
        this.addressInformation = new AddressInformation(addressInformation.getAddress1().isPresent() ? addressInformation.getAddress1() : null,
                addressInformation.getAddress2().isPresent() ? addressInformation.getAddress2() : null,
                addressInformation.getAddressKana1().isPresent() ? addressInformation.getAddressKana1() : null,
                addressInformation.getAddressKana2().isPresent() ? addressInformation.getAddressKana2() : null,
                addressInformation.getPhoneNumber().isPresent() ? addressInformation.getPhoneNumber() : null,
                addressInformation.getPostalCode().isPresent() ? addressInformation.getPostalCode() : null);
        this.linkingDepartment = Optional.ofNullable(linkedDepartment);
    }
    
}
