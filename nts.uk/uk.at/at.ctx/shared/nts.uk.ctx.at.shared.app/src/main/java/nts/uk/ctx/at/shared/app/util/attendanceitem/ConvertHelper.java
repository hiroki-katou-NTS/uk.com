package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;

public class ConvertHelper {

	public static <T, Q, X> List<T> mapTo(List<Q> original, Function<Q, T> actions){
		return original == null ? new ArrayList<>() : original.stream().map(actions).collect(Collectors.toList());
	}
	
	public static <T> T getEnum(Integer value, Class<T> enumClass){
		if(value == null){
			return null;
		}
		return EnumAdaptor.valueOf(value, enumClass);
	}
}
