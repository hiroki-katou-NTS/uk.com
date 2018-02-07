package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;

public interface PersAuthorityRepository {

	List<PersAuthority> findByCompanyId(String companyId, String roleId);

	void add(PersAuthority author);

	void update(PersAuthority author);

	Optional<PersAuthority> findByCId(String companyId, String roleId, int getFunctionNoPers);

}
