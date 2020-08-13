package nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import lombok.Value;
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
	
	/**
	 * <<Query>> 職場別シフトパレットの一覧を取得する
	 */
	public List<PageDto> getShiftPaletteByWP(String workplaceId) {
		return shiftPalletsOrgRepository.findbyWorkPlaceId(0, workplaceId)
				.stream().map(i-> new PageDto(i.getPage(), i.getShiftPallet().getDisplayInfor().getShiftPalletName().v()))
				.collect(Collectors.toList());
	}
	
	/**
	 * <<Query>> 職場グループ別シフトパレットの一覧を取得する
	 */
	public List<PageDto> getShiftPaletteByWPG(String workplaceId) {
		return shiftPalletsOrgRepository.findbyWorkPlaceId(1, workplaceId)
				.stream().map(i-> new PageDto(i.getPage(), i.getShiftPallet().getDisplayInfor().getShiftPalletName().v()))
				.collect(Collectors.toList());
	}
	
	@Value
	public class PageDto {
		public int page;
		public String name;
	}
}
