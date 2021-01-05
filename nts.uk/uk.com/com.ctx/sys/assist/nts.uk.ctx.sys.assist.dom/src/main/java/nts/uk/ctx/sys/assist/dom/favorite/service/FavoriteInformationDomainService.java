package nts.uk.ctx.sys.assist.dom.favorite.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.sys.assist.dom.favorite.FavoriteSpecify;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.お気に入り情報を取得する
 * 
 * 
 * お気に入り情報を取得する
 */
public class FavoriteInformationDomainService {
	private FavoriteInformationDomainService() {}
	
	
	
	/**
	 * 
	 * 社員IDからすべてお気に入りと対象情報名を取得します。
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.お気に入り情報を取得する.お気に入り情報を取得する
	 * 
	 * @param require
	 * @param sid
	 */
	public static Map<FavoriteSpecify, List<String>> get(FavoriteInformationRequire require, String sid) {
		// $お気に入りList = require.お気に入りの指定を取得する(社員ID)	
		List<FavoriteSpecify> getListFavoriteBySid = require.getBySid(sid);
		return getListFavoriteBySid.stream()
				.collect(Collectors.toMap(mapper -> mapper, FavoriteSpecify::passingTargetInfoName));
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
