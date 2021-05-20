package nts.uk.screen.com.app.find.user.information.personal.contact;

import lombok.Data;
import lombok.Builder;
import nts.uk.ctx.bs.person.dom.person.personal.contact.EmergencyContact;
import nts.uk.ctx.bs.person.dom.person.personal.contact.OtherContact;
import nts.uk.ctx.bs.person.dom.person.personal.contact.PersonalContact;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dto 個人連絡先
 */
@Data
@Builder
public class PersonalContactDto implements PersonalContact.MementoSetter {

    /**
     * 個人ID
     */
    private String personalId;

    /**
     * メールアドレス
     */
    private String mailAddress;

    /**
     * 携帯メールアドレス
     */
    private String mobileEmailAddress;

    /**
     * 携帯電話番号
     */
    private String phoneNumber;

    /**
     * 緊急連絡先１
     */
    private EmergencyContactDto emergencyContact1;

    /**
     * 緊急連絡先２
     */
    private EmergencyContactDto emergencyContact2;

    /**
     * 他の連絡先
     */
    private List<OtherContactDto> otherContacts;

    @Override
    public void setEmergencyContact1(EmergencyContact emergencyContact) {
        this.emergencyContact1 = EmergencyContactDto.builder()
                .contactName(emergencyContact.getContactName().v())
                .phoneNumber(emergencyContact.getPhoneNumber().v())
                .remark(emergencyContact.getRemark().v())
                .build();
    }

    @Override
    public void setEmergencyContact2(EmergencyContact emergencyContact) {
        this.emergencyContact2 = EmergencyContactDto.builder()
                .contactName(emergencyContact.getContactName().v())
                .phoneNumber(emergencyContact.getPhoneNumber().v())
                .remark(emergencyContact.getRemark().v())
                .build();
    }

    @Override
    public void setOtherContacts(List<OtherContact> otherContacts) {
        this.otherContacts = otherContacts.stream()
                .map(otherContact -> OtherContactDto.builder()
                        .otherContactNo(otherContact.getOtherContactNo())
                        .address(otherContact.getAddress().v())
                        .build()
                ).collect(Collectors.toList());
    }
}
