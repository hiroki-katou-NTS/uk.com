package nts.uk.ctx.at.record.dom.jobmanagement.manhourinput;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.工数入力.時間帯別勤怠の削除一覧
 * 
 * @author tutt
 *
 */
@Getter
@AllArgsConstructor
public class DeleteAttendancesByTimezone extends AggregateRoot {
	
	// 社員ID
	private final String sId;
	
	// 年月日
	private final GeneralDate ymd;
	
	// 一覧: List<時間帯別勤怠の削除>
	private final List<AttendanceByTimezoneDeletion> attendanceDeletionLst;
}
