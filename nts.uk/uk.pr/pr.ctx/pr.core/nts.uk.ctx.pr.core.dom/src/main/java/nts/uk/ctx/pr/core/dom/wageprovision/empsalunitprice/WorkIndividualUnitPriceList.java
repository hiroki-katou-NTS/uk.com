package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WorkIndividualUnitPriceList {

    String employeeId;

    List<PricePerItem> pricePerItemList;
}
