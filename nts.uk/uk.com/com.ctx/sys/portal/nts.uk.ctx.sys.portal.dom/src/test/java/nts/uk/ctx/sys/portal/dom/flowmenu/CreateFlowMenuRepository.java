package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

@Stateless
public interface CreateFlowMenuRepository {
	
	void insert(CreateFlowMenu domain);
	
	void update(CreateFlowMenu domain);
	
	void delete(CreateFlowMenu domain);
	
	Optional<CreateFlowMenu> findByPk(String cid, String flowMenuCode);
	
	List<CreateFlowMenu> findByCid(String cid);
}
