package nts.uk.screen.at.app.ksm008.query.i;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeSettingDto;

import java.util.List;

@Data
@AllArgsConstructor
public class WorkingHourListDto {

    List<WorkTimeSettingDto> workTimeSetting;

    MaxDaysOfContinuousWorkTimeCompanyDto MaxDaysOfContinuousWorkTimeCompany;
}

@AllArgsConstructor
class MaxDaysOfContinuousWorkTimeCompanyDto {

    /**
     * code
     */
    private String code;

    /**
     * name
     */
    private String name;

    /**
     * max day
     */
    private Integer maxDaysContiWorktime;
}
