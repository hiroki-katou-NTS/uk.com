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
public class AddColumn extends AlterationContent {
	private final String columnId;
	private final ColumnDesign column;

	public AddColumn(String columnId, ColumnDesign column) {
		super(AlterationType.COLUMN_ADD);
		this.columnId = columnId;
		this.column = column;
	}

	public static List<AlterationContent> create(Optional<TableDesign> base, Optional<TableDesign> altered) {
		List<AlterationContent> result = new ArrayList<>();
		for(int i=0; i<altered.get().getColumns().size(); i++) {
			ColumnDesign alterdCol = altered.get().getColumns().get(i);
			Optional<ColumnDesign> baseCol = base.get().getColumns().stream()
					.filter(col -> col.getId().equals(alterdCol.getId()))
					.findFirst();
			if(!baseCol.isPresent()) {
				result.add(new AddColumn(alterdCol.getId(), alterdCol));
			}
		}
		return result;
	}

	public static boolean applicable(Optional<TableDesign> base, Optional<TableDesign> altered) {
		if(!base.isPresent() || !altered.isPresent()) {
			return false;
		}

		for(int i=0; i<altered.get().getColumns().size(); i++) {
			ColumnDesign alterdCol = altered.get().getColumns().get(i);
			Optional<ColumnDesign> baseCol = base.get().getColumns().stream()
					.filter(col -> col.getId().equals(alterdCol.getId()))
					.findFirst();
			if(!baseCol.isPresent()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TableDesignBuilder apply(TableDesignBuilder builder) {
		return builder.addColumn(
				this.columnId,
				this.column);
	}
}
