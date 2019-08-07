package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;

/**
* 健保番号情報: DTO
*/
@AllArgsConstructor
@Value
public class HealInsurNumberInforDto
{
    
    /**
    * 健保得喪期間履歴ID
    */
    private String historyId;
    
    /**
    * 介護保険番号
    */
    private String careInsurNumber;
    
    /**
    * 健康保険番号
    */
    private String healInsNumber;
    
    
    public static HealInsurNumberInforDto fromDomain(HealInsurNumberInfor domain)
    {
        return new HealInsurNumberInforDto(domain.getHistoryId(), domain.getCareInsurNumber().map(i->i.v()).orElse(null), domain.getHealInsNumber().map(i->i.v()).orElse(null));
    }
    
}
