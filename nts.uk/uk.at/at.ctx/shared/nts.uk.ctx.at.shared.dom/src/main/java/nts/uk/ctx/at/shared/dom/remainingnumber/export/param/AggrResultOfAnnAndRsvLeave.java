package nts.uk.ctx.at.shared.dom.remainingnumber.export.param;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 年休積立年休の集計結果
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AggrResultOfAnnAndRsvLeave {

	/** 年休 */
	private Optional<AggrResultOfAnnualLeave> annualLeave;
	/** 積立年休 */
	private Optional<AggrResultOfReserveLeave> reserveLeave;
	
	/**
	 * コンストラクタ
	 */
	public AggrResultOfAnnAndRsvLeave(){
		
		this.annualLeave = Optional.empty();
		this.reserveLeave = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param annualLeave 年休
	 * @param reserveLeave 積立年休
	 * @return 年休積立年休の集計結果
	 */
	public static AggrResultOfAnnAndRsvLeave of(
			Optional<AggrResultOfAnnualLeave> annualLeave,
			Optional<AggrResultOfReserveLeave> reserveLeave){
		
		AggrResultOfAnnAndRsvLeave domain = new AggrResultOfAnnAndRsvLeave();
		domain.annualLeave = annualLeave;
		domain.reserveLeave = reserveLeave;
		return domain;
	}
}
