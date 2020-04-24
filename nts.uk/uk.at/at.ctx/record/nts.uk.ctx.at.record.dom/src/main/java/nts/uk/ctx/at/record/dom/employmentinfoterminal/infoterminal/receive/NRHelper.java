package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NRHelper {
	
	private NRHelper() {};
	
	public static final  List<String> BENTO_NO = IntStream
			.concat(IntStream.rangeClosed('A', 'Z'),
					IntStream.concat(IntStream.rangeClosed('a', 'z'), IntStream.rangeClosed('0', '9')))
			.mapToObj(x -> String.valueOf(((char) x))).collect(Collectors.toList());
}
