package nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaYearMonthHistory;

import java.util.List;
import java.util.stream.Collectors;

/**
* 給与汎用パラメータ年月履歴: DTO
*/
@AllArgsConstructor
@Value
public class SalGenParaYMHistDto
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
    private int startYearMonth;
    
    /**
    * 終了日
    */
    private int endYearMonth;
    
    
    public static List<SalGenParaYMHistDto> fromDomain(SalGenParaYearMonthHistory domain)
    {
        List<SalGenParaYMHistDto> mSalGenParaDateHistDtos = domain.getHistory().stream().map(item -> {
            return new SalGenParaYMHistDto(domain.getParaNo(),domain.getCID(),item.identifier(), item.start().v(), item.end().v());
        }).collect(Collectors.toList());
        return mSalGenParaDateHistDtos;
    }
    
}
