package nts.uk.ctx.at.request.app.command.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripWorkTypes;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessTripWorkTypesCommand {

    private String date;

    private WorkTypeDto workTypeDto;

    public BusinessTripWorkTypes toDomain() {
        return new BusinessTripWorkTypes(
                GeneralDate.fromString(this.date, "yyyy/MM/dd"),
                this.workTypeDto.toDomain()
        );
    }

}
