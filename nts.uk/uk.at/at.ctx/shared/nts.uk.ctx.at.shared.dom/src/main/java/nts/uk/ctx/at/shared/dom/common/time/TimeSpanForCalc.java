package nts.uk.ctx.at.shared.dom.common.time;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.ComparableRange;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * Calculate time from start to end. 計算時間帯
 * 
 * 開始～終了の範囲の最大値は48時間
 * 
 * @author keisuke_hoshina
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class TimeSpanForCalc extends DomainObject implements ComparableRange<Integer> {

	private final TimeWithDayAttr start;
	private final TimeWithDayAttr end;

	/**
	 * 開始から終了までの時間を分の整数で返す Returns time as minutes from start to end.
	 * 
	 * @return 分の整数
	 */
	public int lengthAsMinutes() {
		return this.end.v() - this.start.v();
	}

	/**
	 * 指定時刻が時間帯に含まれているか判断する Returns true if a given time is contained by this time
	 * span.
	 * 
	 * @param time
	 *            指定時刻
	 * @return 含まれていればtrue
	 */
	public boolean contains(TimeWithDayAttr time) {
		return this.start.lessThanOrEqualTo(time) && this.end.greaterThanOrEqualTo(time);
	}

	/**
	 * 時間帯を基準時間の前後で分割する
	 * 
	 * @param basePoint
	 *            時間帯を分割する時の基準時間
	 * @return 分割後の時間帯List
	 */
	public List<TimeSpanForCalc> timeDivide(TimeWithDayAttr basePoint) {
		return Arrays.asList(
				new TimeSpanForCalc(this.start, basePoint),
				new TimeSpanForCalc(basePoint, this.end));
	}

	/**
	 * 開始時刻と終了時刻を後ろにズラす
	 * 
	 * @param minutesToShiftAhead 移動させる時間（分）
	 * @return ズラした後の開始時刻と終了時刻
	 */
	public TimeSpanForCalc shiftAhead(int minutesToShiftAhead) {
		return new TimeSpanForCalc(
				this.start.shiftWithLimit(minutesToShiftAhead),
				this.end.shiftWithLimit(minutesToShiftAhead));
	}

	/**
	 * 開始時刻と終了時刻を手前にズラす
	 * 
	 * @param minutesToMoveBack　移動させる時間（分）
	 * @return ズラした後の開始時刻と終了時刻
	 */
	public TimeSpanForCalc shiftBack(int minutesToShiftBack) {
		return new TimeSpanForCalc(
				this.start.shiftWithLimit(-minutesToShiftBack),
				this.end.shiftWithLimit(-minutesToShiftBack));
	}
	
	/**
	 * 指定した開始時刻に合わせてずらす
	 * @param start 新しい開始時刻
	 * @return ズラした後の開始時刻と終了時刻
	 */
	public TimeSpanForCalc shiftWithStartAt(TimeWithDayAttr newStart) {
		return this.shiftAhead(newStart.valueAsMinutes() - this.start.valueAsMinutes());
	}

	/**
	 * 比較元と比較したい時間帯の位置関係を判定する
	 * @param other 比較したい時間帯
	 * @return　重複状態区分
	 */
	public TimeSpanDuplication checkDuplication(TimeSpanForCalc other) {
		return TimeSpanDuplication.createFrom(this.compare(other));
	}
	
	/**
	 * 重複している時間帯を返す
	 * @param other 比較対象
	 * @return 重複部分
	 */
	public Optional<TimeSpanForCalc> getDuplicatedWith(TimeSpanForCalc other) {
		TimeSpanForCalc result;
		
		if (this.contains(other)) {
			result = other;
		} else if (other.contains(this)) {
			result = this;
		} else if (this.contains(other.start)) {
			result = new TimeSpanForCalc(other.start, this.end);
		} else if (this.contains(other.end)) {
			result = new TimeSpanForCalc(this.start, other.end);
		} else {
			return Optional.empty();
		}
		
		if (result.lengthAsMinutes() == 0) {
			return Optional.empty();
		}
		
		return Optional.of(result);
	}
	
	/**
	 *　重複している時間分時間帯をずらす
	 * @param other 比較したい時間帯
	 * @return 時間帯の重複状況
	 */
	public TimeSpanForCalc reviseToAvoidDuplicatingWith(TimeSpanForCalc other) {
		if (this.checkDuplication(other).isDuplicated()) {
			return other.shiftWithStartAt(this.end);
		}
		return other;
	}

	@Override
	public Integer startValue() {
		return this.start.v();
	}

	@Override
	public Integer endValue() {
		return this.end.v();
	}

	@Override
	public Integer startNext(boolean isIncrement) {
		return isIncrement ? this.startValue() + 1 : this.startValue() - 1;
	}

	@Override
	public Integer endNext(boolean isIncrement) {
		return isIncrement ? this.endValue() + 1 : this.endValue() - 1;
	}
}
