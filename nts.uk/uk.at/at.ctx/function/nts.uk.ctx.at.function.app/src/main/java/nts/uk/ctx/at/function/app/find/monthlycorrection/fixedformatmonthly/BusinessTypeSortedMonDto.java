package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMon;

@Getter
@Setter
@NoArgsConstructor
public class BusinessTypeSortedMonDto {
	/**会社ID*/
	private String companyID;
	/**並び順*/
	List<OrderReferWorkTypeDto> listOrderReferWorkType;
	public BusinessTypeSortedMonDto(String companyID, List<OrderReferWorkTypeDto> listOrderReferWorkType) {
		super();
		this.companyID = companyID;
		this.listOrderReferWorkType = listOrderReferWorkType;
	}
	
	public static BusinessTypeSortedMonDto fromDomain(BusinessTypeSortedMon domain) {
		return new BusinessTypeSortedMonDto(
				domain.getCompanyID(),
				domain.getListOrderReferWorkType().stream().map(c->OrderReferWorkTypeDto.fromDomain(c)).collect(Collectors.toList())
				);
	}
}
