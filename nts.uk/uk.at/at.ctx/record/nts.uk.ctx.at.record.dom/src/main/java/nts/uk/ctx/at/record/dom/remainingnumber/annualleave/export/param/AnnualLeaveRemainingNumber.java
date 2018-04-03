package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveMaxRemainingTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.RealAnnualLeave;

/**
 * 年休情報残数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveRemainingNumber {

	/** 年休（マイナスなし） */
	private AnnualLeave annualLeaveNoMinus;
	/** 年休（マイナスあり） */
	private RealAnnualLeave annualLeaveWithMinus;
	/** 年休（マイナスあり）付与前 */
	private Optional<RealAnnualLeave> annualLeaveWithMinusBeforeGrant;
	/** 半日年休 */
	private Optional<HalfDayAnnualLeave> halfDayAnnualLeave;
	/** 時間年休 */
	private Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeave;

	/**
	 * コンストラクタ
	 */
	public AnnualLeaveRemainingNumber(){
		
		this.annualLeaveNoMinus = new AnnualLeave();
		this.annualLeaveWithMinus = new RealAnnualLeave();
		this.annualLeaveWithMinusBeforeGrant = Optional.empty();
		this.halfDayAnnualLeave = Optional.empty();
		this.timeAnnualLeave = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param annualLeaveNoMinus 年休（マイナスなし）
	 * @param annualLeaveWithMinus 年休（マイナスあり）
	 * @param annualLeaveWithMinusBeforeGrant 年休（マイナスあり）付与前
	 * @param halfDayAnnualLeave 半日年休
	 * @param timeAnnualLeave 時間年休
	 * @return 年休情報残数
	 */
	public static AnnualLeaveRemainingNumber of(
			AnnualLeave annualLeaveNoMinus,
			RealAnnualLeave annualLeaveWithMinus,
			Optional<RealAnnualLeave> annualLeaveWithMinusBeforeGrant,
			Optional<HalfDayAnnualLeave> halfDayAnnualLeave,
			Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeave){
		
		AnnualLeaveRemainingNumber domain = new AnnualLeaveRemainingNumber();
		domain.annualLeaveNoMinus = annualLeaveNoMinus;
		domain.annualLeaveWithMinus = annualLeaveWithMinus;
		domain.annualLeaveWithMinusBeforeGrant = annualLeaveWithMinusBeforeGrant;
		domain.halfDayAnnualLeave = halfDayAnnualLeave;
		domain.timeAnnualLeave = timeAnnualLeave;
		return domain;
	}
}
