package nts.uk.ctx.exio.infra.entity.input.workspace;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataTypeConfiguration;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItem;

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
	
	/* データ型 */
	@Column(name = "DATA_TYPE")
	public int dataType;
	
	@Column(name = "LENGTH")
	public int length;
	
	@Column(name = "SCALE")
	public int scale;
	
	public static final JpaEntityMapper<XimctWorkspaceItem> MAPPER = new JpaEntityMapper<>(XimctWorkspaceItem.class);
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static XimctWorkspaceItem toEntity(WorkspaceItem domain) {
		return new XimctWorkspaceItem(
				new XimctWorkspaceItemPK(
					domain.getDomainId().value,
					domain.getItemNo()),
				domain.getName(),
				domain.getDataTypeConfig().getType().value,
				domain.getDataTypeConfig().getLength(),
				domain.getDataTypeConfig().getScale());
	}
	
	public WorkspaceItem toDomain() {
		return new WorkspaceItem(
				ImportingDomainId.valueOf(pk.domainId),
				pk.itemNo,
				name,
				new DataTypeConfiguration(DataType.valueOf(dataType), length, scale));
	}
}
