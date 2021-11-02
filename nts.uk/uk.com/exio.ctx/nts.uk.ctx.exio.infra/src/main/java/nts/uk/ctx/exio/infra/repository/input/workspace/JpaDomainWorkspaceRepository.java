package nts.uk.ctx.exio.infra.repository.input.workspace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspaceRepository;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItem;
import nts.uk.ctx.exio.infra.entity.input.workspace.XimctWorkspaceDomain;
import nts.uk.ctx.exio.infra.entity.input.workspace.XimctWorkspaceItem;

@Stateless
public class JpaDomainWorkspaceRepository extends JpaRepository implements DomainWorkspaceRepository {

	@Override
	public DomainWorkspace get(ImportingDomainId domainId) {
		
		String query = " select f "
					+ " from XimctWorkspaceDomain f"
					+ " where f.domainId =:domainID ";
		
		val entities = this.queryProxy().query(query, XimctWorkspaceDomain.class)
				.setParameter("domainID", domainId.value)
				.getSingle();
		
		val items = getWorkspaceItemList(domainId);
		
		return toDomain(entities.get(), items);
	}
	
	private List<WorkspaceItem> getWorkspaceItemList(ImportingDomainId domainId){
		
		String query = " select f "
					+ " from XimctWorkspaceItem f"
					+ " where f.pk.domainId =:domainID ";
		
		return this.queryProxy().query(query, XimctWorkspaceItem.class)
				.setParameter("domainID", domainId.value)
				.getList(rec -> rec.toDomain());
	}
	
	private DomainWorkspace toDomain(XimctWorkspaceDomain entity, List<WorkspaceItem> items) {
		
		List<String> primaryKeys = Arrays.asList(entity.primaryKeys.split(","));
		
		val itemsPk = new ArrayList<WorkspaceItem>();
		val itemsNotPk = new ArrayList<WorkspaceItem>();
		
		items.forEach(item -> {
			if(primaryKeys.contains(item.getName())) {
				itemsPk.add(item);
			}else {
				itemsNotPk.add(item);
			}
		});
		
		return new DomainWorkspace(ImportingDomainId.valueOf(entity.domainId), itemsPk, itemsNotPk);
	}
}
