package nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 任意集計期間
 * @author shuichu_ishida
 */
@Getter
public class AnyAggrPeriod extends AggregateRoot {

	/** 会社ID */
	private String companyId;
	/** 集計枠コード */
	private AnyAggrFrameCode aggrFrameCode;
	/** 名称 */
	private AnyAggrName name;
	/** 期間 */
	private DatePeriod period;
	
	/**
	 * コンストラクタ
	 * @param companyId　会社ID
	 * @param aggrFrameCode 集計枠コード
	 */
	public AnyAggrPeriod(String companyId, AnyAggrFrameCode aggrFrameCode){
		
		super();
		this.companyId = companyId;
		this.aggrFrameCode = aggrFrameCode;
		
		this.name = new AnyAggrName("");
		this.period = new DatePeriod(GeneralDate.min(), GeneralDate.min());
	}
	
	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param aggrFrameCode 集計枠コード
	 * @param name 名称
	 * @param period 期間
	 * @return
	 */
	public static AnyAggrPeriod of(
			String companyId,
			AnyAggrFrameCode aggrFrameCode,
			AnyAggrName name,
			DatePeriod period){

		AnyAggrPeriod domain = new AnyAggrPeriod(companyId, aggrFrameCode);
		domain.name = name;
		domain.period = period;
		return domain;
	}
}
