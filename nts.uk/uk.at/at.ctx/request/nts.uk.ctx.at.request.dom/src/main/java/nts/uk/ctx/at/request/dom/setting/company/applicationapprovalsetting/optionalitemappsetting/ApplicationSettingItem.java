package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.event.OptionalItemNo;

/**
 * 申請設定項目
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApplicationSettingItem {
    /**
     * 任意項目NO
     */
    private OptionalItemNo optionalItemNo;

    /**
     * 表示順
     */
    private int displayOrder;
}
