package nts.uk.ctx.at.function.app.command.modifyanyperiod;

import lombok.Data;

import java.util.List;

@Data
public class AddModifyAnyPeriodCommand {
    private boolean initFormat;
    private String code;
    private String name;
    private Integer sheetNo;
    private String sheetName;
    private List<ModifyAnyPeriodDetailDto> listItemDetail;
}
