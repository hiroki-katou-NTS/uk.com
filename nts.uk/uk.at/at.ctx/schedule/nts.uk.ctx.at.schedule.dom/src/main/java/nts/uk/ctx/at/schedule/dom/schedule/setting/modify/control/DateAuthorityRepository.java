package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;

public interface DateAuthorityRepository {

	List<DateAuthority> findByCompanyId(String companyId, String roleId);

	void add(DateAuthority author);

	void update(DateAuthority author);

	Optional<DateAuthority> findByCId(String companyId, String roleId, int functionNoDate);

}
