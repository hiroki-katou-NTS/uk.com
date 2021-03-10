package nts.uk.cnv.dom.td.alteration.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class RemoveColumn extends AlterationContent {
	private final String columnId;

	public RemoveColumn(String columnId) {
		super(AlterationType.COLUMN_DELETE);
		this.columnId = columnId;
	}

	public static List<AlterationContent> create(Optional<TableDesign> base, Optional<TableDesign> altered) {
		List<AlterationContent> retult = new ArrayList<>();
		for(int i=0; i<base.get().getColumns().size(); i++) {
			ColumnDesign baseCol = base.get().getColumns().get(i);
			Optional<ColumnDesign> alteredCol = altered.get().getColumns().stream()
					.filter(col -> col.getId().equals(baseCol.getId()))
					.findFirst();
			if(!alteredCol.isPresent()) {
				retult.add(new RemoveColumn(baseCol.getId()));
			}
		}
		return retult;
	}

	public static boolean applicable(Optional<TableDesign> base, Optional<TableDesign> altered) {
		if(!base.isPresent() || !altered.isPresent()) {
			return false;
		}

		for(int i=0; i<base.get().getColumns().size(); i++) {
			ColumnDesign baseCol = base.get().getColumns().get(i);
			Optional<ColumnDesign> alteredCol = altered.get().getColumns().stream()
					.filter(col -> col.getId().equals(baseCol.getId()))
					.findFirst();
			if(!alteredCol.isPresent()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TableDesignBuilder apply(TableDesignBuilder builder) {
		return builder.removeColumn(
				this.columnId);
	}
}
