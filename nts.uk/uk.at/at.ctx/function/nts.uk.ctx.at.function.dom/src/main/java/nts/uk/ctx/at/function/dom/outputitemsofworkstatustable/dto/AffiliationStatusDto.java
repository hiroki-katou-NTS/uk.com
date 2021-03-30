package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AffiliationStatusDto {
    private String employeeID;

    private List<PeriodInformationDto> periodInformation;

    private boolean noEmployment;
}
