package nts.uk.ctx.office.dom.favorite.adapter;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.Imported.職場の所属社員を取得する
 * 
 * 職場の所属社員を取得する
 * 
 * @author nws-ducnt
 * 
 */
public interface EmployeeBelongWorkplaceAdapter {

	/**
	 * [1]職場の所属社員を取得する
	 * 
	 * 
	 * @param wkps - 社員IDリスト
	 * @param baseDate - 基準日
	 * @return List<社員ID>
	 */
	public List<String> getEmployeeByWplAndBaseDate(List<String> wkps, GeneralDate baseDate);
}
