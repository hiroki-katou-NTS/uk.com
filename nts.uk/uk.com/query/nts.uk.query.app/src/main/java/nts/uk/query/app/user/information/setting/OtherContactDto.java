package nts.uk.query.app.user.information.setting;

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
    private Integer no;

    /**
     * 連絡先利用設定
     */
    private Integer contactUsageSetting;

    /**
     * 連絡先名
     */
    private String contactName;
}
