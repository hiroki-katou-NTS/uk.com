package nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class ShiftPalletsOrgFinder {

	@Inject
	private ShiftPalletsOrgRepository shiftPalletsOrgRepository;

	public List<ShiftPalletsOrgDto> getbyWorkPlaceId(String workplaceId) {
		// 0 = work place
		List<ShiftPalletsOrg> shiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceId(0, workplaceId);
		List<ShiftPalletsOrgDto> result = shiftPalletsOrg.stream().map(c -> new ShiftPalletsOrgDto(c, workplaceId))
				.collect(Collectors.toList());
		return result;
	}
	
	// 会社別シフトパレットの一覧を取得する
	public List<ShiftPalletsOrgDto> getByListPage(String workplaceId) {
		// 0 = work place
		List<ShiftPalletsOrg> shiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceId(0, workplaceId);
		List<ShiftPalletsOrgDto> result = shiftPalletsOrg.stream().map(c -> new ShiftPalletsOrgDto(c, workplaceId))
				.collect(Collectors.toList());
		return result;
	}
}
