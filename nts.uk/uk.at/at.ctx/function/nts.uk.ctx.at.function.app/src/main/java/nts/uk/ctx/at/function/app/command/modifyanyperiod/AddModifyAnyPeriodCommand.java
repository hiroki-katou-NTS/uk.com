package nts.uk.ctx.at.function.app.command.modifyanyperiod;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddModifyAnyPeriodCommand {
    private boolean initFormat;
    private String code;
    private String name;
    private BigDecimal sheetNo;
    private String sheetName;
    private List<ModifyAnyPeriodDetailDto> listItemDetail;
}
