package nts.uk.ctx.bs.person.dom.person.personal.contact;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValueBase;

import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.個人.個人連絡先.個人連絡先
 */
@Getter
public class PersonalContact extends AggregateRoot {

    /**
     * 個人ID
     */
    private String personalId;

    /**
     * メールアドレス
     */
    private Optional<MailAddress> mailAddress;

    /**
     * メールアドレスが在席照会に表示するか
     */
    private Optional<Boolean> isMailAddressDisplay;

    /**
     * 携帯メールアドレス
     */
    private Optional<MailAddress> mobileEmailAddress;

    /**
     * 携帯メールアドレスが在席照会に表示するか
     */
    private Optional<Boolean> isMobileEmailAddressDisplay;

    /**
     * 携帯電話番号
     */
    private Optional<PhoneNumber> phoneNumber;

    /**
     * 携帯電話番号が在席照会に表示するか
     */
    private Optional<Boolean> isPhoneNumberDisplay;

    /**
     * 緊急連絡先１
     */
    private Optional<EmergencyContact> emergencyContact1;

    /**
     * 緊急連絡先１が在席照会に表示するか
     */
    private Optional<Boolean> isEmergencyContact1Display;

    /**
     * 緊急連絡先２
     */
    private Optional<EmergencyContact> emergencyContact2;

    /**
     * 緊急連絡先２が在席照会に表示するか
     */
    private Optional<Boolean> isEmergencyContact2Display;

    /**
     * 他の連絡先
     */
    private List<OtherContact> otherContacts;


    public static PersonalContact createFromMemento(MementoGetter memento) {
        PersonalContact domain = new PersonalContact();
        domain.getMemento(memento);
        return domain;
    }


    public void getMemento(MementoGetter memento) {
        this.personalId = memento.getPersonalId();
        this.mailAddress = Optional.ofNullable(memento.getMailAddress() == null ? null : new MailAddress(memento.getMailAddress()));
        this.isMailAddressDisplay = Optional.ofNullable(memento.getIsMailAddressDisplay() == null ? false : memento.getIsMailAddressDisplay());
        this.mobileEmailAddress = Optional.ofNullable(memento.getMailAddress() == null ? null : new MailAddress(memento.getMobileEmailAddress()));
        this.isMobileEmailAddressDisplay = Optional.ofNullable(memento.getIsMobileEmailAddressDisplay() == null ? false : memento.getIsMobileEmailAddressDisplay());
        this.phoneNumber = Optional.ofNullable(memento.getMailAddress() == null ? null : new PhoneNumber(memento.getPhoneNumber()));
        this.isPhoneNumberDisplay = Optional.ofNullable(memento.getIsPhoneNumberDisplay() == null ? false : memento.getIsPhoneNumberDisplay());
        this.emergencyContact1 = Optional.ofNullable(memento.getEmergencyContact1());
        this.isEmergencyContact1Display = Optional.ofNullable(memento.getIsEmergencyContact1Display() == null ? false : memento.getIsEmergencyContact1Display());
        this.emergencyContact2 = Optional.ofNullable(memento.getEmergencyContact2());
        this.isEmergencyContact2Display = Optional.ofNullable(memento.getIsEmergencyContact2Display() == null ? false : memento.getIsEmergencyContact2Display());
        this.otherContacts = memento.getOtherContacts();
    }


    public void setMemento(MementoSetter memento) {
        memento.setPersonalId(this.personalId);
        memento.setMailAddress(this.mailAddress.map(PrimitiveValueBase::v).orElse(null));
        memento.setIsMailAddressDisplay(this.isMailAddressDisplay.orElse(null));
        memento.setMobileEmailAddress(this.mobileEmailAddress.map(PrimitiveValueBase::v).orElse(null));
        memento.setIsMobileEmailAddressDisplay(this.isMobileEmailAddressDisplay.orElse(null));
        memento.setPhoneNumber(this.phoneNumber.map(PrimitiveValueBase::v).orElse(null));
        memento.setIsPhoneNumberDisplay(this.isPhoneNumberDisplay.orElse(null));
        memento.setEmergencyContact1(this.emergencyContact1.orElse(null));
        memento.setIsEmergencyContact1Display(this.isEmergencyContact1Display.orElse(null));
        memento.setEmergencyContact2(this.emergencyContact2.orElse(null));
        memento.setIsEmergencyContact2Display(this.isEmergencyContact2Display.orElse(null));
        memento.setOtherContacts(this.otherContacts);

    }

    public static interface MementoSetter {
        void setPersonalId(String personalId);

        void setMailAddress(String mailAddress);

        void setIsMailAddressDisplay(Boolean isMailAddressDisplay);

        void setMobileEmailAddress(String mobileEmailAddress);

        void setIsMobileEmailAddressDisplay(Boolean isMobileEmailAddressDisplay);

        void setPhoneNumber(String phoneNumber);

        void setIsPhoneNumberDisplay(Boolean isPhoneNumberDisplay);

        void setEmergencyContact1(EmergencyContact emergencyContact1);

        void setIsEmergencyContact1Display(Boolean isEmergencyContact1Display);

        void setEmergencyContact2(EmergencyContact emergencyContact2);

        void setIsEmergencyContact2Display(Boolean isEmergencyContact2Display);

        void setOtherContacts(List<OtherContact> otherContacts);
    }

    public static interface MementoGetter {
        String getPersonalId();

        String getMailAddress();

        Boolean getIsMailAddressDisplay();

        String getMobileEmailAddress();

        Boolean getIsMobileEmailAddressDisplay();

        String getPhoneNumber();

        Boolean getIsPhoneNumberDisplay();

        EmergencyContact getEmergencyContact1();

        Boolean getIsEmergencyContact1Display();

        EmergencyContact getEmergencyContact2();

        Boolean getIsEmergencyContact2Display();

        List<OtherContact> getOtherContacts();
    }


}
