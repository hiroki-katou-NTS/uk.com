package nts.uk.ctx.exio.infra.repository.input.workspace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspaceRepository;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItem;
import nts.uk.ctx.exio.infra.entity.input.workspace.XimctWorkspaceGroup;
import nts.uk.ctx.exio.infra.entity.input.workspace.XimctWorkspaceItem;

@Stateless
public class JpaGroupWorkspaceRepository extends JpaRepository implements GroupWorkspaceRepository {

	@Override
	public GroupWorkspace get(ImportingGroupId groupId) {
		
		String query = " select f "
					+ " from XimctWorkspaceGroup f"
					+ " where f.groupId =:groupID ";
		
		val entities = this.queryProxy().query(query, XimctWorkspaceGroup.class)
				.setParameter("groupID", groupId.value)
				.getSingle();
		
		val items = getWorkspaceItemList(groupId);
		
		return toDomain(entities.get(), items);
	}
	
	private List<WorkspaceItem> getWorkspaceItemList(ImportingGroupId groupId){
		
		String query = " select f "
					+ " from XimctWorkspaceItem f"
					+ " where f.pk.groupId =:groupID ";
		
		return this.queryProxy().query(query, XimctWorkspaceItem.class)
				.setParameter("groupID", groupId.value)
				.getList(rec -> rec.toDomain());
	}
	
	private GroupWorkspace toDomain(XimctWorkspaceGroup entity, List<WorkspaceItem> items) {
		
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
		
		return new GroupWorkspace(ImportingGroupId.valueOf(entity.groupId), itemsPk, itemsNotPk);
	}
}
