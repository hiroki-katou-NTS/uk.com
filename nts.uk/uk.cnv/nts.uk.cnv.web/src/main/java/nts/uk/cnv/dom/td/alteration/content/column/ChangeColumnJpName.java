package nts.uk.cnv.dom.td.alteration.content.column;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@EqualsAndHashCode(callSuper= false)
public class ChangeColumnJpName extends AlterationContent {
	
	@Getter
	private final String columnId;
	
	@Getter
	private final String jpName;

	public ChangeColumnJpName(String columnId, String jpName) {
		super(AlterationType.COLUMN_JPNAME_CHANGE);
		this.columnId = columnId;
		this.jpName = jpName;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		List<AlterationContent> result = new ArrayList<>();
		for(int i=0; i<altered.get().getColumns().size(); i++) {
			ColumnDesign alterdCol = altered.get().getColumns().get(i);
			ColumnDesign baseCol = base.get().getColumns().stream()
					.filter(col -> col.getId().equals(alterdCol.getId()))
					.findFirst()
					.get();
			if(!baseCol.getJpName().equals(alterdCol.getJpName())) {
				result.add(new ChangeColumnJpName(baseCol.getId(), alterdCol.getJpName()));
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
			if(baseCol.isPresent() && !baseCol.get().getJpName().equals(alterdCol.getJpName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TableProspectBuilder apply(String alterationId, TableProspectBuilder builder) {
		return builder.columnJpName(
				alterationId,
				this.columnId,
				this.jpName);
	}

	@Override
	public String createAlterDdl(Require require, TableDesign tableDesign, TableDefineType defineType) {
		return defineType.columnCommentDdl(
				tableDesign.getName().v(),
				require.getColumnName(columnId),
				this.jpName);
	}
}
