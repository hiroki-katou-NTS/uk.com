package nts.uk.cnv.dom.td.service;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
/**
 * おるたファクトリ
 */
@Stateless
public class AlterationFactory {

	public Alteration create(
			String tableId, AlterationMetaData meta,
			Optional<TableDesign> base, Optional<TableDesign> altered) {

		Alteration result = Alteration.createEmpty(tableId, meta);
		if(!base.isPresent() && !altered.isPresent()) {
			return result;
		}

		if(base.isPresent() && altered.isPresent() && base.get().equals(altered.get())) {
			return result;
		}

		Arrays.stream(AlterationType.values())
			.forEach(type -> {
				if(type.applicable(base, altered)) {
					result.getContents().addAll(type.createContent(base, altered));
				}
			});

		return result;
	}
}
