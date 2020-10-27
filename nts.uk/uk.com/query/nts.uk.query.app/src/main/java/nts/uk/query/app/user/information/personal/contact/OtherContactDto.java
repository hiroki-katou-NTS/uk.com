package nts.uk.query.app.user.information.personal.contact;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.auth.dom.personal.contact.OtherContact;

/**
 * Dto 他の連絡先
 */
@Data
@Builder
public class OtherContactDto {
    /**
     * NO
     */
    private Integer otherContactNo;

    /**
     * 在席照会に表示するか
     */
    private Boolean isDisplay;

    /**
     * 連絡先のアドレス
     */
    private String address;

}
