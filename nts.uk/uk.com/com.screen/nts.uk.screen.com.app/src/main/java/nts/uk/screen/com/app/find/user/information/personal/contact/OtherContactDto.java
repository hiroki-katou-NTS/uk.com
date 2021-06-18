package nts.uk.screen.com.app.find.user.information.personal.contact;

import lombok.Builder;
import lombok.Data;

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
     * 連絡先のアドレス
     */
    private String address;

}
