package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository;

import java.math.BigDecimal;
import java.util.List;

public interface DailyAttendanceItemUsedRepository {
	
	/**
	 * 日次の勤怠項目が利用できる帳票
	 *
	 * @param companyId 会社ID
	 * @param reportId 帳票ID（勤怠項目が利用できる帳票）
	 * @return List＜勤怠項目ID＞
	 */
	public List<Integer> getAllDailyItemId(String companyId, BigDecimal reportId); 
}
