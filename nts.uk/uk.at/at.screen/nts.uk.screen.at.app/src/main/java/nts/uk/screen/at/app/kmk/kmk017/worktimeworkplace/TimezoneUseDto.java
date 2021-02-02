package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimezoneUseDto {

    int workNo;
    int startTime;
    int endTime;

    public static TimezoneUseDto fromDomain(TimezoneUse domain) {
        return new TimezoneUseDto(domain.getWorkNo(), domain.getStart().v(),
            domain.getEnd().v());
    }

}
