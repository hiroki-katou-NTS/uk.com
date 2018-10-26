package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.ElementsCombinationPaymentAmountDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.QualificationGroupSettingContentDto;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingContent;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContent;

import java.util.List;
import java.util.stream.Collectors;

/**
* 賃金テーブル内容
*/
@Data
@NoArgsConstructor
public class WageTableContentCommand {
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * 支払金額
    */
    private List<ElementsCombinationPaymentAmountCommand> payment;
    
    /**
    * 資格グループ設定
    */
    private List<QualificationGroupSettingContentCommand> qualificationGroupSetting;

    public WageTableContent fromCommandToDomain () {
        return new WageTableContent(historyID, payment.stream().map(i -> i.fromCommandToDomain()).collect(Collectors.toList()), qualificationGroupSetting.stream().map(i -> i.fromCommandToDomain()).collect(Collectors.toList()));
    }
}
