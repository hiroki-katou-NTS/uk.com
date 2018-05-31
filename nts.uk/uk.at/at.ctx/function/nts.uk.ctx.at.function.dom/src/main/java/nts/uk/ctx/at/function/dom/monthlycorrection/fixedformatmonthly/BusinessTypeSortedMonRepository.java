package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.Optional;

public interface BusinessTypeSortedMonRepository {

	Optional<BusinessTypeSortedMon> getOrderReferWorkType(String companyID);
	
	void addBusinessTypeSortedMon(BusinessTypeSortedMon businessTypeSortedMon);
	
	void updateBusinessTypeSortedMon(BusinessTypeSortedMon businessTypeSortedMon);
	
	void deleteBusinessTypeSortedMon(String companyID);
	
}
