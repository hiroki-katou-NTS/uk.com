package nts.uk.ctx.office.dom.favorite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforImport;

public class FavoriteSpecifyTestHelper {
	
	/**
	 * 
	 * @param 対象選択 targetSelection
	 * @param 職場ID workplaceId
	 * @return お気に入りの指定 FavoriteSpecify 
	 */
	public static FavoriteSpecify mockFavoriteSpecify(int targetSelection,  List<String> workplaceId) {
		FavoriteSpecifyDto mockDto = new FavoriteSpecifyDto(
				"favoriteName", 
				"creatorId", 
				null, 
				targetSelection,
				workplaceId,
				0);
		return FavoriteSpecify.createFromMemento(mockDto);
	}
	
	/**
	 * Mock data for [R-1] 職場表示名を取得す
	 */
	public static Map<String, WorkplaceInforImport> mockRequireGetWrkspDispName() {
		WorkplaceInforImport info = new WorkplaceInforImport(
				"workplaceId",
				"hierarchyCode",
				"workplaceCode",
				"workplaceName",
				"workplaceDisplayName",
				"workplaceGenericName",
				"workplaceExternalCode"
				);
		Map<String, WorkplaceInforImport> resultMap = new HashMap<>();
		resultMap.put("key",info);
		return resultMap;
	}
}
