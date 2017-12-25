package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConvertHelper {

	public static <T, Q, X> List<T> mapTo(List<Q> original, Function<Q, T> actions){
		return original.stream().map(actions).collect(Collectors.toList());
	}
}
