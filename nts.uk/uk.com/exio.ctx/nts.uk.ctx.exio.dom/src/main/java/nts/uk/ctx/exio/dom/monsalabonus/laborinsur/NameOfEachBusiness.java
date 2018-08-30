package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;

@AllArgsConstructor
@Value
public class NameOfEachBusiness {
    /**
     * 労災保険事業No
     */
    private int occAccInsurBusNo;

    /**
     * 利用する
     */
    private int toUse;

    /**
     * 名称
     */
    private Optional<OccAccInsurBusinessName> name;
}
