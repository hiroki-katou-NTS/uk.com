package nts.uk.ctx.at.shared.dom.common.time;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.uk.shr.com.time.TimeWithDayAttr;

public interface HasTimeSpanList<T extends HasTimeSpanForCalc<T>> {

	List<T> getTimeSpanList();
	
	default List<T> extractBetween(TimeWithDayAttr start, TimeWithDayAttr end) {
		val targetSpan = new TimeSpanForCalc(start, end);
		
		List<T> result = new ArrayList<>();
		this.getTimeSpanList().stream().forEach(source -> {
			source.getTimeSpan().getDuplicatedWith(targetSpan).ifPresent(duplicated -> {
				result.add(source.newSpanWith(duplicated.getStart(), duplicated.getEnd()));
			});
		});
		return result;
	}
}
