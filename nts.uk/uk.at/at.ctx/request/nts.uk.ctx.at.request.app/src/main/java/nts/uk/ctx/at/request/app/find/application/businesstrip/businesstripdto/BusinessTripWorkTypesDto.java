package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripWorkTypes;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusinessTripWorkTypesDto {

    private String date;

    private WorkTypeDto workTypeDto;

    public static BusinessTripWorkTypesDto fromDomain(BusinessTripWorkTypes domain) {
        return new BusinessTripWorkTypesDto(
                domain.getDate().toString(),
                domain.getWorkType() != null ? WorkTypeDto.fromDomain(domain.getWorkType()) : null
        );
    }

    public BusinessTripWorkTypes toDomain() {
        return new BusinessTripWorkTypes(
                GeneralDate.fromString(this.date, "yyyy/MM/dd"),
                this.workTypeDto == null ? null : this.workTypeDto.toDomain()
        );
    }

}
