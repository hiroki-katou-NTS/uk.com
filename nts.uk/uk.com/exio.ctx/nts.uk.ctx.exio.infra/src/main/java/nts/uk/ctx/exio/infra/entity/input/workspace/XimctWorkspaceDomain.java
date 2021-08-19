package nts.uk.ctx.exio.infra.entity.input.workspace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;

/**
 *  受入グループのワークスペース
 */
@Entity
@Table(name = "XIMCT_WORKSPACE_GROUP")
@AllArgsConstructor
@NoArgsConstructor
public class XimctWorkspaceDomain extends JpaEntity{
	
	@Id
	@Column(name = "GROUP_ID")
	public int groupId;
	
	@Column(name = "NAME")
	public String name;
	
	@Column(name = "PRIMARY_KEYS")
	public String primaryKeys;
	
	public static final JpaEntityMapper<XimctWorkspaceDomain> MAPPER = new JpaEntityMapper<>(XimctWorkspaceDomain.class);
	
	@Override
	protected Object getKey() {
		return groupId;
	}
}
