package nts.uk.ctx.sys.auth.dom.personal.contact;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.個人.個人連絡先.個人連絡先
 */
@Getter
@Builder
public class EmergencyContact extends DomainObject {

    /**
     * メモ
     */
    private Remark remark;

    /**
     * 連絡先名
     */
    private ContactName contactName;

    /**
     * 電話番号
     */
    private PhoneNumber phoneNumber;

    public static EmergencyContact createFromMemento(MementoGetter memento) {
        EmergencyContact domain = EmergencyContact.builder().build();
        domain.getMemento(memento);
        return domain;
    }

    public void getMemento(MementoGetter memento) {
        this.remark = new Remark(memento.getRemark());
        this.contactName = new ContactName(memento.getContactName());
        this.phoneNumber = new PhoneNumber(memento.getPhoneNumber());
    }

    public void setMemento(MementoSetter memento) {
        memento.setRemark(this.remark.v());
        memento.setContactName(this.contactName.v());
        memento.setPhoneNumber(this.phoneNumber.v());
    }

    public interface MementoSetter {
        void setRemark(String remark);

        void setContactName(String contactName);

        void setPhoneNumber(String phoneNumber);
    }

    public interface MementoGetter {
        String getRemark();

        String getContactName();

        String getPhoneNumber();
    }
}