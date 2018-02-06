package nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control;

import java.util.List;
import java.util.Optional;

public interface WorktypeDisRepository {

	void add(WorktypeDis worktypeDis);

	void update(WorktypeDis worktypeDis);

	List<WorktypeDis> findByCompanyId(String companyId);

	Optional<WorktypeDis> findByCId(String companyId);

}
