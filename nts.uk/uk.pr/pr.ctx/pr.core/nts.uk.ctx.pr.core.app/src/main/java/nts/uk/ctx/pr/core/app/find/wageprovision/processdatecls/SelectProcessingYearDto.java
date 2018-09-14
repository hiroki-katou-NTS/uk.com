package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
public class SelectProcessingYearDto {
    private List<SetDaySupportDto> setDaySupportDtoList;
    private List<SpecPrintYmSetDto> specPrintYmSetDtoList;
}
