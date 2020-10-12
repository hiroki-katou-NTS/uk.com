package nts.uk.ctx.sys.auth.app.find.personal.contact;

import lombok.Builder;

import java.util.Optional;
/**
 * Dto 個人連絡先
 */
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
