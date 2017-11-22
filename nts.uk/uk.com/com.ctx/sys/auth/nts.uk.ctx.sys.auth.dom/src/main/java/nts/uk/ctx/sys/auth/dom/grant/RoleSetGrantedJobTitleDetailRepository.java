package nts.uk.ctx.sys.auth.dom.grant;

import java.util.List;

public interface RoleSetGrantedJobTitleDetailRepository {

	public List<RoleSetGrantedJobTitleDetail> getAllByCompany(String companyId);
}
