package nts.uk.ctx.office.dom.favorite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify.Require;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforAdapter;
import nts.uk.ctx.office.dom.favorite.RequireImpl;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.お気に入り情報を取得する
 * 
 * 
 * お気に入り情報を取得する
 */
@Stateless
public class FavoriteInformationDomainService {
	
	@Inject
	private WorkplaceInforAdapter adapter;
	
	/**
	 * 
	 * 社員IDからすべてお気に入りと対象情報名を取得します。
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.お気に入り情報を取得する.お気に入り情報を取得する
	 * 
	 * @param require
	 * @param sid
	 */
	public Map<FavoriteSpecify, List<String>> get(FavoriteInformationRequire favoriteInforRequire, String sid) {
		// $お気に入りList = require.お気に入りの指定を取得する(社員ID)	
		List<FavoriteSpecify> listFavorite = favoriteInforRequire.getBySid(sid);
		Map<FavoriteSpecify, List<String>> result = new HashMap<FavoriteSpecify, List<String>>();
		Require require = new RequireImpl(adapter);
		listFavorite.forEach(x -> {
			result.put(x, x.passingTargetInfoName(require));
		});
		return result;
	}
	
	public static interface FavoriteInformationRequire {
		
		/**
		 * [R-1] 取得する
		 * 
		 * @param sid
		 * @return List<FavoriteSpecify> List<お気に入りの指定>
		 */
		 List<FavoriteSpecify> getBySid(String sid);
	}
}
