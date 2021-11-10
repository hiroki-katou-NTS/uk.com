package nts.uk.ctx.exio.app.input.develop.workspace.oruta.detail;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;

@Value
public class OrutaTable {

	String lastAlterId;
	String id;
	String name;
	String jpName;
	List<OrutaTableColumn> columns;
	OrutaTableConstraints constraints;
	
	public List<String> toCsvXimctWorkspaceItem(ImportingDomainId domainId) {
		
		val itemNoMap = domainId.createCanonicalization().getItemNoMap();
		
		return columns.stream()
				.filter(c -> isWorkspaceItem(c.getName()))
				.map(c -> {
					return c.toCsvXimctWorkspaceItem(
						domainId,
						itemNoMap.getItemNo(c.getName()));
				})
				.collect(toList());
	}
	
	private static boolean isWorkspaceItem(String name) {
		return Arrays.asList("CONTRACT_CD", "CID").contains(name) == false;
	}
}
