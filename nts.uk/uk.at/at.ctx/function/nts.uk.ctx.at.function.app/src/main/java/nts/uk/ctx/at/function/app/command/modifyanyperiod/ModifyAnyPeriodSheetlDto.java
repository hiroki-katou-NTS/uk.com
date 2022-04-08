package nts.uk.ctx.at.function.app.command.modifyanyperiod;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ModifyAnyPeriodSheetlDto {
    private Integer sheetNo;
    private String sheetName;
    private List<ModifyAnyPeriodDetailDto> detailDtoList;
}
