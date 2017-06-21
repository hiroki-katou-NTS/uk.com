package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.List;

import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingDto;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
/**
 * 
 * @author hoatt
 *
 */
public interface TopPageSetFactory {
	/**
	 * build layout
	 * @param layout
	 * @param placements
	 * @param myPage
	 * @return
	 */
	LayoutForMyPageDto buildLayoutDto(Layout layout, List<Placement> placements, MyPageSettingDto myPage);
}
