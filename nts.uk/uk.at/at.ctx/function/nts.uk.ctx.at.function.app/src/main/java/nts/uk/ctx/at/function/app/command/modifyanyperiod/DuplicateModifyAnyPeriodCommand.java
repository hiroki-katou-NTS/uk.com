package nts.uk.ctx.at.function.app.command.modifyanyperiod;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DuplicateModifyAnyPeriodCommand {
    private boolean initFormat;
    private String code;
    private String codeCurrent;
    private String name;
}
