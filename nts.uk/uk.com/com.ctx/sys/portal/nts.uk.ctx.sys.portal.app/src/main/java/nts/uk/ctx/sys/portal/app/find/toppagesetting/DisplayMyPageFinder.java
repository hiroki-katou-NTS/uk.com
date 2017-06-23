package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.ArrayList;
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
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSetRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetRepository;
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
	@Inject
	private TopPageJobSetRepository topPageJobSet;
	@Inject
	private TopPageSelfSettingFinder topPageSelfSet;
	@Inject
	private TopPagePersonSetRepository topPagePerson;
	//companyId
	String companyId = AppContexts.user().companyId();
	//employeeId
	String employeeId = AppContexts.user().employeeId();
	/**
	 * Find Layout by ID (my page)
	 * 
	 * @param layoutID
	 * @return Optional Layout
	 */
	public LayoutForMyPageDto findLayout(String layoutID) {
		Optional<Layout> layout = toppageRepository.find(layoutID,2);
		if (layout.isPresent()) {
			List<Placement> placements = placementRepository.findByLayout(layoutID);
			MyPageSettingDto myPageSetting = myPageSetFinder.findByCompanyId(companyId);
			LayoutForMyPageDto layoutNew = topPageSet.buildLayoutDto(layout.get(), placements);
			return layoutNew;
		}
		return null;
	}
	/**
	 * find layout (top page)
	 * @param topPageCode
	 * @return
	 */
	public LayoutForMyPageDto findLayoutTopPage(String topPageCode){
		if(topPageCode != null){//co top page code
			TopPageDto topPage = toppageFinder.findByCode(companyId, topPageCode, "0");
			Optional<Layout> layout = toppageRepository.find(topPage.getLayoutId(),0);
			if (layout.isPresent()) {
				List<Placement> placements = placementRepository.findByLayout(topPage.getLayoutId());
				LayoutForMyPageDto layoutNew = topPageSet.buildLayoutTopPage(layout.get(), placements);
				return layoutNew;
			}
			return null;
		}
		//khong co top page code
		JobPositionDto jobPosition = topPageSelfSet.getJobPosition(AppContexts.user().employeeId());
		List<String> lst = new ArrayList<>();
		lst.add(jobPosition.getJobId());
		LayoutForMyPageDto layoutTopPage = null;
		TopPageJobSet tpJobSet = topPageJobSet.findByListJobId(companyId,lst).get(0);
		if(jobPosition != null && tpJobSet != null){
				layoutTopPage = topPageSet.getTopPageForPosition(jobPosition,tpJobSet);
		}
		if(jobPosition == null || tpJobSet == null ){
			//lay du lieu bang person set
			TopPagePersonSet tpPerson = topPagePerson.findBySid(companyId, employeeId).get();
			layoutTopPage = topPageSet.getTopPageNotPosition(tpPerson);
		}
		return layoutTopPage;
//		LayoutForMyPageDto layout2 = topPageSet.getTopPage(jobPosition);
//		TopPageDto toppageNew = toppageFinder.findByCode(companyId, topPageCode, "0");
//		if(toppageNew !=null){
//			Optional<Layout> layout = toppageRepository.find(toppageNew.getLayoutId(),0);
//			if (layout.isPresent()) {
//				List<Placement> placements = placementRepository.findByLayout(toppageNew.getLayoutId());
//				LayoutForMyPageDto layoutNew = topPageSet.buildLayoutTopPage(layout.get(), placements);
//				return layoutNew;
//			}
//		}
//		return null;
	}
}
