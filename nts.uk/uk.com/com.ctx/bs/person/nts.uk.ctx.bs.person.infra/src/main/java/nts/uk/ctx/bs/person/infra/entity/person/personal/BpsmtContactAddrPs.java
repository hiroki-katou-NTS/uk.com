package nts.uk.ctx.bs.person.infra.entity.person.personal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import nts.uk.ctx.bs.person.dom.person.personal.contact.ContactName;
import nts.uk.ctx.bs.person.dom.person.personal.contact.EmergencyContact;
import nts.uk.ctx.bs.person.dom.person.personal.contact.OtherContact;
import nts.uk.ctx.bs.person.dom.person.personal.contact.OtherContactAddress;
import nts.uk.ctx.bs.person.dom.person.personal.contact.PersonalContact;
import nts.uk.ctx.bs.person.dom.person.personal.contact.PhoneNumber;
import nts.uk.ctx.bs.person.dom.person.personal.contact.Remark;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Entity 個人連絡先
 */
@Data
@Entity
@Table(name = "BPSMT_CONTACT")
@EqualsAndHashCode(callSuper = true)
public class BpsmtContactAddrPs extends UkJpaEntity implements PersonalContact.MementoGetter, PersonalContact.MementoSetter, Serializable{

    private static final long serialVersionUID = 1L;

    // column 排他バージョン
    @Version
    @Column(name = "EXCLUS_VER")
    private long version;

    // column 契約コード
    @Basic(optional = false)
    @Column(name = "CONTRACT_CD")
    private String contractCd;

    // Embedded primary key 個人ID
    @EmbeddedId
    private BpsmtContactAddrPsPK bpsmtContactAddrPsPK;

    // column メールアドレス
    @Basic(optional = true)
    @Column(name = "MAIL_ADDRESS")
    private String mailAddress;

    // column メールアドレスが在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "MAIL_ADDRESS_DISP")
    private Integer isMailAddressDisplay;

    // column 携帯メールアドレス
    @Basic(optional = true)
    @Column(name = "PHONE_MAIL_ADDRESS")
    private String mobileEmailAddress;

    // column 携帯メールアドレスが在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "PHONE_MAIL_ADDRESS_DISP")
    private Integer isMobileEmailAddressDisplay;

    // column 携帯電話番号
    @Basic(optional = true)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    // column 携帯電話番号が在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "PHONE_NUMBER_DISP")
    private Integer isPhoneNumberDisplay;

    // column 緊急連絡先１.メモ
    @Basic(optional = true)
    @Column(name = "URG_CONTACT1_MEMO")
    private String remark1;

    // column 緊急連絡先１.連絡先名
    @Basic(optional = true)
    @Column(name = "URG_CONTACT1_NAME")
    private String contactName1;

    // column 緊急連絡先１.電話番号
    @Basic(optional = true)
    @Column(name = "URG_CONTACT1_PHONE_NUMBER")
    private String phoneNumber1;

    // column 緊急連絡先１が在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "URG_CONTACT1_DISP")
    private Integer isEmergencyContact1Display;

    // column 緊急連絡先2.メモ
    @Basic(optional = true)
    @Column(name = "URG_CONTACT2_MEMO")
    private String remark2;

    // column 緊急連絡先2.連絡先名
    @Basic(optional = true)
    @Column(name = "URG_CONTACT2_NAME")
    private String contactName2;

    // column 緊急連絡先2.電話番号
    @Basic(optional = true)
    @Column(name = "URG_CONTACT2_PHONE_NUMBER")
    private String phoneNumber2;

    // column 緊急連絡先2が在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "URG_CONTACT2_DISP")
    private Integer isEmergencyContact2Display;

    // column 他の連絡先[0].連絡先のアドレス
    @Basic(optional = false)
    @Column(name = "OTHER_CONTACT1_ADDRESS")
    private String address1;

    // column column 他の連絡先[0].在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "OTHER_CONTACT1_DISP")
    private Integer isDisplay1;

    // column 他の連絡先[1].連絡先のアドレス
    @Basic(optional = false)
    @Column(name = "OTHER_CONTACT2_ADDRESS")
    private String address2;

    // column column 他の連絡先[1].在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "OTHER_CONTACT2_DISP")
    private Integer isDisplay2;

    // column 他の連絡先[2].連絡先のアドレス
    @Basic(optional = false)
    
    @Column(name = "OTHER_CONTACT3_ADDRESS")
    private String address3;

    // column column 他の連絡先[2].在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "OTHER_CONTACT3_DISP")
    private Integer isDisplay3;

    // column 他の連絡先[3].連絡先のアドレス
    @Basic(optional = false)
    @Column(name = "OTHER_CONTACT4_ADDRESS")
    private String address4;

    // column column 他の連絡先[3].在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "OTHER_CONTACT4_DISP")
    private Integer isDisplay4;

    // column 他の連絡先[4].連絡先のアドレス
    @Basic(optional = false)
    @Column(name = "OTHER_CONTACT5_ADDRESS")
    private String address5;

    // column column 他の連絡先[4].在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "OTHER_CONTACT5_DISP")
    private Integer isDisplay5;

    @Override
    protected Object getKey() {
        return this.bpsmtContactAddrPsPK;
    }

	@Override
	public void setPersonalId(String personalId) {
        if (this.bpsmtContactAddrPsPK == null) {
            this.bpsmtContactAddrPsPK = new BpsmtContactAddrPsPK();
        }
        this.bpsmtContactAddrPsPK.setPersonalId(personalId);
	}

	@Override
	public void setIsMailAddressDisplay(Boolean isMailAddressDisplay) {
        this.isMailAddressDisplay = isMailAddressDisplay ? 1 : 0;
	}

