package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingDto;
import nts.uk.ctx.sys.portal.app.find.mypage.setting.MyPageSettingFinder;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageDto;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageFinder;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSelfSetRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class DisplayMyPageFinder {
	@Inject
	private TopPageSelfSetRepository toppageRepository;

	@Inject
	private PlacementRepository placementRepository;
	@Inject
	private TopPageSetFactory topPageSet;
	@Inject
	private MyPageSettingFinder myPageSetFinder;
	@Inject
	private TopPageFinder toppageFinder;

	//companyId
	String companyId = AppContexts.user().companyId();
	/**
	 * Find Layout by ID
	 * 
	 * @param layoutID
	 * @return Optional Layout
	 */
	public LayoutForMyPageDto findLayout(String layoutID) {
		Optional<Layout> layout = toppageRepository.find(layoutID,2);
		if (layout.isPresent()) {
			List<Placement> placements = placementRepository.findByLayout(layoutID);
			MyPageSettingDto myPageSetting = myPageSetFinder.findByCompanyId(companyId);
			LayoutForMyPageDto layoutNew = topPageSet.buildLayoutDto(layout.get(), placements,myPageSetting);
			return layoutNew;
		}
		return null;
	}
	public LayoutForMyPageDto findLayoutTopPage(String topPageCode){
		TopPageDto toppageNew = toppageFinder.findByCode(companyId, topPageCode, "0");
		return null;
	}
}
