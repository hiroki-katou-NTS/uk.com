package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalCnt;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class VerticalCntSettingFinder {
	@Inject
	private FixedVerticalSettingRepository repository;

	public List<VerticalCntSettingDto> findAll(int fixedItemAtr) {
		String companyId = AppContexts.user().companyId();
		return repository.findAllCnt(companyId, fixedItemAtr).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	/**
	 * Convert to Database Type Vertical Time
	 * 
	 * @param verticalTime
	 * @return
	 */
	private VerticalCntSettingDto convertToDbType(VerticalCnt verticalCnt) {
		VerticalCntSettingDto verticalCntSettingDto = new VerticalCntSettingDto();
		verticalCntSettingDto.setCompanyId(verticalCnt.getCompanyId());
		verticalCntSettingDto.setFixedItemAtr(verticalCnt.getFixedItemAtr());
		verticalCntSettingDto.setVerticalCountNo(verticalCnt.getVerticalCountNo());

		return verticalCntSettingDto;
	}

}
