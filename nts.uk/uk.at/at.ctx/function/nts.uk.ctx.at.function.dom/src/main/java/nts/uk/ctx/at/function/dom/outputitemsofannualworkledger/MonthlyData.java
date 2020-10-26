package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;

import java.util.List;

/**
 * 月次出力１行
 */
@Getter
@Setter
@AllArgsConstructor
public class MonthlyData {

    // 出力項目の値
    private List<MonthlyValue> lstMonthlyValue;

    // 出力項目名称
    private String outputItemName;

    // 属性
    private CommonAttributesOfForms attribute;

}
