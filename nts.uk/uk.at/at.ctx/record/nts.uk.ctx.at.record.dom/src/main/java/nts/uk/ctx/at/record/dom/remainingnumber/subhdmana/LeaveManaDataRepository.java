package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface LeaveManaDataRepository {
	
	// ドメインモデル「休出管理データ」を取得
	// 社員ID=パラメータ「社員ID」
	// 代休消化区分=未消化

	List<LeaveManagementData> getBySidWithsubHDAtr(String cid, String sid, int state);
	
	List<LeaveManagementData> getByComDayOffId(String cid, String sid, String comDayOffID);
	
	List<LeaveManagementData> getBySidNotUnUsed(String cid, String sid);
	
	List<LeaveManagementData> getBySid(String cid, String sid);
	
	List<LeaveManagementData> getBySidWithsubHDAtrAndDateCondition (String cid, String sid, GeneralDate startDate, GeneralDate endDate);
	
	List<LeaveManagementData> getBySidWithHolidayDate(String cid, String sid, GeneralDate dateHoliday);
	
	void create(LeaveManagementData domain);
	
	void updateByLeaveIds(List<String> leaveIds);
	
}
