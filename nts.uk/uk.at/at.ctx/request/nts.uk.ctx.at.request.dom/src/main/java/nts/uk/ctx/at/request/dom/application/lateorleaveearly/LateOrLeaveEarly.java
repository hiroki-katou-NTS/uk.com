package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.util.Time;

/**
 * 
 * @author hieult
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class LateOrLeaveEarly extends AggregateRoot {
	/** 会社ID */
	private String companyID;
	/** 申請ID */
	private String appID;
	/** 実績取消区分 */
	private Change actualCancelAtr;
	/** 早退1 */
	private Select early1;
	/** 早退時刻1 */
	private TimeDay earlyTime1;
	/** 遅刻1 */
	private Select late1;
	/** 遅刻時刻1 */
	private TimeDay lateTime1;
	/** 早退2 */
	private Select early2;
	/** 早退時刻2 */
	private TimeDay earlyTime2;
	/** 遅刻2 */
	private Select late2;
	/** 遅刻時刻2 */
	private TimeDay lateTime2;

	public static LateOrLeaveEarly createFromJavaType(String companyID, String appID, int actualCancelAtr,
		int early1, String earlyTime1, int late1, String lateTime1 , int early2, String earlyTime2, int late2, 
		String lateTime2) {
			return new LateOrLeaveEarly (companyID, appID,
										 EnumAdaptor.valueOf(actualCancelAtr, Change.class),
										 EnumAdaptor.valueOf(early1, Select.class),
										 new TimeDay(Time.parse(earlyTime1)),
										 EnumAdaptor.valueOf(late1, Select.class),
										 new TimeDay(Time.parse(lateTime1)),
										 EnumAdaptor.valueOf(early2, Select.class),
										 new TimeDay(Time.parse(earlyTime2)),
										 EnumAdaptor.valueOf(late2, Select.class),
										 new TimeDay(Time.parse(lateTime2))
										 );
					}
}
