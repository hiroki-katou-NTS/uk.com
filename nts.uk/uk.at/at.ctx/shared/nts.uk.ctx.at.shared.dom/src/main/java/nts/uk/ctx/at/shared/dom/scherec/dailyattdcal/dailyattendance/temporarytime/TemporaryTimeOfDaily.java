package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//import org.omg.CORBA.TCKind;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManageReGetClass;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.OutsideWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.TemporaryTimeSheet;

/** 日別実績の臨時時間 */
@Getter
public class TemporaryTimeOfDaily {

	/** 臨時時間: 日別実績の臨時枠時間 */
	private List<TemporaryFrameTimeOfDaily> temporaryTime;
	/** 臨時回数 */
	private TemporaryTimes temporaryTimes;

	public TemporaryTimeOfDaily(
			List<TemporaryFrameTimeOfDaily> temporaryTime,
			TemporaryTimes temporaryTimes) {
		super();
		this.temporaryTime = temporaryTime;
		this.temporaryTimes = temporaryTimes;
	}

	public TemporaryTimeOfDaily() {
		super();
		this.temporaryTime = new ArrayList<>();
		this.temporaryTimes = new TemporaryTimes(0);
	}
	
	/**
	 * 臨時時間枠の枠時間の合計を算出する
	 * @return　total frame time
	 */
	public int totalTemporaryFrameTime() {
		//return new AttendanceTime(this.temporaryTime.stream().map(tc -> tc.getTemporaryTime().v()).collect(Collectors.summingInt(tc -> tc)));
		return this.temporaryTime.stream().map(tc -> tc.getTemporaryTime().v()).collect(Collectors.summingInt(tc -> tc));
	}
	
	/**
	 * 計算処理
	 * @param recordReget 実績
	 * @return 日別勤怠の臨時時間
	 */
	public static TemporaryTimeOfDaily calcProcess(
			ManageReGetClass recordReget){
		
		if (!recordReget.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().isPresent()){
			return new TemporaryTimeOfDaily();
		}
		OutsideWorkTimeSheet outsideWorkTimeSheet = recordReget.getCalculationRangeOfOneDay().getOutsideWorkTimeSheet().get();
		if (!outsideWorkTimeSheet.getTemporaryTimeSheet().isPresent()){
			return new TemporaryTimeOfDaily();
		}
		TemporaryTimeSheet temporaryTimeSheet = outsideWorkTimeSheet.getTemporaryTimeSheet().get();
		
		return new TemporaryTimeOfDaily(
				// 臨時枠時間の取得
				temporaryTimeSheet.getTemporaryFrameTime(),
				// 臨時回数の計算
				temporaryTimeSheet.calcTemporaryTimes());
	}
}
