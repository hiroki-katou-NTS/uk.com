package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormulaElementDto {
    public String code;
    public String name;
    public String remarkInformation;

    public FormulaElementDto (String code, String name) {
        this.code = code;
        this.name = name;
    }
}
