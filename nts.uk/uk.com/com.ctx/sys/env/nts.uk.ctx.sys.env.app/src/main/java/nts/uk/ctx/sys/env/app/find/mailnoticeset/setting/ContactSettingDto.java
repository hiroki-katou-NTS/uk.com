package nts.uk.ctx.sys.env.app.find.mailnoticeset.setting;

import lombok.Builder;
import lombok.Data;

/**
 * Dto 連絡先の設定
 */
@Data
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
