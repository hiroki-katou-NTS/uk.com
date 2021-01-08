package nts.uk.ctx.office.dom.favorite.adapter;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.Imported.社員の職場IDを取得する
 * 
 * 社員の職場IDを取得する
 * 
 * @author nws-ducnt
 * 
 */
public interface EmployeeWorkplaceIdAdapter {

	/**
	 * [1]職場IDを取得する
	 * 
	 * 社員IDリストから職場IDを取得する - Get a workplace ID from the employee ID list
	 * 
	 * @param sIds   - 社員IDリスト
	 * @param baseDate - 基準日
	 * @return Map<社員ID、職場ID>
	 */
	public Map<String, String> getWorkplaceId(List<String> sIds, GeneralDate baseDate);
}
