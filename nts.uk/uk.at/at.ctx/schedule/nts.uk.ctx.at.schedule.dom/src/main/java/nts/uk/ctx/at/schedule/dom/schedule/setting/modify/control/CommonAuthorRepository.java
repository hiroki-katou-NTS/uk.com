package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import java.util.List;
import java.util.Optional;

public interface CommonAuthorRepository {

	List<CommonAuthor> findByCompanyId(String companyId, String roleId);

	Optional<CommonAuthor> findByCId(String companyId, String roleId, int functionNoCommon);

	void update(CommonAuthor author);

	void add(CommonAuthor author);

}
