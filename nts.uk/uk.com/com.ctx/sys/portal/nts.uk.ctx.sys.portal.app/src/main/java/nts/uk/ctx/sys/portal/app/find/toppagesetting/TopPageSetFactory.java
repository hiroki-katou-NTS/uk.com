package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.List;

import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
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
	LayoutForMyPageDto buildLayoutDto(Layout layout, List<Placement> placements);
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
	LayoutAllDto getTopPageForPosition(JobPositionDto jobPosition,TopPageJobSet topPageJob);
	/**
	 * get top page not position
	 * @param tpPerson
	 * @return
	 */
	LayoutAllDto getTopPageNotPosition();
	/**
	 * get top page by code
	 * @param companyId
	 * @param code
	 * @param system
	 * @param classification
	 * @return
	 */
	LayoutForTopPageDto getTopPageByCode(String companyId, String code, int system, int classification, boolean check);
	/**
	 * check my page co duoc su dung khong
	 * @return
	 */
	Boolean checkMyPageSet();
	/**
	 * check top page co duoc su dung hay khong
	 * @return
	 */
	Boolean checkTopPageSet();
}
