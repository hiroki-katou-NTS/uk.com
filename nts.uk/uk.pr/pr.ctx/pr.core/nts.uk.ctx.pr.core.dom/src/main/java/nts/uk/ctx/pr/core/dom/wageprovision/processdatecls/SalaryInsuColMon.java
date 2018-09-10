package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 給与社会保険徴収月
 */
@AllArgsConstructor
@Getter
public class SalaryInsuColMon extends AggregateRoot {



	/**
	 * 徴収月
	 */
	private SocialInsuColleMonth monthCollected;
	
	public SalaryInsuColMon( int monthCollected) {

		this.monthCollected = EnumAdaptor.valueOf(monthCollected, SocialInsuColleMonth.class);
	}

}
