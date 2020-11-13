package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Monthly aggregate.<br>
 * Domain 月別実績の集計
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MonthlyAggregate extends DomainObject {

	/**
	 * The Monthly aggregate classification.<br>
	 * 使用区分
	 */
	private NotUseAtr monthlyAggCls;

	/**
	 * Instantiates a new <code>MonthlyAggregate</code>.
	 *
	 * @param monthlyAggCls the monthly aggregate classification classification
	 */
	public MonthlyAggregate(int monthlyAggCls) {
		this.monthlyAggCls = EnumAdaptor.valueOf(monthlyAggCls, NotUseAtr.class);
	}

}
