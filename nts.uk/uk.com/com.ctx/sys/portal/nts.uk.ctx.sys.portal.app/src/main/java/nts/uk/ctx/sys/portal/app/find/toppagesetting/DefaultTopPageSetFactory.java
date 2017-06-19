package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.app.find.layout.LayoutDto;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingDto;
import nts.uk.ctx.sys.portal.app.find.placement.PlacementDto;
import nts.uk.ctx.sys.portal.app.find.placement.PlacementPartDto;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.externalurl.ExternalUrl;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;

/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class DefaultTopPageSetFactory implements TopPageSetFactory {

	@Inject
	TopPagePartService topPagePartService;

	@Override
	public LayoutDto buildLayoutDto(Layout layout, List<Placement> placements, MyPageSettingDto myPage) {
		if (myPage.getUseMyPage().intValue() == 0) {
			return null;
		} else {
			List<PlacementDto> placementDtos = buildPlacementDto(layout, placements, myPage);
			return new LayoutDto(layout.getCompanyID(), layout.getLayoutID(), layout.getPgType().value, placementDtos);
		}
	}

	private List<PlacementDto> buildPlacementDto(Layout layout, List<Placement> placements, MyPageSettingDto myPage) {
		List<TopPagePart> activeTopPageParts = topPagePartService.getAllActiveTopPagePart(layout.getCompanyID(),layout.getPgType());

		List<PlacementDto> placementDtos = new ArrayList<PlacementDto>();
		for (Placement placement : placements) {
			if (placement.isExternalUrl()) {
				if (myPage.getExternalUrlPermission().intValue() == 1) {
					ExternalUrl externalUrl = placement.getExternalUrl().get();
					placementDtos.add(new PlacementDto(placement.getPlacementID(), placement.getLayoutID(),
							placement.getColumn().v(), placement.getRow().v(), fromExternalUrl(externalUrl)));
				}
				continue;
			}
			TopPagePart topPagePart = activeTopPageParts.stream()
					.filter(c -> c.getToppagePartID().equals(placement.getToppagePartID())).findFirst().orElse(null);
			if (topPagePart != null) {
//				if (CheckUse(topPagePart.getType().value, myPage)) {
					placementDtos.add(new PlacementDto(placement.getPlacementID(), placement.getLayoutID(),
							placement.getColumn().v(), placement.getRow().v(), fromTopPagePart(topPagePart)));
//				}
			}
		}
		return placementDtos;
	}

	private boolean CheckUse(int type, MyPageSettingDto myPage) {
		Boolean t = false;
		if (type == 0 && myPage.getUseWidget() == 1) {// Widget
			t = true;
		}
		if (type == 1 && myPage.getUseDashboard() == 1) {// DashBoard
			t = true;
		}
		if (type == 2 && myPage.getUseFlowMenu() == 1) {// FlowMenu
			t = true;
		}
		return t;
	};

	private PlacementPartDto fromTopPagePart(TopPagePart topPagePart) {
		return new PlacementPartDto(topPagePart.getWidth().v(), topPagePart.getHeight().v(),
				topPagePart.getToppagePartID(), topPagePart.getCode().v(), topPagePart.getName().v(),
				topPagePart.getType().value, null);
	}

	private PlacementPartDto fromExternalUrl(ExternalUrl externalUrl) {
		return new PlacementPartDto(externalUrl.getWidth().v(), externalUrl.getHeight().v(), null, null, null, null,
				externalUrl.getUrl().v());
	}
}
