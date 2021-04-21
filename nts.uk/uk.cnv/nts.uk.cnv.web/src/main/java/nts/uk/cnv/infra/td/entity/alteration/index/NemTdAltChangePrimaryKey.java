package nts.uk.cnv.infra.td.entity.alteration.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.constraint.ChangePK;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_CHANGE_PK")
public class NemTdAltChangePrimaryKey extends NemTdAltContentBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltChangeTableConstraintsPk pk;

	@Column(name = "IS_CLUSTERED")
	public boolean clustered;

	@Override
	protected Object getKey() {
		return pk;
	}

	public static List<JpaEntity> toEntity(NemTdAltContentPk contentPk, AlterationContent ac) {
		List<JpaEntity> result = new ArrayList<>();
		val domain = (ChangePK) ac;
		
		val parent = new NemTdAltChangePrimaryKey(
				NemTdAltChangeTableConstraintsPk.asPK(contentPk),
				domain.isClustred());
		result.add(parent);
		
		for (int i = 0; i < domain.getColumnIds().size(); i++) {
			
			String columnId = domain.getColumnIds().get(i);
			int columnOrder = i + 1;
			
			val column = new NemTdAltChangePrimaryKeyColumn(
					NemTdAltChangeTableConstraintsColumnPk.create(parent, columnId),
					columnOrder);
			
			result.add(column);
		}
		
		return result;
	}

	public AlterationContent toDomain(List<String> columnIds) {
		return new ChangePK(columnIds, this.clustered);
	}

}
