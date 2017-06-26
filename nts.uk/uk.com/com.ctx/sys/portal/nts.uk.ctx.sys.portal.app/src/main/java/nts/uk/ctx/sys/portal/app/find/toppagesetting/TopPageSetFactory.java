package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.List;

import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingDto;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSet;
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
	LayoutForMyPageDto buildLayoutDto(Layout layout, List<Placement> placements);
	/**
	 * build layout top page
	 * @param layout
	 * @param placements
	 * @return
	 */
	LayoutForMyPageDto buildLayoutTopPage(Layout layout, List<Placement> placements);
	/**
	 * get top page for position
	 * @param jobPosition
	 * @param topPageJob
	 * @return
	 */
	LayoutForMyPageDto getTopPageForPosition(JobPositionDto jobPosition,TopPageJobSet topPageJob);
	/**
	 * get top page not position
	 * @param tpPerson
	 * @return
	 */
	LayoutForMyPageDto getTopPageNotPosition(TopPagePersonSet tpPerson);
	/**
	 * get top page by code
	 * @param companyId
	 * @param code
	 * @param system
	 * @param classification
	 * @return
	 */
	LayoutForMyPageDto getTopPageByCode(String companyId, String code, int system, int classification);
}
