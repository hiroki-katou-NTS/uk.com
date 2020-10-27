package nts.uk.ctx.sys.auth.app.command.user.information.personal.contact;

import lombok.Builder;
import lombok.Data;

/**
 * Command dto 個人連絡先
 */
@Data
@Builder
public class OtherContactDto  {
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
