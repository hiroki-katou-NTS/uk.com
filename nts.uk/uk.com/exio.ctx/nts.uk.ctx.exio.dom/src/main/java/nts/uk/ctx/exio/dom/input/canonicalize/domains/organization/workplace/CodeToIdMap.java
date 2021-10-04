package nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.val;
import nts.gul.text.IdentifierUtil;

/**
 * コードからIDへのマップ
 */
class CodeToIdMap {

	private final Map<String, String> map = new HashMap<>();
	
	public void put(String code, String id) {
		this.map.put(code, id);
	}
	
	public String getId(String code) {
		return this.map.get(code);
	}
	
	public boolean containsCode(String code) {
		return this.map.containsKey(code);
	}
}
