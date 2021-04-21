package nts.uk.cnv.infra.td.entity.alteration.constraints.index;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.query.QueryProxy;
import nts.uk.cnv.infra.td.entity.alteration.constraints.ChangeTableConstraintsColumn;
import nts.uk.cnv.infra.td.entity.alteration.constraints.ChangeTableConstraintsColumnPk;
import nts.uk.cnv.infra.td.entity.alteration.constraints.ChangeTableConstraintsPk;

@SuppressWarnings("serial")
@Table(name = "NEM_TD_ALT_CHANGE_INDEX_COLUMN")
@Entity
@NoArgsConstructor
public class NemTdAltChangeIndexColumn extends ChangeTableConstraintsColumn implements Serializable {

	public NemTdAltChangeIndexColumn(ChangeTableConstraintsColumnPk pk, int columnOrder) {
		super(pk, columnOrder);
	}

	public static List<String> getSortedColumnIds(QueryProxy queryProxy, ChangeTableConstraintsPk pk) {
		
		return ChangeTableConstraintsColumn.getSortedColumnIds(
				queryProxy,
				NemTdAltChangeIndexColumn.class,
				pk);
	}
}