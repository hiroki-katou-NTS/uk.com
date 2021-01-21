package nts.uk.screen.at.app.ksm008.BanHolidayTogether;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether.BanHolidayTogetherCodeNameDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartupInfoBanHolidayDto {
    /**
     * 勤務予定のアラームチェック条件.コード
     */
    private String code;

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
     *   List<同時休日禁止>
     */
    private List<BanHolidayTogetherCodeNameDto> listBanHolidayTogetherCodeName;

    /** 営業日カレンダー種類 **/
    private List<EnumConstant> businessDaysCalendarTypeEnum;
}
