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
	private final AttendanceTime outsideTime;
	
	/**
	 * 時間外深夜時間
	 */
	private final AttendanceTime outsideLateNightTime;

	public OvertimeDeclaration(AttendanceTime outsideTime, AttendanceTime outsideLateNightTime) {
		super();
		this.outsideTime = outsideTime;
		this.outsideLateNightTime = outsideLateNightTime;
	}
	
	
}
