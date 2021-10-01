package nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.val;
import nts.gul.text.IdentifierUtil;

/**
 * 職場コードからIDへのマップ
 */
class WorkplaceIdMap {

	private final Map<String, String> codeToId = new HashMap<>();
	
	public static WorkplaceIdMap create(List<String> codes) {
		val map = new WorkplaceIdMap();
		codes.forEach(c -> map.put(c, IdentifierUtil.randomUniqueId()));
		return map;
	}
	
	public void put(String code, String id) {
		this.codeToId.put(code, id);
	}
	
	public String getId(String code) {
		return this.codeToId.get(code);
	}
}
