package nts.uk.ctx.office.dom.favorite.adapter;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.Imported.社員の職位を取得する
 * 																				
 * 社員の職位を取得する
 * 
 * @author nws-ducnt
 * 
 */
public interface EmployeePositionAdapter {

	
	/**
	 * [1]職位を取得する
	 * 
	 * 社員IDリスト、基準日から職位IDを取得する - Get position ID from employee ID list, base date
	 * 
	 * @param wpkIds   - 社員IDリスト
	 * @param baseDate - 基準日
	 * @return Map<社員ID、職場ID>
	 */
	
	public Map<String, EmployeeJobHistImport> getPositionBySidsAndBaseDate(List<String> sIds, GeneralDate baseDate);
}
