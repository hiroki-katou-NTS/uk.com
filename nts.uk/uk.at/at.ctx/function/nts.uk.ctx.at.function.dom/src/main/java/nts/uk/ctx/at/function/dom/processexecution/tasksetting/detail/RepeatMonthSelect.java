package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *	繰り返す月
 */
@Getter
@AllArgsConstructor
public class RepeatMonthSelect {
	/* 1月 */
	private boolean january;
	
	/* 2月 */
	private boolean february;
	
	/* 3月 */
	private boolean march;
	
	/* 4月 */
	private boolean april;
	
	/* 5月 */
	private boolean may;
	
	/* 6月 */
	private boolean june;
	
	/* 7月 */
	private boolean july;
	
	/* 8月 */
	private boolean august;
	
	/* 9月 */
	private boolean september;
	
	/* 10月 */
	private boolean october;
	
	/* 11月 */
	private boolean november;
	
	/* 12月 */
	private boolean december;

	public static RepeatMonthSelect initDefaut() {
		return new RepeatMonthSelect(false, false, false, false, false, false, false, false, false, false, false, false);
	}
	
	public List<Integer> getMonthsAfterEqualsStartMonth(Integer startMonth) {
		List<Integer> allMonthList = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());
		
		List<Integer> pickedMonths = new ArrayList<>();
		if (january) pickedMonths.add(1);
		if (february) pickedMonths.add(2);
		if (march) pickedMonths.add(3);
		if (april) pickedMonths.add(4);
		if (may) pickedMonths.add(5);
		if (june) pickedMonths.add(6);
		if (july) pickedMonths.add(7);
		if (august) pickedMonths.add(8);
		if (september) pickedMonths.add(9);
		if (october) pickedMonths.add(10);
		if (november) pickedMonths.add(11);
		if (december) pickedMonths.add(12);
		
		List<Integer> narrowList = new ArrayList<>();
		for (int i = startMonth; i <= allMonthList.size(); i++) {
			if (pickedMonths.contains(i)) {
				narrowList.add(i);
			}
		}
		return narrowList;
	}
}
