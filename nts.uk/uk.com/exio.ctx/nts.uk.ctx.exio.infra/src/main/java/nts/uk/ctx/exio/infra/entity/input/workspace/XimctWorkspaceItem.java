package nts.uk.ctx.exio.infra.entity.input.workspace;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItem;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItemType;

@Entity
@Table(name = "XIMCT_WORKSPACE_ITEM")
@AllArgsConstructor
@NoArgsConstructor
public class XimctWorkspaceItem extends JpaEntity {
	
	@EmbeddedId
	private XimctWorkspaceItemPK pk;
	
	/* 項目名 */
	@Column(name = "NAME")
	public String name;
	
	/* 種別 */
	@Column(name = "TYPE")
	public int type;
	
	public static final JpaEntityMapper<XimctWorkspaceItem> MAPPER = new JpaEntityMapper<>(XimctWorkspaceItem.class);
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static XimctWorkspaceItem toEntity(WorkspaceItem domain) {
		return new XimctWorkspaceItem(
				new XimctWorkspaceItemPK(
					domain.getGroupId().value,
					domain.getItemNo()),
				domain.getName(),
				domain.getType().value);
	}
	
	public WorkspaceItem toDomain() {
		return new WorkspaceItem(
				ImportingGroupId.valueOf(pk.groupId),
				pk.itemNo,
				name,
				WorkspaceItemType.valueOf(type));
	}
}
