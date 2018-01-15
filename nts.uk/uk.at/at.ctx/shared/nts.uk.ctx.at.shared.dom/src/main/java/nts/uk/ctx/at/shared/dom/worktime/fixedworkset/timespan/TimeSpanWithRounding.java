package nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 時間帯(丸め付き)
 * @author keisuke_hoshina
 *
 */
public class TimeSpanWithRounding extends TimeSpanForCalc {

	@Getter
	private final Finally<TimeRoundingSetting> rounding;
	
	public TimeSpanWithRounding(TimeWithDayAttr start, TimeWithDayAttr end, Finally<TimeRoundingSetting> rounding) {
		super(start, end);
		this.rounding = rounding;
	}
	
	public int roundedLengthAsMinutesWithDeductingBy(int deductingTimeAsMinutes) {
		return this.rounding.get().round(this.lengthAsMinutes() - deductingTimeAsMinutes);
	}
	
	public TimeSpanWithRounding newTimeSpan(TimeSpanForCalc newTimeSpan) {
		return new TimeSpanWithRounding(newTimeSpan.getStart(), newTimeSpan.getEnd(), rounding);
	}
	
	
	/**
	 * 複数の時間帯（丸め付）クラスを1つの時間帯（丸め付）クラスとして結合する
	 * @author ken_takasu
	 * @param sources
	 * @return
	 */
	public static Optional<TimeSpanWithRounding> joinedTimeSpanWithRounding(Collection<TimeSpanWithRounding> sources) {
		if (sources.isEmpty()) {
			return Optional.empty();
		}
		
		val rounding = sources.stream().findAny().get().getRounding();
		if (!sources.stream().allMatch(s -> s.getRounding().equals(rounding))) {
			throw new RuntimeException("丸め設定が異なるものが混じっている");
		}

		val start = sources.stream().map(s -> s.getStart()).min(Comparator.naturalOrder()).get();
		val end = sources.stream().map(s -> s.getEnd()).max(Comparator.naturalOrder()).get();

		return Optional.of(new TimeSpanWithRounding(start, end, rounding));
	}
}
