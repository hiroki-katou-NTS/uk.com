package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.List;

/**
 * 労災保険事業
 */
@AllArgsConstructor
@Getter
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
