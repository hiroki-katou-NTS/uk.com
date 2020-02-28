package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomworkstlinfor;


import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorWorkFormInfo;

@AllArgsConstructor
@Value
public class CorWorkFormInfoDto {

    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 被保険者区分
     */
    private int insPerCls;

    public static CorWorkFormInfoDto fromDomain(CorWorkFormInfo domain){
        return new CorWorkFormInfoDto(domain.getHistoryId(),domain.getInsPerCls().value);
    }
}
