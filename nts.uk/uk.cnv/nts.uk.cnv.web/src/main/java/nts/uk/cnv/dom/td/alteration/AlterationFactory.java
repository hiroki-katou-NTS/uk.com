package nts.uk.cnv.dom.td.alteration;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
/**
 * おるたファクトリ
 */
@Stateless
public class AlterationFactory {

	public Optional<Alteration> create(
			String featureId, String tableId, AlterationMetaData meta,
			Optional<? extends TableDesign> base, Optional<TableDesign> altered) {

		if(base.equals(altered)) {
			return Optional.empty();
		}

		Alteration result = Alteration.createEmpty(featureId, tableId, meta);
		Arrays.stream(AlterationType.values())
			.filter(type -> type.applicable(base, altered))
			.forEach(type -> {
				result.getContents().addAll(type.createContent(base, altered));
			});

		return Optional.of(result);
	}
}
