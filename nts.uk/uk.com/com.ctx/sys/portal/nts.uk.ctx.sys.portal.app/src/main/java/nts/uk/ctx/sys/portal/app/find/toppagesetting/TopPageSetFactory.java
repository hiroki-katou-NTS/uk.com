package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.List;

import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.toppagesetting.PortalJobTitleImport;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
/**
 * 
 * @author hoatt
 *
 */
public interface TopPageSetFactory {
	/**
	 * build layout my page
	 * @param layout
	 * @param placements
	 * @return
	 */
	LayoutForMyPageDto buildLayoutMyPage(Layout layout, List<Placement> placements);
	/**
	 * build layout top page
	 * @param layout
	 * @param placements
	 * @return
	 */
	LayoutForTopPageDto buildLayoutTopPage(Layout layout, List<Placement> placements);
	/**
	 * get top page for position
	 * @param jobPosition
	 * @param topPageJob
	 * @return
	 */
	LayoutAllDto getTopPageForPosition(String fromScreen, PortalJobTitleImport jobPosition,TopPageJobSet topPageJob);
	/**
	 * get top page not position
	 * @param tpPerson
	 * @return
	 */
	LayoutAllDto getTopPageNotPosition(String fromScreen);
	/**
	 * check my page co duoc su dung khong
	 * @return
	 */
	boolean checkMyPageSet();
	/**
	 * check top page co duoc su dung hay khong
	 * @return
	 */
	boolean checkTopPageSet();
	/**
	 * find lay out my page
	 * @return
	 */
	LayoutForMyPageDto findLayoutMyPage();
}
