/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getschedulesbyshift;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;

/**
 * @author laitv
 *
 */
@Value
public class SchedulesbyShiftParam {

	public List<ShiftMasterMapWithWorkStyle> listShiftMasterNotNeedGetNew; // ・新たに取得する必要のないシフト一覧：List<シフトマスタ>
	public List<String> listSid;             // ・社員IDリスト：List<社員ID>
	public GeneralDate startDate;            // ・期間
	public GeneralDate endDate;    	         // ・期間
	public boolean getActualData;            // ・実績も取得するか：boolean : có lấy data thực tế không
}
