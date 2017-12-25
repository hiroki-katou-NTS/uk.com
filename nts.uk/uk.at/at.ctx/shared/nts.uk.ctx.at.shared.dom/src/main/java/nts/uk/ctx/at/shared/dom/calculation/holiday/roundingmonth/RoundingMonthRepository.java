package nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth;

import java.util.List;
import java.util.Optional;

public interface RoundingMonthRepository {

	List<RoundingMonth> findByCompanyId(String companyId, String itemTimeId);

	void add(RoundingMonth month);

	void update(RoundingMonth month);

	Optional<RoundingMonth> findByCId(String companyId, String timeItemId);

}
