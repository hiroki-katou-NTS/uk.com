package nts.uk.ctx.at.function.app.command.outputworkanualsetting;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.DailyOutputItemsAnnualWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.OutputItemNameOfAnnualWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.DailyMonthlyClassification;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.IndependentCalcClassic;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class DailyOutputItemsCommand {

    // 順位
    private int rank;

    // 名称-> 	帳票の出力項目名称
    private String name;

    // 	印刷対象フラグ
    private boolean printTargetFlag;

    // 	単独計算区分
    private int independentCalcClassic;

    // 	日次月次区分
    private int dailyMonthlyClassification;

    //  項目詳細の属性
    private int attribute;

    //  選択勤怠項目リスト
    private List<OutputItemDetailAttItemCommand> selectedAttendanceItemList;

    public static DailyOutputItemsAnnualWorkLedger toDomain(DailyOutputItemsCommand command){
        return new DailyOutputItemsAnnualWorkLedger(
            command.rank,new OutputItemNameOfAnnualWorkLedger(command.name),
            command.printTargetFlag,
            EnumAdaptor.valueOf(command.independentCalcClassic, IndependentCalcClassic.class),
            EnumAdaptor.valueOf(command.dailyMonthlyClassification, DailyMonthlyClassification.class),
            EnumAdaptor.valueOf(command.attribute, CommonAttributesOfForms.class),
            command.selectedAttendanceItemList.stream().map(x ->
                new OutputItemDetailAttItem(
                    EnumAdaptor.valueOf(x.getOperator(),OperatorsCommonToForms.class),
                    x.getAttendanceItemId())).collect(Collectors.toList())
            );
    }

}
