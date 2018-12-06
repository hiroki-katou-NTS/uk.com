package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
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
public class WageTableContentDto{
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * 支払金額
    */
    private List<ElementsCombinationPaymentAmountDto> payment;
    
    /**
    * 資格グループ設定
    */
    private List<QualificationGroupSettingContentDto> qualificationGroupSetting;

    public static WageTableContentDto fromDomainToDto (WageTableContent domain) {
        WageTableContentDto dto = new WageTableContentDto();
        dto.historyID = domain.getHistoryID();
        dto.payment = domain.getPayment().stream().map(ElementsCombinationPaymentAmountDto::fromDomainToDto).collect(Collectors.toList());
        dto.qualificationGroupSetting = domain.getQualificationGroupSetting().stream().map(QualificationGroupSettingContentDto::fromDomainToDto).collect(Collectors.toList());
        return dto;
    }
}
