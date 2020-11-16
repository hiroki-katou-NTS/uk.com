package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.IndependentCalcClassic;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * ValueObject:年間勤務台帳の日次出力項目
 * @author : chinh.hm
 */
@AllArgsConstructor
public class DailyOutputItemsAnnualWorkLedger extends ValueObject {
    // 	順位
    private int rank;

    //  名称
    private OutputItemNameOfAnnualWorkLedger name;

    //  印刷対象フラグ
    private boolean printTtargetFlag;

    // 	単独計算区分
    private IndependentCalcClassic independentCalcClassic;

    // 	日次月次区分
    private DailyMonthlyClassification dailyMonthlyClassification;

    // 	属性
    private CommonAttributesOfForms attribute;

    // 	選択勤怠項目リスト
    private OutputItemDetailAttItem selectionAttendanceItem;

    // 	[C-0] 出力項目を作成する


}
