package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 生命保険情報
*/
@Getter
public class LifeInsurance extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cId;
    
    /**
    * コード
    */
    private LifeInsuranceCode lifeInsuranceCode;
    
    /**
    * 名称
    */
    private LifeInsuranceName lifeInsuranceName;
    
    public LifeInsurance(String cid, String lifeInsuranceCode, String lifeInsuranceName) {
        this.cId = cid;
        this.lifeInsuranceCode = new LifeInsuranceCode(lifeInsuranceCode);
        this.lifeInsuranceName = new LifeInsuranceName(lifeInsuranceName);
    }
    
}
