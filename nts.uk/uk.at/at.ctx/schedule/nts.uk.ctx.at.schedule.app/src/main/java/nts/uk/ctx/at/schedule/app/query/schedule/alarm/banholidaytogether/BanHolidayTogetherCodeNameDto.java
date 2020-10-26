package nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanHolidayTogetherCodeNameDto {
    /** コード */
    private String banHolidayTogetherCode;

    /** 名称 */
    private String banHolidayTogetherName;
}
