package nts.uk.screen.com.app.find.ccg005.favorite.information;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.office.dom.favorite.FavoriteSpecifyRepository;
import nts.uk.ctx.office.dom.favorite.service.DefaultFavoriteInfoRequireImpl;
import nts.uk.ctx.office.dom.favorite.service.FavoriteInformationDomainService;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.D：お気に入り.メニュー別OCD.お気に入り情報を取得.お気に入り情報を取得
 */
@Stateless
public class FavoriteInformationScreenQuery {

	@Inject
	private FavoriteSpecifyRepository repo;
	
	@Inject
	private FavoriteInformationDomainService ds;

	public Map<FavoriteSpecifyDto, List<String>> getFavoriteInformation() {
		DefaultFavoriteInfoRequireImpl rq = new DefaultFavoriteInfoRequireImpl(repo);
		String sid = AppContexts.user().employeeId();
		Map<FavoriteSpecifyDto, List<String>> returnMap = new HashMap<>();
		ds.get(rq, sid).forEach((key, value) -> {
			FavoriteSpecifyDto dto = FavoriteSpecifyDto.builder().build();
			key.setMemento(dto);
			returnMap.put(dto, value);
		});
		return returnMap;
	}
}
