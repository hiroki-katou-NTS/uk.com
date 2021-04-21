package nts.uk.cnv.infra.td.entity.alteration.index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_CHANGE_PK_COLUMN")
public class NemTdAltChangePrimaryKeyColumn extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltChangeTableConstraintsColumnPk pk;
	
	@Column(name = "COLUMN_ORDER")
	public int columnOrder;

	public static NemTdAltChangePrimaryKeyColumn toEntity(NemTdAltChangeTableConstraintsPk parent, String columnId, int columnOrder) {
		return new NemTdAltChangePrimaryKeyColumn(
				new NemTdAltChangeTableConstraintsColumnPk(
					parent.alterationId,
					parent.seqNo,
					parent.suffix,
					columnId
				),
				columnOrder
			);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
