package nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;
import nts.uk.shr.com.context.AppContexts;

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
	public List<PageDto> getByListPage(String workplaceId) {
		String cid = AppContexts.user().companyId();
		List<ShiftPalletsOrg> shiftPalletsOrg = shiftPalletsOrgRepository.findByCID(cid);
		List<PageDto> result = shiftPalletsOrg.stream().map(c -> new PageDto(c)).collect(Collectors.toList());
		return result;
	}

	@Getter
	public class PageDto {
		private int page;
		private String name;

		public PageDto(ShiftPalletsOrg dom) {
			this.page = dom.getPage();
			this.name = dom.getShiftPallet().getDisplayInfor().getShiftPalletName().v();
		}
	}
}
