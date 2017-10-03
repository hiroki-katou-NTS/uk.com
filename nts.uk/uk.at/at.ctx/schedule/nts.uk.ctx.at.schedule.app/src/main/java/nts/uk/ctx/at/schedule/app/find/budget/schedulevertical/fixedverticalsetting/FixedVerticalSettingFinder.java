package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVertical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class FixedVerticalSettingFinder {
	@Inject
	private FixedVerticalSettingRepository repository;
	
	/**
	 * Find Fixed Vertical by companyId
	 * @return
	 */
	public List<FixedVerticalSettingDto> findByCid() {
		String companyId = AppContexts.user().companyId();
		return repository.findAll(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	private FixedVerticalSettingDto convertToDbType(FixedVertical fixedVertical) {
		FixedVerticalSettingDto verticalSettingDto = new FixedVerticalSettingDto();
		verticalSettingDto.setCompanyId(fixedVertical.getCompanyId());
		verticalSettingDto.setFixedVerticalNo(fixedVertical.getFixedVerticalNo());
		verticalSettingDto.setUseAtr(fixedVertical.getUseAtr().value);
		verticalSettingDto.setFixedItemAtr(fixedVertical.getFixedItemAtr().value);
		verticalSettingDto.setVerticalDetailedSettings(fixedVertical.getVerticalDetailedSettings().value);

		return verticalSettingDto;
	}
}
