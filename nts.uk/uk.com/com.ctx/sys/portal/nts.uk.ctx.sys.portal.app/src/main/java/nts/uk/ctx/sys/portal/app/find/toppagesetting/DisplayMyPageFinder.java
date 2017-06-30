package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.app.find.toppage.TopPageDto;
import nts.uk.ctx.sys.portal.app.find.toppage.TopPageFinder;
import nts.uk.ctx.sys.portal.dom.layout.Layout;
import nts.uk.ctx.sys.portal.dom.placement.Placement;
import nts.uk.ctx.sys.portal.dom.placement.PlacementRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSet;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSetRepository;
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
	private TopPageFinder toppageFinder;
	@Inject
	private TopPageJobSetRepository topPageJobSet;
	@Inject
	private TopPageSelfSettingFinder topPageSelfSet;
	/**
	 * find layout (top page)
	 * @param topPageCode
	 * @return
	 */
	public LayoutAllDto findLayoutTopPage(String topPageCode){
		//companyId
		String companyId = AppContexts.user().companyId();
		if(topPageCode != null && topPageCode != ""){//co top page code
			//check my page co duoc su dung hay khong
			Boolean checkMyPage = topPageSet.checkMyPageSet();
			//check top page co duoc setting khong
			Boolean checkTopPage = topPageSet.checkTopPageSet();
			TopPageDto topPage = toppageFinder.findByCode(companyId, topPageCode, "0");
			if(topPage==null){//khong co du lieu
				return new LayoutAllDto(null,null,true,checkMyPage,checkTopPage);
			}
			Optional<Layout> layout = toppageRepository.find(topPage.getLayoutId(),0);
			if (layout.isPresent()) {//co du lieu
				List<Placement> placements = placementRepository.findByLayout(topPage.getLayoutId());
				LayoutForTopPageDto layoutTopPage = topPageSet.buildLayoutTopPage(layout.get(), placements);
				LayoutForMyPageDto layoutMyPage = null;
				return new LayoutAllDto(layoutMyPage,layoutTopPage,true,checkMyPage,checkTopPage);
			}
			return new LayoutAllDto(null,null,true,checkMyPage,checkTopPage);
		}
		//khong co top page code
		//lay chuc vu
		JobPositionDto jobPosition = topPageSelfSet.getJobPosition(AppContexts.user().employeeId());
		List<String> lst = new ArrayList<>();
		LayoutAllDto layoutTopPage = null;
		if(jobPosition != null){//co chuc vu
			lst.add(jobPosition.getJobId());
			//lay top page job title set
			TopPageJobSet tpJobSet = topPageJobSet.findByListJobId(companyId,lst).get(0);
			if(tpJobSet != null){//co chuc vu va co job setting
				layoutTopPage = topPageSet.getTopPageForPosition(jobPosition,tpJobSet);
			}else{
				layoutTopPage = topPageSet.getTopPageNotPosition();
			}
		}else{//khong co chuc vu
			layoutTopPage = topPageSet.getTopPageNotPosition();
		}
		return layoutTopPage;
	}
	
}
