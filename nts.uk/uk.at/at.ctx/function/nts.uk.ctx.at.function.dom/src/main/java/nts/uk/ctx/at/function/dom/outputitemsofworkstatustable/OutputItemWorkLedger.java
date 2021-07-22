package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.OutputItemNameOfAnnualWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.IndependentCalcClassic;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class OutputItemWorkLedger extends ValueObject {
    // 順位
    private int rank;

    // 名称-> 	年間勤務台帳の出力項目名称月次
    private OutputItemNameOfAnnualWorkLedger name;

    // 	印刷対象フラグ
    private boolean printTargetFlag;

    // 	単独計算区分
    private IndependentCalcClassic independentCalcClassic;

    // 	日次月次区分
    private DailyMonthlyClassification dailyMonthlyClassification;

    //  項目詳細の属性
    private CommonAttributesOfForms itemDetailAttributes;

    //  選択勤怠項目リスト
    private List<OutputItemDetailAttItem> selectedAttendanceItemList;
}
