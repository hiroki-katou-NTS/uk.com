package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
/**
 * 
 * 夜勤上限時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療勤務形態.夜勤上限時間
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class NightShiftUpperLimitTime implements DomainAggregate{
	
	/** 契約コード　**/
	private ContractCode contractCode;
	
	/** 夜勤専従者　**/
	private AttendanceTimeMonth fullTimeWorker;
	
	/** 夜勤専従者以外**/
	private AttendanceTimeMonth otherFullTimeWorker;
	
}
