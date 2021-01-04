package nts.uk.query.app.user.information.setting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto 連絡先の設定
 */
@Getter
@Setter
@Builder
public class ContactSettingDto {
    /**
     * 連絡先利用設定
     */
    private Integer contactUsageSetting;

    /**
     * 更新可能
     */
    private Integer updatable;
}
