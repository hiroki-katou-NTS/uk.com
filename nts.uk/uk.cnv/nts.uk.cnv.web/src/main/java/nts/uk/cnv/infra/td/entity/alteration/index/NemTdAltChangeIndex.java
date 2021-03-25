package nts.uk.cnv.infra.td.entity.alteration.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_CHANGE_INDEX")
public class NemTdAltChangeIndex extends NemTdAltContentBase implements Serializable {

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
		val domain = (ChangeIndex) ac;
		val parent = new NemTdAltChangeIndex(
				NemTdAltChangeTableConstraintsPk.asIndex(contentPk, domain.getSuffix()),
				domain.isClustred()
			);
		result.add(parent);
		result.addAll(
				domain.getColumnIds().stream()
					.map(colId -> new NemTdAltChangePrimaryKeyColumn(
							new NemTdAltChangeTableConstraintsColumnPk(
								parent.pk.alterationId,
								parent.pk.seqNo,
								parent.pk.suffix,
								colId
							)
						))
					.collect(Collectors.toList())
			);
		return result;
	}

	public AlterationContent toDomain(List<String> indexColumnIds) {
		return new ChangeIndex(this.pk.suffix, indexColumnIds, this.clustered);
	}

}
