package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;

public interface ScheModifyDeadlineRepository {

	void add(SchemodifyDeadline deadline);

	List<SchemodifyDeadline> findByCompanyId(String companyId, String roleId);

	void update(SchemodifyDeadline deadline);

	Optional<SchemodifyDeadline> findByCId(String companyId, String roleId);

}
