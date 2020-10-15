package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.IndependentCalculationClassification;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.List;

/**
 *ValueObject: 出力項目
 * @author chinh.hm
 */
@Getter
@Setter
public class OutputItem extends ValueObject {
    // 順位
    private int rank;

    // 名称-> 	帳票の出力項目名称
    private FormOutputItemName name;

    // 	印刷対象フラグ
    private boolean printTargetFlag;

    // 	単独計算区分
    private IndependentCalculationClassification independentCalculaClassification;

    // 	日次月次区分
    private DailyMonthlyClassification dailyMonthlyClassification;

    //  項目詳細の属性
    private CommonAttributesOfForms itemDetailAttributes;

    //  選択勤怠項目リスト
    private List<OutputItemDetailSelectionAttendanceItem> selectedAttendanceItemList;


}
