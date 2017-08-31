package nts.uk.ctx.at.record.ac.premiumItemFinder;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.PremiumItemAdapter;
import nts.uk.ctx.at.record.dom.dailyattendanceitem.adapter.PremiumItemDto;
import nts.uk.ctx.at.schedule.pub.budget.premium.PremiumItemPub;

@Stateless
public class PremiumItemFinder implements PremiumItemAdapter {
	
	@Inject
	private PremiumItemPub premiumItemPub;

	@Override
	public List<PremiumItemDto> getPremiumItemName(String companyID, List<Integer> displayNumbers) {
		return this.premiumItemPub.findByCompanyIDAndDisplayNumber(companyID, displayNumbers).stream().map(f -> {
			return new PremiumItemDto(f.getDisplayNumber(), f.getName());
		}).collect(Collectors.toList());
	}

}
