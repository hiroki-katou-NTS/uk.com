package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveOutput;

/**
 * 年休積立年休情報OUTPUT
 * @author shuichu_ishida
 */
@Getter
public class AnnualAndReserveLeaveOutput {

	/** 年休 */
	private AnnualLeaveOutput annualLeave;
	/** 積立年休 */
	private ReserveLeaveOutput reserveLeave;
	
	/**
	 * コンストラクタ
	 */
	public AnnualAndReserveLeaveOutput(){
		
		this.annualLeave = new AnnualLeaveOutput();
		this.reserveLeave = new ReserveLeaveOutput();
	}
	
	/**
	 * ファクトリー
	 * @param annualLeave 年休
	 * @param reserveLeave 積立年休
	 * @return 年休積立年休情報OUTPUT
	 */
	public static AnnualAndReserveLeaveOutput of(
			AnnualLeaveOutput annualLeave,
			ReserveLeaveOutput reserveLeave){
		
		AnnualAndReserveLeaveOutput domain = new AnnualAndReserveLeaveOutput();
		domain.annualLeave = annualLeave;
		domain.reserveLeave = reserveLeave;
		return domain;
	}
}
