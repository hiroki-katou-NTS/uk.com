package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
* 厚生年金種別情報
*/
@Getter
public class WelfarePenTypeInfor extends AggregateRoot {
    
    /**
    * 厚年得喪期間履歴ID
    */
    private String historyId;
    
    /**
    * 坑内員区分
    */
    private PitInsiderDivision undergroundDivision;
    
    public WelfarePenTypeInfor(String historyId, int undergoundDivision) {
        this.historyId = historyId;
        this.undergroundDivision = EnumAdaptor.valueOf(undergoundDivision, PitInsiderDivision.class);
    }
    
}
