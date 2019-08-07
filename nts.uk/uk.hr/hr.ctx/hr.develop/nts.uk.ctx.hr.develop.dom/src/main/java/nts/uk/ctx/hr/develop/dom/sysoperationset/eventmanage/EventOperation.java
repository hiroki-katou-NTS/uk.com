package nts.uk.ctx.hr.develop.dom.sysoperationset.eventmanage;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.hr.develop.dom.hrdevelopmentevent.AvailableEvent;
import nts.uk.ctx.hr.develop.dom.hrdevelopmentevent.EventId;
/**
 * イベント管理
 * @author yennth
 *
 */
@AllArgsConstructor
@Getter
public class EventOperation extends AggregateRoot{
	// イベントID
	private EventId eventId;
	// イベントを使用する
	private AvailableEvent useEvent;
	// 会社ID
	private String companyId;
	// 会社コード
	private BigInteger ccd;
	
	/**
	 * create domain from java type
	 * @param eventId
	 * @param useEvent
	 * @param companyId
	 * @return
	 */
	public static EventOperation createFromJavaType(int eventId, int useEvent, String companyId, BigInteger ccd) {
		return new EventOperation(EnumAdaptor.valueOf(eventId, EventId.class), 
				EnumAdaptor.valueOf(useEvent, AvailableEvent.class),
				companyId, ccd);
	}
}
