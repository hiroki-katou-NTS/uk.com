package nts.uk.ctx.at.request.app.command.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripWorkTypes;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessTripWorkTypesCommand {

    private String date;

    private WorkTypeDto workTypeDto;
    
    private WorkTimeSettingDto workTimeSetting;

    public BusinessTripWorkTypes toDomain() {
        return new BusinessTripWorkTypes(
                GeneralDate.fromString(this.date, "yyyy/MM/dd"),
                this.workTypeDto == null ? null : this.workTypeDto.toDomain(), 
                workTimeSetting == null ? null : new WorkTimeSetting(workTimeSetting)
        );
    }

}
