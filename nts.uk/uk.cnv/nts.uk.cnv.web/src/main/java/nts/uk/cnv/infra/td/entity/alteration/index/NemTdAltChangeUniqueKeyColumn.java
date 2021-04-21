package nts.uk.cnv.infra.td.entity.alteration.index;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.query.QueryProxy;

@SuppressWarnings("serial")
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_CHANGE_UK_COLUMN")
public class NemTdAltChangeUniqueKeyColumn extends ChangeTableConstraintsColumn implements Serializable {
	
	public NemTdAltChangeUniqueKeyColumn(ChangeTableConstraintsColumnPk pk, int columnOrder) {
		super(pk, columnOrder);
	}

	public static List<String> getSortedColumnIds(QueryProxy queryProxy, ChangeTableConstraintsPk pk) {
		
		return ChangeTableConstraintsColumn.getSortedColumnIds(
				queryProxy,
				NemTdAltChangeUniqueKeyColumn.class,
				pk);
	}
}