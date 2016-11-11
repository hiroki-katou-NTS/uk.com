package nts.uk.ctx.pr.proto.dom.paymentdata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.primitive.PersonId;

public class HolidayPaid extends AggregateRoot {
	/**
	 * Remain day.
	 */
	@Getter
	private int remainDays;
	
	/**
	 * Remain time.
	 */
	@Getter
	private int remainTime;
	
	/**
	 * Personal Id
	 */
	@Getter
	private PersonId personId;
	
	public HolidayPaid(int remainDays, int remainTime, PersonId personId) {
		this.remainDays = remainDays;
		this.remainTime = remainTime;
		this.personId = personId;
	}
	
	/**
	 * Create instance using Java type parameters.
	 * 
	 * @param remainDays remain Days
	 * @param remainTime remain Time
	 * @param personId personId
	 * @return HolidayPaid
	 */
	public static HolidayPaid createFromJavaType(int remainDays, int remainTime, String personId) {
		return new HolidayPaid(remainDays, remainTime, new PersonId(personId));
	}
}
