package nts.uk.screen.at.app.ksm008.query.c.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.banworktogether.BanWorkTogetherDto;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQueryDto;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceOrg.OrgInfoDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitScreenCDto {

    private OrgInfoDto orgInfo;

    // 勤務予定のアラームチェック条件
    private AlarmCheckConditionsQueryDto alarmCheck;

    // List<同時出勤禁止>
    private List<BanWorkTogetherDto> banWorkTogether;

}
