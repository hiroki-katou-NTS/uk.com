package nts.uk.ctx.at.function.dom.attendancetype;

import java.util.List;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface AttendanceTypeRepository {
	/**
	 * get attendance Id by screen use atr
	 * 画面で利用できる勤怠項目一覧
	 * @param companyID
	 * @param screenUseAtr
	 * @return
	 */
	public List<AttendanceType> getItemByScreenUseAtr(String companyID, int screenUseAtr);
	
	/**
	 * 
	 * @param companyId
	 * @param screenUseAtr
	 * @param attendanceItemType
	 * @return
	 */
	public List<AttendanceType> getItemByAtrandType(String companyId, int screenUseAtr, int attendanceItemType);
	
}
