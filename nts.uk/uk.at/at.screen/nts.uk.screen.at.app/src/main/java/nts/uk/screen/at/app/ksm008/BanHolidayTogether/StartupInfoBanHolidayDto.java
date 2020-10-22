package nts.uk.screen.at.app.ksm008.BanHolidayTogether;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartupInfoBanHolidayDto {
    /**
     * 条件名
     */
    private String conditionName;

    /**
     * サブ条件リスト.説明
     */
    private List<String> explanationList;

    /**
     * 対象組織情報.単位
     */
    private int unit;

    /**
     * 対象組織情報.職場ID
     */
    private String workplaceId;

    /**
     * 対象組織情報.職場グループID
     */
    private String workplaceGroupId;

    /**
     * 組織の表示情報.コード
     */
    private String orgCode;

    /**
     * 組織の表示情報.表示名
     */
    private String orgDisplayName;

    /**
     * 同日休日禁止.コード
     */
    private List<String> banHolidayTogetherCode;

    /**
     * 同日休日禁止.名称
     */
    private List<String> banHolidayTogetherName;
}
