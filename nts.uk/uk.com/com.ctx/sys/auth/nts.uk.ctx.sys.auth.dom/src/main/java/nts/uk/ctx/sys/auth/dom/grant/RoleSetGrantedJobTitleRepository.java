package nts.uk.ctx.sys.auth.dom.grant;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */
public interface RoleSetGrantedJobTitleRepository {

	public List<RoleSetGrantedJobTitle> getAllByCompanyId(String companyId);
	
}
