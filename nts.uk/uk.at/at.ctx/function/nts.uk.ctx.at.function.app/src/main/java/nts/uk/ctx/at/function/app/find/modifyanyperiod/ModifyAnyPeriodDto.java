package nts.uk.ctx.at.function.app.find.modifyanyperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.app.command.modifyanyperiod.ModifyAnyPeriodDetailDto;
import nts.uk.ctx.at.function.app.command.modifyanyperiod.ModifyAnyPeriodSheetlDto;

import java.util.List;

@Data
@AllArgsConstructor
public class ModifyAnyPeriodDto {
    private String code;

    private String name;

    private boolean isSetFormatToDefault;

    private List<ModifyAnyPeriodSheetlDto> sheetlDtos;


}
