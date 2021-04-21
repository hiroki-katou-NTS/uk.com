package nts.uk.cnv.infra.td.entity.alteration.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.query.QueryProxy;

@SuppressWarnings("serial")
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_CHANGE_PK_COLUMN")
public class NemTdAltChangePrimaryKeyColumn extends ChangeTableConstraintsColumn implements Serializable {
	
	public NemTdAltChangePrimaryKeyColumn(ChangeTableConstraintsColumnPk pk, int columnOrder) {
		super(pk, columnOrder);
	}

	public static List<NemTdAltChangePrimaryKeyColumn> toEntities(
			ChangeTableConstraintsPk parentPk,
			List<String> sortedColumnIds) {
		
		List<NemTdAltChangePrimaryKeyColumn> entities = new ArrayList<>();
		
		for (int i = 0; i < sortedColumnIds.size(); i++) {
			
			String columnId = sortedColumnIds.get(i);
			int columnOrder = i + 1;
			
			val column = new NemTdAltChangePrimaryKeyColumn(
					ChangeTableConstraintsColumnPk.create(parentPk, columnId),
					columnOrder);
			
			entities.add(column);
		}
		
		return entities;
	}

	public static List<String> getSortedColumnIds(QueryProxy queryProxy, ChangeTableConstraintsPk pk) {
		return ChangeTableConstraintsColumn.getSortedColumnIds(
				queryProxy,
				NemTdAltChangePrimaryKeyColumn.class,
				pk);
	}
}
