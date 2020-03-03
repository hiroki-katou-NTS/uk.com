package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * VO : 時間外の申告
 * @author tutk
 *
 */
@Value
public class OvertimeDeclaration implements DomainObject {
	/**
	 * 時間外時間
	 */
	private final AttendanceTime overTime;
	
	/**
	 * 時間外深夜時間
	 */
	private final AttendanceTime overLateNightTime;

	public OvertimeDeclaration(AttendanceTime overTime, AttendanceTime overLateNightTime) {
		super();
		this.overTime = overTime;
		this.overLateNightTime = overLateNightTime;
	}
	
	
}
