package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@Getter
public class EmpAffInfoExportDto {
    private List<AffiliationStatusDto> affiliationStatus;
}
