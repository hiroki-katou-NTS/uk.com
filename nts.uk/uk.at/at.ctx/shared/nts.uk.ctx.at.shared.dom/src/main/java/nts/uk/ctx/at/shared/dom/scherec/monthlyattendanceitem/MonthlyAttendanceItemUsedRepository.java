package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem;

import java.util.List;

public interface MonthlyAttendanceItemUsedRepository {
	
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.勤怠項目が利用できる帳票.Repository.帳票用の月次勤怠項目の取得Repository
	 *
	 * @param companyId 会社ID
	 * @param reportId 帳票ID（勤怠項目が利用できる帳票）
	 * @return List＜勤怠項目ID＞
	 */
	public List<Integer> getAllMonthlyItemId(String companyId, int reportId); 
}
