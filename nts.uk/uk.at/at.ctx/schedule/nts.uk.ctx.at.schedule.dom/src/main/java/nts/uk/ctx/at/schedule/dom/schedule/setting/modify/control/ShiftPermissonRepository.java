package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;

public interface ShiftPermissonRepository {

	Optional<ShiftPermisson> findByCId(String companyId, String roleId, int functionNoShift);

	void update(ShiftPermisson author);

	void add(ShiftPermisson author);

	List<ShiftPermisson> findByCompanyId(String companyId, String roleId);

}
