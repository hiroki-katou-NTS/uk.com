package nts.uk.ctx.exio.infra.entity.input.workspace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.workspace.WorkspaceItem;
import nts.uk.ctx.exio.dom.input.workspace.WorkspaceItemType;

@Entity
@Table(name = "XIMCT_WORKSPACE_ITEM")
@AllArgsConstructor
@NoArgsConstructor
public class XimctWorkspaceItem extends JpaEntity {
	
	public static final JpaEntityMapper<XimctWorkspaceItem> MAPPER = new JpaEntityMapper<>(XimctWorkspaceItem.class);
	
	@Id
	@Column(name = "GROUP_ID")
	public int groupId;

	@Column(name = "ITEM_NO")
	public int itemNo;

	@Column(name = "NAME")
	public String name;

	@Column(name = "TYPE")
	public int type;
	
	@Override
	protected Object getKey() {
		return groupId;
	}

	public static XimctWorkspaceItem toEntity(WorkspaceItem domain) {
		
		return new XimctWorkspaceItem(
				domain.getGroupId().value,
				domain.getItemNo(),
				domain.getName(),
				domain.getType().value);
	}
	
	public WorkspaceItem toDomain() {
		
		return new WorkspaceItem(
				ImportingGroupId.valueOf(groupId),
				itemNo,
				name,
				WorkspaceItemType.valueOf(type));
	}
}
