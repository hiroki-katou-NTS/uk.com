package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.YearMonth;

/**
 * 36協定超過情報
 * @author shuichi_ishida
 */
@Getter
public class AgreementExcessInfo {

	/** 超過回数 */
	private int excessTimes;
	/** 年月リスト */
	private List<YearMonth> yearMonths;
	
	public AgreementExcessInfo(){
		this.excessTimes = 0;
		this.yearMonths = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param excessTimes 超過回数
	 * @param yearMonths 年月リスト
	 * @return 36協定釣果情報
	 */
	public static AgreementExcessInfo of(
			int excessTimes,
			List<YearMonth> yearMonths){
		
		AgreementExcessInfo domain = new AgreementExcessInfo();
		domain.excessTimes = excessTimes;
		domain.yearMonths = yearMonths;
		return domain;
	}
}
