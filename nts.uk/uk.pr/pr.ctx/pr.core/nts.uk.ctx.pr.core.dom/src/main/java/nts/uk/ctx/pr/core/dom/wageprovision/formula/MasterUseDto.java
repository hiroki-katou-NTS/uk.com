package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// mix of 部門, 職位, 職位, 分類給与分類
public class MasterUseDto {
    public String code;
    public String name;
}
