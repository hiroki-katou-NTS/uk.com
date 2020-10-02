package nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrgRepository;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class ShiftPalletsOrgFinder {

	@Inject
	private ShiftPaletteOrgRepository shiftPalletsOrgRepository;
	
	public List<ShiftPalletsOrgDto> getbyWorkPlaceId(String workplaceId) {
		// 0 = work place
		List<ShiftPaletteOrg> shiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceId(0, workplaceId);
		List<ShiftPalletsOrgDto> result = shiftPalletsOrg.stream().map(c -> new ShiftPalletsOrgDto(c, workplaceId))
				.collect(Collectors.toList());
		return result;
	}
	
	public List<ShiftPalletsOrgDto> getbyWorkPlaceGrId(String workplaceGrId) {
		// 1 = work place group
		List<ShiftPaletteOrg> shiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceId(1, workplaceGrId);
		List<ShiftPalletsOrgDto> result = shiftPalletsOrg.stream().map(c -> new ShiftPalletsOrgDto(c, workplaceGrId))
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
