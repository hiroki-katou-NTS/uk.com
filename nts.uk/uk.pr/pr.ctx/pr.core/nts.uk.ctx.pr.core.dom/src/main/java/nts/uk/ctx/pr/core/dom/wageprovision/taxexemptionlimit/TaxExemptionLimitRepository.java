package nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author thanh.tq 非課税限度額の登録
 *
 */
public interface TaxExemptionLimitRepository {

	List<TaxExemptionLimit> getAllTaxExemptLimit();

	List<TaxExemptionLimit> getTaxExemptLimitByCompanyId(String cid);

	Optional<TaxExemptionLimit> getTaxExemptLimitById(String cid, String taxFreeAmountCode);

	void add(TaxExemptionLimit domain);

	void update(TaxExemptionLimit domain);

	void remove(String cid, String taxFreeamountCode);

}
