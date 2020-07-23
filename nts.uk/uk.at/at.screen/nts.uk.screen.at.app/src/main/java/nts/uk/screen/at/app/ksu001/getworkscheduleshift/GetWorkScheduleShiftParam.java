/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;

/**
 * @author laitv
 *
 */
public class GetWorkScheduleShiftParam {
	
	List<ShiftMaster> listShiftMaster; // 新たに取得する必要のないシフト一覧：List<シフトマスタ>
	List<String> listSid;  // 社員リスト：List<社員ID> 
	GeneralDate startDate; // 期間：期間
	GeneralDate endDate;   // 期間：期間
} 
