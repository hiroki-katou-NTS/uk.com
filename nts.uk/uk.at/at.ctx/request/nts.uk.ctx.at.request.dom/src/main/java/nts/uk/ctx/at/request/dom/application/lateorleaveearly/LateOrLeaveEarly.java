package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import javax.persistence.EnumType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author hieult
 *
 */
@Value
@EqualsAndHashCode(callSuper= false)
public class LateOrLeaveEarly extends AggregateRoot {

	private String companyID;

	private String appID;

	private String actualCancelAtr;

	private Early early;

	private Early early2;

	private int earlyTime;

	private int earlyTime2;

	private Late late;

	private Late late2;

	private int lateTime;

	private int lateTime2;

		public static LateOrLeaveEarly createFromJavaType(String companyID, String appID, String actualCancelAtr,
				int early, int early2, int earlyTime, int earlyTime2, Late late, Late late2, int lateTime,
				int lateTime2) {
		
					return new LateOrLeaveEarly (companyID, appID, actualCancelAtr,
												 EnumAdaptor.valueOf(early, Early.class),
												 EnumAdaptor.valueOf(early2, Early.class),
												 earlyTime, earlyTime2,
												 late, late2, lateTime, lateTime2);
							}
}
