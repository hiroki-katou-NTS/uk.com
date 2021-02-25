package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
/**
 * 作業
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.作業
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class Task  implements DomainAggregate{
	/*** 作業枠NO */
	private final TaskFrameNo taskFrameNo;
	
	/*** コード  */
	private final TaskCode code;
	
	/*** 有効期限 */
	private DatePeriod expriationDate;
	
	/*** 表示情報 */
	private TaskDisplayInfo displayInfo;
	
	/*** 外部連携情報 */
	private ExternalCooperationInfo extCoopInfo;
	
	/*** 子作業一覧 */
	private List<TaskCode> taskSubs;
}
