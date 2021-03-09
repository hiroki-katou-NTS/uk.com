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
public class ChangeColumnComment extends AlterationContent {
	private final String columnName;
	private final String comment;

	public ChangeColumnComment(String columnName, String comment) {
		super(AlterationType.COLUMN_COMMENT_CHANGE);
		this.columnName = columnName;
		this.comment = comment;
	}

	public static List<AlterationContent> create(Optional<TableDesign> base, Optional<TableDesign> altered) {
		List<AlterationContent> result = new ArrayList<>();
		for(int i=0; i<altered.get().getColumns().size(); i++) {
			ColumnDesign alterdCol = altered.get().getColumns().get(i);
			ColumnDesign baseCol = base.get().getColumns().stream()
					.filter(col -> col.getName().equals(alterdCol.getName()))
					.findFirst()
					.get();
			if(!baseCol.getComment().equals(alterdCol.getComment())) {
				result.add(new ChangeColumnComment(baseCol.getName(), alterdCol.getComment()));
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
					.filter(col -> col.getName().equals(alterdCol.getName()))
					.findFirst();
			if(baseCol.isPresent() && !baseCol.get().getComment().equals(alterdCol.getComment())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TableDesignBuilder apply(TableDesignBuilder builder) {
		return builder.columnComment(
				this.columnName,
				this.comment);
	}
}
