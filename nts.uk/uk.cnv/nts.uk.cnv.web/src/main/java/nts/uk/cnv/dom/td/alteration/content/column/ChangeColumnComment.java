package nts.uk.cnv.dom.td.alteration.content.column;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@EqualsAndHashCode(callSuper= false)
public class ChangeColumnComment extends AlterationContent {
	private final String columnId;
	private final String comment;

	public ChangeColumnComment(String columnId, String comment) {
		super(AlterationType.COLUMN_COMMENT_CHANGE);
		this.columnId = columnId;
		this.comment = comment;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		List<AlterationContent> result = new ArrayList<>();
		for(int i=0; i<altered.get().getColumns().size(); i++) {
			ColumnDesign alterdCol = altered.get().getColumns().get(i);
			ColumnDesign baseCol = base.get().getColumns().stream()
					.filter(col -> col.getId().equals(alterdCol.getId()))
					.findFirst()
					.get();
			if(!baseCol.getComment().equals(alterdCol.getComment())) {
				result.add(new ChangeColumnComment(baseCol.getId(), alterdCol.getComment()));
			}
		}
		return result;
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		if(!base.isPresent() || !altered.isPresent()) {
			return false;
		}
		for(int i=0; i<altered.get().getColumns().size(); i++) {
			ColumnDesign alterdCol = altered.get().getColumns().get(i);
			Optional<ColumnDesign> baseCol = base.get().getColumns().stream()
					.filter(col -> col.getId().equals(alterdCol.getId()))
					.findFirst();
			if(baseCol.isPresent() && !baseCol.get().getComment().equals(alterdCol.getComment())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TableProspectBuilder apply(String alterationId, TableProspectBuilder builder) {
		return builder.columnComment(
				alterationId,
				this.columnId,
				this.comment);
	}

	@Override
	public String createAlterDdl(TableDesign tableDesign, TableDefineType defineType) {
		return "";
	}
}
