package nts.uk.cnv.dom.td.alteration.content;

import java.util.List;

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

	public abstract TableProspectBuilder apply(String alterationId, TableProspectBuilder builder);

	public abstract String createAlterDdl(Require require, TableDesign tableDesign, TableDefineType defineType);

	public interface Require{
		List<String> getColumnNames(List<String> columnIds);
		String getColumnName(String columnId);
	}
}
