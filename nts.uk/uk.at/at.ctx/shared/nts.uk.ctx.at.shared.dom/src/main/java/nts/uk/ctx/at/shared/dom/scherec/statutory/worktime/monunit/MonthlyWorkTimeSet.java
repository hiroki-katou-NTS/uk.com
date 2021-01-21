package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;

/** 月単位労働時間 */
@Getter
public abstract class MonthlyWorkTimeSet extends AggregateRoot implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	private String comId;
	
	/** 勤務区分 */
	private LaborWorkTypeAttr laborAttr;
	
	/** 年月 */
	private YearMonth ym;
	
	/** 月労働時間 */
	private MonthlyLaborTime laborTime;
	
	protected MonthlyWorkTimeSet(String comId, LaborWorkTypeAttr laborAttr, 
			YearMonth ym, MonthlyLaborTime laborTime) {
		
		this.comId = comId;
		this.laborAttr = laborAttr;
		this.laborTime = laborTime;
		this.ym = ym;
	}
	
	/** 労働勤務区分 */
	@AllArgsConstructor
	public static enum LaborWorkTypeAttr {
		
		/** 通常勤務 */
		REGULAR_LABOR(0),

		/** 変形労働 */
		DEFOR_LABOR(1),

		/** フレックス勤務 */
		FLEX(2);
		
		public int value;
	}
}
