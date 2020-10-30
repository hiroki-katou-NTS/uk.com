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
    private OtherContactAddress address;
    
}
