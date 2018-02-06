package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;

public interface PerWorkplaceRepository {

	Optional<PerWorkplace> findByCId(String companyId, String roleId, int functionNoWorkplace);

	void update(PerWorkplace author);

	void add(PerWorkplace author);

	List<PerWorkplace> findByCompanyId(String companyId, String roleId);

}
