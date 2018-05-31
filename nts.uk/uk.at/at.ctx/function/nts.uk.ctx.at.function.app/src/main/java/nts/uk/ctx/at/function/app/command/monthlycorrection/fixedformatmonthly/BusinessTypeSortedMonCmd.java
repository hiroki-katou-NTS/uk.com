package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMon;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTypeSortedMonCmd {
	/**会社ID*/
	private String companyID;
	/**並び順*/
	List<OrderReferWorkTypeCmd> listOrderReferWorkType;

	public static BusinessTypeSortedMon fromCommand(BusinessTypeSortedMonCmd command) {
		return new BusinessTypeSortedMon(
				command.getCompanyID(),
				command.getListOrderReferWorkType().stream().map(c->OrderReferWorkTypeCmd.fromCommand(c)).collect(Collectors.toList())
				);
	}


}
