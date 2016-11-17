package nts.uk.ctx.pr.screen.app.query.paymentdata.repository;

import java.util.List;
import java.util.Optional;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.DetailItemDto;

public interface PaymentDataQueryRepository {
	
	/*
	 * Get all detail item
	 */
	List<DetailItemDto> findAll(String companyCode, String personId, int payBonusAtr, int processingYM);
	
	/*
	 * Get item list of each category
	 */
	List<DetailItemDto> findItemByCategory(String companyCode, String personId, int payBonusAtr, int processingYm, int categoryAtr);
	
	/*
	 * Get item list of deduction category
	 */
	List<DetailItemDto> findDeductionItem(String companyCode, String personId, int payBonusAtr, int processingYm, int categoryAtr, int deductionAtr);
	
	/*
	 * Get each item
	 */
	Optional<DetailItemDto> findItem(String companyCode, String personId, int payBonusAtr, int processingYm,int categoryAtr, int itemCode);
}