	@Override
	public void setIsMobileEmailAddressDisplay(Boolean isMobileEmailAddressDisplay) {
        this.isMobileEmailAddressDisplay = isMobileEmailAddressDisplay ? 1 : 0;
	}

	@Override
	public void setIsPhoneNumberDisplay(Boolean isPhoneNumberDisplay) {
        this.isPhoneNumberDisplay = isPhoneNumberDisplay ? 1 : 0;
	}

	@Override
	public void setEmergencyContact1(EmergencyContact emergencyContact1) {
        this.contactName1 = emergencyContact1.getContactName().v();
        this.remark1 = emergencyContact1.getRemark().v();
        this.phoneNumber1 = emergencyContact1.getPhoneNumber().v();
	}

	@Override
	public void setIsEmergencyContact1Display(Boolean isEmergencyContact1Display) {
        this.isEmergencyContact1Display = isEmergencyContact1Display ? 1 : 0;
	}

	@Override
	public void setEmergencyContact2(EmergencyContact emergencyContact2) {
        this.contactName2 = emergencyContact2.getContactName().v();
        this.remark2 = emergencyContact2.getRemark().v();
        this.phoneNumber2 = emergencyContact2.getPhoneNumber().v();
	}

	@Override
	public void setIsEmergencyContact2Display(Boolean isEmergencyContact2Display) {
        this.isEmergencyContact2Display = isEmergencyContact2Display ? 1 : 0;
	}

	@Override
	public void setOtherContacts(List<OtherContact> otherContacts) {
        for (OtherContact otherContact : otherContacts) {
            setOtherContactByNo(otherContact);
        }
	}

    private boolean setOtherContactByNo(OtherContact otherContact) {
        Boolean isDisplay = null;
        switch (otherContact.getOtherContactNo()) {
            case 1:
                this.address1 = otherContact.getAddress().v();
                isDisplay = otherContact.getIsDisplay().orElse(null);
                this.isDisplay1 = isDisplay == null ? null : isDisplay ? 1 : 0;
                return true;
            case 2:
                this.address2 = otherContact.getAddress().v();
                isDisplay = otherContact.getIsDisplay().orElse(null);
                this.isDisplay2 = isDisplay ? 1 : 0;
                return true;
            case 3:
                this.address3 = otherContact.getAddress().v();
                isDisplay = otherContact.getIsDisplay().orElse(null);
                this.isDisplay3 = isDisplay ? 1 : 0;
                return true;
            case 4:
                this.address4 = otherContact.getAddress().v();
                isDisplay = otherContact.getIsDisplay().orElse(null);
                this.isDisplay4 = isDisplay ? 1 : 0;
                return true;
            case 5:
                this.address5 = otherContact.getAddress().v();
                isDisplay = otherContact.getIsDisplay().orElse(null);
                this.isDisplay5 = isDisplay ? 1 : 0;
                return true;
            default:
                return false;
        }
    }

	@Override
	public String getPersonalId() {
        return this.bpsmtContactAddrPsPK.getPersonalId();
	}

	@Override
	public Boolean getIsMailAddressDisplay() {
		return isMailAddressDisplay == 1;
	}

	@Override
	public Boolean getIsMobileEmailAddressDisplay() {
		return isMobileEmailAddressDisplay == 1;
	}

	@Override
	public Boolean getIsPhoneNumberDisplay() {
		return isPhoneNumberDisplay == 1;
	}

	@Override
	public EmergencyContact getEmergencyContact1() {
        return EmergencyContact.builder()
                .contactName(new ContactName(this.contactName1))
                .phoneNumber(new PhoneNumber(this.phoneNumber1))
                .remark(new Remark(this.remark1))
                .build();
	}

	@Override
	public Boolean getIsEmergencyContact1Display() {
		return this.isEmergencyContact1Display == 1;
	}

	@Override
	public EmergencyContact getEmergencyContact2() {
        return EmergencyContact.builder()
                .contactName(new ContactName(this.contactName2))
                .phoneNumber(new PhoneNumber(this.phoneNumber2))
                .remark(new Remark(this.remark2))
                .build();
	}

	@Override
	public Boolean getIsEmergencyContact2Display() {
		return this.isEmergencyContact2Display == 1;
	}

	@Override
	public List<OtherContact> getOtherContacts() {
        List<OtherContact> otherContacts = new ArrayList<>();
        otherContacts.add(
                OtherContact.builder()
                        .otherContactNo(1)
                        .address(new OtherContactAddress(this.address1)) 
                        .isDisplay(Optional.of(this.isDisplay1 == 1))
                        .build()
        );
        otherContacts.add(
                OtherContact.builder()
                        .otherContactNo(2)
                        .address(new OtherContactAddress(this.address2))
                        .isDisplay(Optional.of(this.isDisplay2 == 1))
                        .build()
        );
        otherContacts.add(
                OtherContact.builder()
                        .otherContactNo(3)
                        .address(new OtherContactAddress(this.address3))
                        .isDisplay(Optional.of(this.isDisplay3 == 1))
                        .build()
        );
        otherContacts.add(
                OtherContact.builder()
                        .otherContactNo(4)
                        .address(new OtherContactAddress(this.address4))
                        .isDisplay(Optional.of(this.isDisplay4 == 1))
                        .build()
        );
        otherContacts.add(
                OtherContact.builder()
                        .otherContactNo(5)
                        .address(new OtherContactAddress(this.address5))
                        .isDisplay(Optional.of(this.isDisplay5 == 1))
                        .build()
        );
        return otherContacts;
	}
}
