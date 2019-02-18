package nts.uk.ctx.at.shared.dom.monthly.agreement;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

/**
 * 36協定上限複数月平均時間
 * @author shuichi_ishida
 */
@Getter
public class AgreMaxAverageTimeMulti {

	/** 上限時間 */
	private LimitOneMonth maxTime;
	/** 平均時間 */
	private List<AgreMaxAverageTime> averageTimeList;
	
	/**
	 * コンストラクタ
	 */
	public AgreMaxAverageTimeMulti() {
		this.maxTime = new LimitOneMonth(0);
		this.averageTimeList = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param maxTime 上限時間
	 * @param averageTimeList 平均時間
	 * @return 36協定上限複数月平均時間
	 */
	public static AgreMaxAverageTimeMulti of(
			LimitOneMonth maxTime,
			List<AgreMaxAverageTime> averageTimeList) {
		
		AgreMaxAverageTimeMulti domain = new AgreMaxAverageTimeMulti();
		domain.maxTime = maxTime;
		domain.averageTimeList = averageTimeList;
		return domain;
	}
}
