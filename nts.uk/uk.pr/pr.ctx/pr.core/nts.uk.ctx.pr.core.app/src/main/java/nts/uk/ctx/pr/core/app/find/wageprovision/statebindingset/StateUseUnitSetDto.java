package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateUseUnitSet;

/**
* 明細書利用単位設定: DTO
*/
@AllArgsConstructor
@Value
public class StateUseUnitSetDto {
    
    /**
    * 会社ID
    */
    private String companyID;
    
    /**
    * マスタ利用区分
    */
    private int masterUse;
    
    /**
    * 個人利用区分
    */
    private int individualUse;
    
    /**
    * 利用マスタ
    */
    private Integer usageMaster;
    
    
    public static StateUseUnitSetDto fromDomain(StateUseUnitSet domain) {
        return new StateUseUnitSetDto(domain.getCompanyID(), domain.getMasterUse().value, domain.getIndividualUse().value, domain.getUsageMaster().map(i->i.value).orElse(null));
    }
    
}
