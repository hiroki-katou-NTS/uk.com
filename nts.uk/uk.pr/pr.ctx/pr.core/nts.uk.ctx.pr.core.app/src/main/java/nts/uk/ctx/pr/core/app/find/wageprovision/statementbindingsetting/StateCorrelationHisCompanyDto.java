package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompany;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（会社）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorrelationHisCompanyDto {
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * 開始年月
    */
    private int startYearMonth;
    
    /**
    * 終了年月
    */
    private int endYearMonth;
    
    
    public static List<StateCorrelationHisCompanyDto> fromDomain(String cid,StateCorrelationHisCompany domain) {

        List<StateCorrelationHisCompanyDto> listStateCorrelationHisCompanyDto = new ArrayList<StateCorrelationHisCompanyDto>();
        if(domain.getHistory().size() > 0){
            listStateCorrelationHisCompanyDto = domain.getHistory().stream().map(item ->{
                return new StateCorrelationHisCompanyDto(cid,item.identifier(),item.start().v(),item.end().v());
            }).collect(Collectors.toList());
        }

        return listStateCorrelationHisCompanyDto;
    }
}
