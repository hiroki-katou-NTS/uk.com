package nts.uk.ctx.at.shared.dom.calculation.holiday.flex;

import java.util.List;
import java.util.Optional;

public interface FlexSetRepository {

	List<FlexSet> findByCompanyId(String companyId);

	void add(FlexSet flexSet);

	void update(FlexSet flexSet);

	Optional<FlexSet> findByCId(String companyId);

}
