package nts.uk.screen.at.app.kwr004.b;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class AnnualOutputSettingDto {

    //コード
    private String code;

    // 名称
    private String name;

    // 日次出力項目リスト->DAILY
    private List<DailyOutputItemsDto> dailyOutputItems;

    // 	月次出力項目リスト-MONTHLY
    private List<MonthlyOutputItemsDto> monthlyOutputItems;

    public static AnnualOutputSettingDto setData(AnnualWorkLedgerOutputSetting domain){
        List<DailyOutputItemsDto> dailyOutputItems = domain.getDailyOutputItemList().stream().map(x ->
            new DailyOutputItemsDto(x.getRank(),x.isPrintTargetFlag(),x.getName().v(),x.getItemDetailAttributes().value,x.getIndependentCalcClassic().value)).
            collect(Collectors.toList());

        List<MonthlyOutputItemsDto> monthlyOutputItems = domain.getMonthlyOutputItemList().stream().map(x ->
            new MonthlyOutputItemsDto(x.getRank(),x.isPrintTargetFlag(),x.getName().v(),x.getItemDetailAttributes().value)).
            collect(Collectors.toList());
        return new AnnualOutputSettingDto(domain.getCode().v(),domain.getName().v(),dailyOutputItems, monthlyOutputItems);
    }
}
