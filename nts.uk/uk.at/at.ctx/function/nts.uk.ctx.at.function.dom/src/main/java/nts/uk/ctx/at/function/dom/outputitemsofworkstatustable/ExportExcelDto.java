package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExportExcelDto {
    // 社員コード
    private String employeeCode;

    // 社員名
    private String employeeName;

    // 職場コード
    private String workPlaceCode;

    // 職場名
    private String workPlaceName;

    private double totalOfOneLine;
    // 出力項目名称
    private String outPutItemName;
    // 実績値
    private Double actualValue;
    // 属性
    private CommonAttributesOfForms attributes;
    // 文字値
    private String characterValue;
    // 日付
    private GeneralDate date;
}
