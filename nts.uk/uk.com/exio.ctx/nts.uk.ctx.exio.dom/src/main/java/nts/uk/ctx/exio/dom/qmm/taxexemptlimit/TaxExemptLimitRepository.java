package nts.uk.ctx.exio.dom.qmm.taxexemptlimit;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author thanh.tq 非課税限度額の登録
 *
 */
public interface TaxExemptLimitRepository {

	List<TaxExemptLimit> getAllTaxExemptLimit();
	
	List<TaxExemptLimit> getTaxExemptLimitByCompanyId(String cid);

	Optional<TaxExemptLimit> getTaxExemptLimitById(String cid, String taxFreeamountCode );

	void add(TaxExemptLimit domain);

	void update(TaxExemptLimit domain);

	void remove(String cid, String taxFreeamountCode);

}
