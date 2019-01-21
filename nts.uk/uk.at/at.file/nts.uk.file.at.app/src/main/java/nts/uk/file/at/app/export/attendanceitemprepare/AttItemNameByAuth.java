package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.List;
import java.util.Map;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;

/**
 * 
 * @author hoidd 
 *
 */
public interface AttItemNameByAuth {
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	public Map<String, List<AttItemName>>  getAllByComp(String companyId);
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	public Map<String, List<AttItemName>>  getAllMonthlyByComp(String companyId);
}
