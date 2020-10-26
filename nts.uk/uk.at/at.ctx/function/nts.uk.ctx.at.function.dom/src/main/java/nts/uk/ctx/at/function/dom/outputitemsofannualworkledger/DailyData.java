package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;

import java.util.List;

/**
 * 日次データ
 */
@Getter
@Setter
@AllArgsConstructor
public class DailyData {

    // 1列のデータ
    private List<DailyOutputColumnData> lstDailyOutputColumnData;

    // 右の出力名称
    private String rightColumnName;

    // 右の属性
    private CommonAttributesOfForms rightAttribute;

    // 左の出力名称
    private String leftColumnName;

    // 左の属性
    private CommonAttributesOfForms leftAttribute;

}
