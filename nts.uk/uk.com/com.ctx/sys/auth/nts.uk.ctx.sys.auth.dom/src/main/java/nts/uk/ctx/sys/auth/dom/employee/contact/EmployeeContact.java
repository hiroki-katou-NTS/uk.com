package nts.uk.ctx.sys.auth.dom.employee.contact;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.sys.auth.dom.personal.contact.PhoneNumber;
import nts.uk.ctx.sys.auth.dom.user.MailAddress;

import java.util.Optional;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.社員.社員データ管理.社員連絡先.社員連絡先
 */
@Getter
public class EmployeeContact extends AggregateRoot {
    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * メールアドレス
     */
    private Optional<MailAddress> mailAddress;

    /**
     * メールアドレスが在席照会に表示するか
     */
    private Optional<Boolean> isMailAddressDisplay;

    /**
     * 座席ダイヤルイン
     */
    private Optional<SeatDialIn> seatDialIn;

    /**
     * 座席ダイヤルインが在席照会に表示するか
     */
    private Optional<Boolean> isSeatDialInDisplay;

    /**
     * 座席内線番号
     */
    private Optional<SeatExtensionNumber> seatExtensionNumber;

    /**
     * 座席内線番号が在席照会に表示するか
     */
    private Optional<Boolean> isSeatExtensionNumberDisplay;

    /**
     * 携帯メールアドレス
     */
    private Optional<MailAddress> mobileMailAddress;

    /**
     * 携帯メールアドレスが在席照会に表示するか
     */
    private Optional<Boolean> isMobileMailAddressDisplay;

    /**
     * 携帯電話番号
     */
    private Optional<PhoneNumber> cellPhoneNumber;

    /**
     * 携帯電話番号が在席照会に表示するか
     */
    private Optional<Boolean> isCellPhoneNumberDisplay;

    public static EmployeeContact createFromMemento(MementoGetter memento) {
        EmployeeContact domain = new EmployeeContact();
        domain.getMemento(memento);
        return domain;
    }

    public void getMemento(MementoGetter memento) {
        this.employeeId = memento.getEmployeeId();
        this.mailAddress = Optional.ofNullable( memento.getMailAddress() == null ? null : new MailAddress(memento.getMailAddress()));
        this.isMailAddressDisplay = Optional.ofNullable(memento.getIsMailAddressDisplay() == null ? false : memento.getIsMailAddressDisplay());
        this.seatDialIn = Optional.ofNullable(memento.getSeatDialIn() == null ? null : new SeatDialIn(memento.getSeatDialIn()));
        this.isSeatDialInDisplay = Optional.ofNullable(memento.getIsSeatDialInDisplay() == null ? false : memento.getIsSeatDialInDisplay());
        this.seatExtensionNumber = Optional.ofNullable(memento.getSeatExtensionNumber() == null ? null : new SeatExtensionNumber(memento.getSeatExtensionNumber()));
        this.isSeatExtensionNumberDisplay = Optional.ofNullable(memento.getIsSeatExtensionNumberDisplay() == null ? false : memento.getIsSeatExtensionNumberDisplay());
        this.mobileMailAddress = Optional.ofNullable(memento.getMobileMailAddress() == null ? null : new MailAddress(memento.getMobileMailAddress()));
        this.isMobileMailAddressDisplay = Optional.ofNullable(memento.getIsMobileMailAddressDisplay() == null ? false : memento.getIsMobileMailAddressDisplay());
        this.cellPhoneNumber = Optional.ofNullable(memento.getCellPhoneNumber() == null ? null : new PhoneNumber(memento.getCellPhoneNumber()));
        this.isCellPhoneNumberDisplay = Optional.ofNullable(memento.getIsCellPhoneNumberDisplay() == null ? false : memento.getIsCellPhoneNumberDisplay());
    }

    public void setMemento(MementoSetter memento) {
        memento.setEmployeeId(this.employeeId);
        memento.setMailAddress(this.mailAddress.map(PrimitiveValueBase::v).orElse(null));
        memento.setIsMailAddressDisplay(this.isMailAddressDisplay.orElse(null));
        memento.setSeatDialIn(this.seatDialIn.map(PrimitiveValueBase::v).orElse(null));
        memento.setIsSeatDialInDisplay(this.isSeatDialInDisplay.orElse(null));
        memento.setSeatExtensionNumber(this.seatExtensionNumber.map(PrimitiveValueBase::v).orElse(null));
        memento.setIsSeatExtensionNumberDisplay(this.isSeatExtensionNumberDisplay.orElse(null));
        memento.setMobileMailAddress(this.mobileMailAddress.map(PrimitiveValueBase::v).orElse(null));
        memento.setIsMobileMailAddressDisplay(this.isMobileMailAddressDisplay.orElse(null));
        memento.setCellPhoneNumber(this.cellPhoneNumber.map(PrimitiveValueBase::v).orElse(null));
        memento.setIsCellPhoneNumberDisplay(this.isCellPhoneNumberDisplay.orElse(null));
    }

    public interface MementoSetter {
        void setEmployeeId(String employeeId);

        void setMailAddress(String mailAddress);

        void setIsMailAddressDisplay(Boolean isMailAddressDisplay);

        void setSeatDialIn(String seatDialIn);

        void setIsSeatDialInDisplay(Boolean isSeatDialInDisplay);

        void setSeatExtensionNumber(String seatExtensionNumber);

        void setIsSeatExtensionNumberDisplay(Boolean isSeatExtensionNumberDisplay);

        void setMobileMailAddress(String mobileMailAddress);

        void setIsMobileMailAddressDisplay(Boolean isMobileMailAddressDisplay);

        void setCellPhoneNumber(String cellPhoneNumber);

        void setIsCellPhoneNumberDisplay(Boolean isCellPhoneNumberDisplay);
    }

    public interface MementoGetter {
        String getEmployeeId();

        String getMailAddress();

        Boolean getIsMailAddressDisplay();

        String getSeatDialIn();

        Boolean getIsSeatDialInDisplay();

        String getSeatExtensionNumber();

        Boolean getIsSeatExtensionNumberDisplay();

        String getMobileMailAddress();

        Boolean getIsMobileMailAddressDisplay();

        String getCellPhoneNumber();

        Boolean getIsCellPhoneNumberDisplay();
    }
}
