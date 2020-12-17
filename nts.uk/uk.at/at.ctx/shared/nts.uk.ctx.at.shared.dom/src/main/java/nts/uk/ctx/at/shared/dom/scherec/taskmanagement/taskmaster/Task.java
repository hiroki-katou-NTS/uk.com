package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
/**
 * 作業
 * @author lan_lt
 *
 */
@AllArgsConstructor
public class Task  implements DomainAggregate{
	/*** 作業枠NO */
	@Getter
	private final TaskFrameNo taskFrameNo;
	
	/*** コード  */
	@Getter
	private final TaskCode code;
	
	/*** 有効期限 */
	@Getter
	private DatePeriod expriationDate;
	
	/*** 表示情報 */
	@Getter
	private TaskDisplayInfo displayInfo;
	
	/*** 外部連携情報 */
	@Getter
	private ExternalCooperationInfo extCoopInfo;
	
	/*** 子作業一覧 */
	@Getter
	private List<TaskCode> taskSubs;
}
