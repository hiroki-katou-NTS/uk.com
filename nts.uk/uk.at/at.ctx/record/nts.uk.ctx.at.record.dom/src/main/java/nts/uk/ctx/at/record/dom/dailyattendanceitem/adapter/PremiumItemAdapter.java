package nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter;

import java.util.List;

public interface PremiumItemAdapter {
	List<PremiumItemDto> getPremiumItemName(String companyID, List<Integer> displayNumbers);
}
