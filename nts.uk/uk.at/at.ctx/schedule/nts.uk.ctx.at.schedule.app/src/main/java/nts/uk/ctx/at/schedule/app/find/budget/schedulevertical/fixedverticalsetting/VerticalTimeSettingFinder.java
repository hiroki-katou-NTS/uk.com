package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalTime;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class VerticalTimeSettingFinder {
	@Inject
	private FixedVerticalSettingRepository repository;

	/**
	 * Find Fixed Vertical by companyId
	 * 
	 * @return
	 */
	public List<VerticalTimeSettingDto> findAll(int fixedItemAtr) {
		String companyId = AppContexts.user().companyId();
		return repository.findAllVerticalTime(companyId, fixedItemAtr).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	/**
	 * Convert to Database Type Vertical Time
	 * 
	 * @param verticalTime
	 * @return
	 */
	private VerticalTimeSettingDto convertToDbType(VerticalTime verticalTime) {
		VerticalTimeSettingDto verticalSettingDto = new VerticalTimeSettingDto();
		verticalSettingDto.setCompanyId(verticalTime.getCompanyId());
		verticalSettingDto.setFixedItemAtr(verticalTime.getFixedItemAtr().value);
		verticalSettingDto.setVerticalTimeNo(verticalTime.getVerticalTimeNo());
		verticalSettingDto.setDisplayAtr(verticalTime.getDisplayAtr().value);
		verticalSettingDto.setStartClock(verticalTime.getStartClock().v());

		return verticalSettingDto;
	}
}
