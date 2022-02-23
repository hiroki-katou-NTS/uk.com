package nts.uk.ctx.at.function.app.command.modifyanyperiod;

import lombok.Data;

import java.util.List;

@Data
public class AddModifyAnyPeriodCommand {
    private String businessCode;
    private List<ModifyAnyPeriodDetailDto> listItemDetail;
}
