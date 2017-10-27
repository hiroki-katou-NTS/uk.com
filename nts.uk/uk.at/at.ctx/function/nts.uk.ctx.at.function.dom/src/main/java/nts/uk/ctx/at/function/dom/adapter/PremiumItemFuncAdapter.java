package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

public interface PremiumItemFuncAdapter {
	
	List<PremiumItemFuncAdapterDto> getPremiumItemName(String companyID, List<Integer> displayNumbers);

}
