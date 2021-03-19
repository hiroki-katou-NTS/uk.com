package nts.uk.cnv.dom.td.alteration.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@EqualsAndHashCode(callSuper= false)
public class ChangeTableJpName extends AlterationContent {
	private final String jpName;

	public ChangeTableJpName(String jpName) {
		super(AlterationType.TABLE_JPNAME_CHANGE);
		this.jpName = jpName;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return Arrays.asList( new ChangeTableJpName(altered.get().getJpName()));
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return (base.isPresent() && altered.isPresent() && !base.get().getJpName().equals(altered.get().getJpName()));
	}

	@Override
	public TableProspectBuilder apply(String alterationId, TableProspectBuilder builder) {
		return builder.jpName(alterationId, this.jpName);
	}

	@Override
	public String createAlterDdl(TableDesign tableDesign, TableDefineType defineType) {
		return defineType.tableCommentDdl(tableDesign.getName().v(), this.jpName);
	}
}
