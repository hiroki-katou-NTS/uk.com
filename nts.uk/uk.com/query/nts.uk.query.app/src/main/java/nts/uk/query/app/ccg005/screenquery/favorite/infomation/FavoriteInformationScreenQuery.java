package nts.uk.query.app.ccg005.screenquery.favorite.infomation;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecifyRepository;
import nts.uk.ctx.office.dom.favorite.service.DefaultFavoriteInfoRequireImpl;
import nts.uk.ctx.office.dom.favorite.service.FavoriteInformationDomainService;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.D：お気に入り.メニュー別OCD.お気に入り情報を取得.お気に入り情報を取得
 */
public class FavoriteInformationScreenQuery {

	@Inject
	private FavoriteSpecifyRepository repo;

	public Map<FavoriteSpecify, List<String>> getFavoriteInformation() {
		DefaultFavoriteInfoRequireImpl rq = new DefaultFavoriteInfoRequireImpl(repo);
		String sid = AppContexts.user().employeeId();
		return FavoriteInformationDomainService.get(rq, sid);
	}
}
