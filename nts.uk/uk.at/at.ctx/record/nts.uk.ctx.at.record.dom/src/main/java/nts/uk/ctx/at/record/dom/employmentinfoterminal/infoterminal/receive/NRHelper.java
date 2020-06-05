package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class NRHelper {

	private NRHelper() {
	};

	public static final List<Integer> APPREASON_TYPE = Arrays.asList(0, 1, 2, 6, 7, 8, 9);
	
	protected static final List<String> BENTO_NO = IntStream
			.concat(IntStream.rangeClosed('A', 'Z'),
					IntStream.concat(IntStream.rangeClosed('a', 'z'), IntStream.rangeClosed('0', '9')))
			.mapToObj(x -> String.valueOf(((char) x))).collect(Collectors.toList());

	public static String toStringNR(Integer startTime, Integer endTime) {
		return startTime < 0 ? "-" + toStringNR(startTime) + toStringNR(endTime)
				:  "+" + toStringNR(startTime) + toStringNR(endTime);
	}

	private static String toStringNR(Integer time) {
		return (String.format("%02d", Math.abs(time / 60)) + String.format("%02d", Math.abs(time % 60)));
	}

}
