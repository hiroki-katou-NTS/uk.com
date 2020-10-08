package nts.uk.ctx.sys.auth.dom.personal.contact;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.util.Optional;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.個人.個人連絡先.個人連絡先
 */
@Getter
@Builder
public class OtherContact extends DomainObject {

    /**
     * NO
     */
    private Integer otherContactNo;

    /**
     * 在席照会に表示するか
     */
    private Optional<Boolean> isDisplay;

    /**
     * 連絡先のアドレス
     */
    private String address;


    public OtherContact createFromMemento(MementoGetter memento) {
        OtherContact domain = OtherContact.builder().build();
        domain.getMemento(memento);
        return domain;
    }


    public void getMemento(MementoGetter memento) {
        this.otherContactNo = memento.getOtherContactNo();
        this.isDisplay = Optional.of(memento.getDisplay());
        this.address = memento.getAddress();
    }


    public void setMemento(MementoSetter memento) {
        memento.setOtherContactNo(this.otherContactNo);
        memento.setDisplay(this.isDisplay.orElse(null));
        memento.setAddress(this.address);
    }

    public interface MementoSetter {
        void setOtherContactNo(Integer otherContactNo);

        void setDisplay(Boolean display);

        void setAddress(String address);
    }

    public interface MementoGetter {
        Integer getOtherContactNo();

        Boolean getDisplay();

        String getAddress();
    }
}
