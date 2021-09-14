package nts.uk.ctx.exio.app.input.develop.workspace.oruta.detail;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;

@Value
public class OrutaTable {

	String lastAlterId;
	String id;
	String name;
	String jpName;
	List<OrutaTableColumn> columns;
	OrutaTableConstraints constraints;
	
	public List<String> toCsvXimctWorkspaceItem(ImportingDomainId domainId, List<ImportableItem> items) {
		
		return columns.stream()
				.map(c -> {
					val itemOpt = items.stream().filter(i -> i.getItemName().equals(c.getName())).findFirst();
					return c.toCsvXimctWorkspaceItem(
						domainId,
						itemOpt);
				})
				.collect(toList());
	}
}
