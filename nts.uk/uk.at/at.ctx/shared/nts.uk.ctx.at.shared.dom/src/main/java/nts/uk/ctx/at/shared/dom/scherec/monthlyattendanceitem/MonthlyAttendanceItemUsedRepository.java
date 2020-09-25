package nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem;

import java.util.List;

public interface MonthlyAttendanceItemUsedRepository {
	
	/**
	 * 	月次の勤怠項目が利用できる帳票
	 *
	 * @param companyId 会社ID
	 * @param reportId 帳票ID（勤怠項目が利用できる帳票）
	 * @return List＜勤怠項目ID＞
	 */
	public List<Integer> getAllMonthlyItemId(String companyId, int reportId); 
}
