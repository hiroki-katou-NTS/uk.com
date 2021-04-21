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
import nts.uk.cnv.dom.td.alteration.content.constraint.ChangeUnique;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_CHANGE_UK")
public class NemTdAltChangeUniqueKey extends NemTdAltContentBase implements Serializable {

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
		
		val domain = (ChangeUnique) ac;
		val parentPk = ChangeTableConstraintsPk.asUK(contentPk, domain.getSuffix());

		List<JpaEntity> result = new ArrayList<>();
		result.add(new NemTdAltChangeUniqueKey(parentPk, domain.isClustred(), domain.isDeleted()));
		result.addAll(NemTdAltChangePrimaryKeyColumn.toEntities(parentPk, domain.getColumnIds()));
		
		return result;
		
	}

	public AlterationContent toDomain(List<String> indexColumnIds) {
		return new ChangeUnique(this.pk.suffix, indexColumnIds, this.clustered, this.deleted);
	}

}
