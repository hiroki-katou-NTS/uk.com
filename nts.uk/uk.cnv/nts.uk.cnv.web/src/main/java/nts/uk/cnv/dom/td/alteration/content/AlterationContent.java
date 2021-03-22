package nts.uk.cnv.dom.td.alteration.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@Getter
@AllArgsConstructor
public abstract class AlterationContent {
	AlterationType type;

	public abstract void apply(String alterationId, TableProspectBuilder builder);

	public abstract String createAlterDdl(TableDesign tableDesign, TableDefineType defineType);

}
