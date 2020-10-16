package nts.uk.ctx.sys.auth.app.command.user.information.personal.contact;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.auth.dom.personal.contact.EmergencyContact;
import nts.uk.ctx.sys.auth.dom.personal.contact.OtherContact;
import nts.uk.ctx.sys.auth.dom.personal.contact.PersonalContact;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Command dto 個人連絡先
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
     * メールアドレスが在席照会に表示するか
     */
    private Boolean isMailAddressDisplay;

    /**
     * 携帯メールアドレス
     */
    private String mobileEmailAddress;

    /**
     * 携帯メールアドレスが在席照会に表示するか
     */
    private Boolean isMobileEmailAddressDisplay;

    /**
     * 携帯電話番号
     */
    private String phoneNumber;

    /**
     * 携帯電話番号が在席照会に表示するか
     */
    private Boolean isPhoneNumberDisplay;

    /**
     * 緊急連絡先１
     */
    private EmergencyContactDto emergencyContact1;

    /**
     * 緊急連絡先１が在席照会に表示するか
     */
    private Boolean isEmergencyContact1Display;

    /**
     * 緊急連絡先２
     */
    private EmergencyContactDto emergencyContact2;

    /**
     * 緊急連絡先２が在席照会に表示するか
     */
    private Boolean isEmergencyContact2Display;

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
        this.otherContacts.addAll(
                otherContacts.stream()
                        .map(otherContact -> OtherContactDto.builder()
                                .otherContactNo(otherContact.getOtherContactNo())
                                .isDisplay(otherContact.getIsDisplay().orElse(null))
                                .address(otherContact.getAddress())
                                .build()
                        ).collect(Collectors.toList())
        );
    }
}
