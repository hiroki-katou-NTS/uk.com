package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.List;

import nts.uk.ctx.sys.portal.app.find.layout.LayoutDto;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingDto;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;

public interface TopPageSetFactory {
	LayoutDto buildLayoutDto(Layout layout, List<Placement> placements, MyPageSettingDto myPage);
}
