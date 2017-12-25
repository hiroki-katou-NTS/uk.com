package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

public interface SpecificDateAdapter {

	List<SpecificDateImport> getSpecificDate(String companyId, List<Integer> specificDateItemNos);
	
}
