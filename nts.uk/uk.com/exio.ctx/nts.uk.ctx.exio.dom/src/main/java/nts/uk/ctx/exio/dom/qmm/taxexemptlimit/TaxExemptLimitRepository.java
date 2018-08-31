package nts.uk.ctx.exio.dom.qmm.taxexemptlimit;

import java.util.List;
import java.util.Optional;

/**
 * 非課税限度額の登録
 */
public interface TaxExemptLimitRepository {

	List<TaxExemptLimit> getAllTaxExemptLimit();

	Optional<TaxExemptLimit> getTaxExemptLimitById(String cid);

	void add(TaxExemptLimit domain);

	void update(TaxExemptLimit domain);

	void remove(String cid);

}
