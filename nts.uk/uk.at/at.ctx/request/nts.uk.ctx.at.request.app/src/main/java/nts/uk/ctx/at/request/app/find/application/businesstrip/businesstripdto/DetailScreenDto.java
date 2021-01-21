package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.businesstrip.service.DetailScreenB;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailScreenDto {

    private BusinessTripInfoOutputDto businessTripInfoOutputDto;

    private BusinessTripDto businessTripDto;

    public static DetailScreenDto fromDomain(DetailScreenB domain) {
        return new DetailScreenDto(
                BusinessTripInfoOutputDto.convertToDto(domain.getBusinessTripInfoOutput()),
                BusinessTripDto.fromDomain(domain.getBusinessTrip())
        );
    }
}
