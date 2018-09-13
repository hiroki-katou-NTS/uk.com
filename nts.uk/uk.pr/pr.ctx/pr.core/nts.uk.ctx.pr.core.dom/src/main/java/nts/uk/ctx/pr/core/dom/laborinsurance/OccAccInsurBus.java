package nts.uk.ctx.pr.core.dom.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.List;

/**
 * 労災保険事業
 */
@AllArgsConstructor
@Getter
@Setter
public class OccAccInsurBus extends AggregateRoot {

    /**
     * 会社ID
     */
    private String cid;
    /**
     * 各事業
     */
    private List<NameOfEachBusiness> eachBusiness;




}
