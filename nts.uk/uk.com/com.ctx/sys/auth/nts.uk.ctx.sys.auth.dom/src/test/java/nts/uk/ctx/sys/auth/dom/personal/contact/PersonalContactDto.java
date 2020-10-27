package nts.uk.ctx.sys.auth.dom.personal.contact;

import lombok.Builder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * test dto 個人連絡先
 */
@Builder
public class PersonalContactDto implements PersonalContact.MementoSetter, PersonalContact.MementoGetter {

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
        this.otherContacts = otherContacts.stream()
                .map(otherContact -> OtherContactDto.builder()
                        .otherContactNo(otherContact.getOtherContactNo())
                        .isDisplay(otherContact.getIsDisplay().orElse(null))
                        .address(otherContact.getAddress().v())
                        .build()
                ).collect(Collectors.toList());
    }

	@Override
	public String getPersonalId() {
		return this.personalId;
	}

	@Override
	public String getMailAddress() {
		return this.mailAddress;
	}

	@Override
	public Boolean getIsMailAddressDisplay() {
		return this.isMailAddressDisplay;
	}

	@Override
	public String getMobileEmailAddress() {
		return this.mobileEmailAddress;
	}

	@Override
	public Boolean getIsMobileEmailAddressDisplay() {
		return this.isMobileEmailAddressDisplay;
	}

	@Override
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@Override
	public Boolean getIsPhoneNumberDisplay() {
		return this.isPhoneNumberDisplay;
	}

	@Override
	public EmergencyContact getEmergencyContact1() {
		return  EmergencyContact.builder()
				.contactName(new ContactName(this.emergencyContact1.getContactName()))
				.phoneNumber(new PhoneNumber(this.emergencyContact1.getPhoneNumber()))
				.remark(new Remark(this.emergencyContact1.getRemark()))
				.build();
	}

	@Override
	public Boolean getIsEmergencyContact1Display() {
		return this.isEmergencyContact1Display;
	}

	@Override
	public EmergencyContact getEmergencyContact2() {
		return  EmergencyContact.builder()
				.contactName(new ContactName(this.emergencyContact2.getContactName()))
				.phoneNumber(new PhoneNumber(this.emergencyContact2.getPhoneNumber()))
				.remark(new Remark(this.emergencyContact2.getRemark()))
				.build();
	}

	@Override
	public Boolean getIsEmergencyContact2Display() {
		return this.isEmergencyContact2Display;
	}

	@Override
	public List<OtherContact> getOtherContacts() {
		return this.otherContacts.stream()
				.map(item -> OtherContact.builder()
						.otherContactNo(item.getOtherContactNo())
						.isDisplay(Optional.of(item.getIsDisplay()))
						.address(new OtherContactAddress(item.getAddress()))
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	@Override
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	@Override
	public void setIsMailAddressDisplay(Boolean isMailAddressDisplay) {
		this.isMailAddressDisplay = isMailAddressDisplay;
	}

	@Override
	public void setMobileEmailAddress(String mobileEmailAddress) {
		this.mobileEmailAddress = mobileEmailAddress;
	}

	@Override
	public void setIsMobileEmailAddressDisplay(Boolean isMobileEmailAddressDisplay) {
		this.isMobileEmailAddressDisplay = isMobileEmailAddressDisplay;
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public void setIsPhoneNumberDisplay(Boolean isPhoneNumberDisplay) {
		this.isPhoneNumberDisplay = isPhoneNumberDisplay;
	}

	@Override
	public void setIsEmergencyContact1Display(Boolean isEmergencyContact1Display) {
		this.isEmergencyContact1Display = isEmergencyContact1Display;
	}

	@Override
	public void setIsEmergencyContact2Display(Boolean isEmergencyContact2Display) {
		this.isEmergencyContact2Display = isEmergencyContact2Display;
	}
}
