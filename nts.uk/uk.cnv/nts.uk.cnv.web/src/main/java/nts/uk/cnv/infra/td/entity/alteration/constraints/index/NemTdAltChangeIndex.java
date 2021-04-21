package nts.uk.cnv.infra.td.entity.alteration.constraints.index;

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
import nts.uk.cnv.dom.td.alteration.content.constraint.ChangeIndex;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;
import nts.uk.cnv.infra.td.entity.alteration.constraints.ChangeTableConstraintsColumn;
import nts.uk.cnv.infra.td.entity.alteration.constraints.ChangeTableConstraintsPk;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_CHANGE_INDEX")
public class NemTdAltChangeIndex extends NemTdAltContentBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ChangeTableConstraintsPk pk;

	@Column(name = "IS_CLUSTERED")
	public boolean clustered;

	@Column(name = "IS_DELETED")
	public boolean deleted;


	@Override
	protected Object getKey() {
		return pk;
	}

	public static List<JpaEntity> toEntity(NemTdAltContentPk contentPk, AlterationContent ac) {
		
		val domain = (ChangeIndex) ac;
		val parentPk = ChangeTableConstraintsPk.asIndex(contentPk, domain.getSuffix());

		List<JpaEntity> result = new ArrayList<>();
		result.add(new NemTdAltChangeIndex(parentPk, domain.isClustred(), domain.isDeleted()));
		result.addAll(ChangeTableConstraintsColumn.toEntities(
				parentPk,
				domain.getColumnIds(),
				NemTdAltChangeIndexColumn::new));
		
		return result;
	}

	public AlterationContent toDomain(List<String> indexColumnIds) {
		return new ChangeIndex(this.pk.suffix, indexColumnIds, this.clustered, this.deleted);
	}

}
