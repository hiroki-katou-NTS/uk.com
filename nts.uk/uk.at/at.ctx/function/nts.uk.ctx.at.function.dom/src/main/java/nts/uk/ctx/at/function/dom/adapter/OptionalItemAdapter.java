package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

public interface OptionalItemAdapter {
	
	List<OptionalItemImport> findOptionalItem(String companyId, List<Integer> optionalItemNos);
	
}
