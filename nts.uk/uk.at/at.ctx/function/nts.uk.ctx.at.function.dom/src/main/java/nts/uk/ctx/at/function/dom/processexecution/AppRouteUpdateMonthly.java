package nts.uk.ctx.at.function.dom.processexecution;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Approval route update monthly.<br>
 * Domain 承認ルート更新（日次）
 *
 * @author nws-minhnb
 */
@Getter
public class AppRouteUpdateMonthly {
	
	/**
	 * The Approval route update attribute.<br>
	 * 使用区分
	 */
	private NotUseAtr appRouteUpdateAtr;

	/**
	 * Instantiates a new App route update monthly.
	 *
	 * @param appRouteUpdateAtr the approval route update attribute
	 */
	public AppRouteUpdateMonthly(int appRouteUpdateAtr) {
		this.appRouteUpdateAtr = EnumAdaptor.valueOf(appRouteUpdateAtr, NotUseAtr.class);
	}
}
