package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;

/** 乖離時間 */
@Getter
public class DivergenceTime {

	//乖離時間
	private AttendanceTimeOfExistMinus divTime;
	
	//乖離時間NO - primitive value
	private int divTimeId;
	
	//乖離理由
	@Setter
	private Optional<DivergenceReasonContent> divReason;
	
	//乖離理由コード
	@Setter
	private Optional<DiverdenceReasonCode> divResonCode;

	public DivergenceTime(
			AttendanceTimeOfExistMinus divTime,
			int divTimeId,
			DivergenceReasonContent divReason,
			DiverdenceReasonCode divResonCode) {
		
		super();
		this.divTime = divTime;
		this.divTimeId = divTimeId;
		this.divReason = Optional.ofNullable(divReason);
		this.divResonCode = Optional.ofNullable(divResonCode);
	}
	
	public DivergenceTime(
			AttendanceTimeOfExistMinus divTime,
			int divTimeId,
			Optional<DivergenceReasonContent> divReason,
			Optional<DiverdenceReasonCode> divResonCode) {
		
		super();
		this.divTime = divTime;
		this.divTimeId = divTimeId;
		this.divReason = (divReason);
		this.divResonCode = (divResonCode);
	}
	
	/**
	 * 乖離時間のチェック
	 * @return
	 */
	public Optional<EmployeeDailyPerError> checkDivergenceTime(){
		return Optional.empty();
	}
	
	public static DivergenceTime createDefaultWithNo(int no) {
		return new DivergenceTime(new AttendanceTimeOfExistMinus(0), no, Optional.empty(), Optional.empty());
	}
}
