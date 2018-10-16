package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaDateHistory;

import java.util.List;
import java.util.stream.Collectors;

/**
* 給与汎用パラメータ年月日履歴: DTO
*/
@AllArgsConstructor
@Value
public class SalGenParaDateHistDto
{
    
    /**
    * パラメータNo
    */
    private String paraNo;
    
    /**
    * 会社ID
    */
    private String cID;
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 開始日
    */
    private GeneralDate startDate;
    
    /**
    * 終了日
    */
    private GeneralDate endDate;
    
    
    public static   List<SalGenParaDateHistDto> fromDomain(SalGenParaDateHistory domain)
    {
        List<SalGenParaDateHistDto> mSalGenParaDateHistDtos = domain.getDateHistoryItem().stream().map(item -> {
            return new SalGenParaDateHistDto(domain.getParaNo(),domain.getCId(),item.identifier(), item.start(), item.end());
        }).collect(Collectors.toList());
        return mSalGenParaDateHistDtos;
    }
    
}
