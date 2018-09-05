package nts.uk.ctx.sys.assist.dom.salary;

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
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 徴収月
	 */
	private SocialInsuColleMonth monthCollected;
	
	public SalaryInsuColMon(int processCateNo, String cid, int monthCollected) {
		this.processCateNo = processCateNo;
		this.cid = cid;
		this.monthCollected = EnumAdaptor.valueOf(monthCollected, SocialInsuColleMonth.class);
	}

}
