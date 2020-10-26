package nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllBanHolidayTogetherQueryDto {
    /** コード */
    private List<String> banHolidayTogetherCode;

    /** 名称 */
    private List<String> banHolidayTogetherName;
}
