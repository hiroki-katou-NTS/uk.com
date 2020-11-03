package nts.uk.screen.at.app.ksm008.query.b.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQueryDto;

import java.util.List;

@Data
@NoArgsConstructor
public class InitScreenDto {

    private List<PersonInfoDto> personInfos;

    private AlarmCheckConditionsQueryDto alarmCheck;

}
