package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 申告計算時刻
 * @author shuichi_ishida
 */
@Getter
@Setter
public class DeclareCalcClock {

	/** 普通残業終了 */
	private Optional<TimeWithDayAttr> overtimeEnd;
	/** 普通残業深夜開始 */
	private Optional<TimeWithDayAttr> overtimeMnStart;
	/** 早出残業開始 */
	private Optional<TimeWithDayAttr> earlyOtStart;
	/** 早出残業深夜開始 */
	private Optional<TimeWithDayAttr> earlyOtMnStart;
	/** 休出終了 */
	private Optional<TimeWithDayAttr> holidayWorkEnd;
	/** 休出深夜開始 */
	private Optional<TimeWithDayAttr> holidayWorkMnStart;

	/**
	 * コンストラクタ
	 */
	public DeclareCalcClock(){
		this.overtimeEnd = Optional.empty();
		this.overtimeMnStart = Optional.empty();
		this.earlyOtStart = Optional.empty();
		this.earlyOtMnStart = Optional.empty();
		this.holidayWorkEnd = Optional.empty();
		this.holidayWorkMnStart = Optional.empty();
	}
}
