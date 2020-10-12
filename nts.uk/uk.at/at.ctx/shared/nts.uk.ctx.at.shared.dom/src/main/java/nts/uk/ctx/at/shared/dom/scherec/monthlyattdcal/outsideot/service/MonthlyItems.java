package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 月次項目一覧
 */
public enum MonthlyItems {
	// フレックス超過時間
	LEGAL_FLEX_TIME(15, "フレックス法定内時間", null),
	ILLEGAL_FLEX_TIME(16, "フレックス法定外時間", null),
	FLEX_EXCESS_TIME(17, "フレックス超過時間", null),
	
	// 残業時間
	OVERTIME_1(35, "残業時間1", 1),
	OVERTIME_2(36, "残業時間2", 2),
	OVERTIME_3(37, "残業時間3", 3),
	OVERTIME_4(38, "残業時間4", 4),
	OVERTIME_5(39, "残業時間5", 5),
	OVERTIME_6(40, "残業時間6", 6),
	OVERTIME_7(41, "残業時間7", 7),
	OVERTIME_8(42, "残業時間8", 8),
	OVERTIME_9(43, "残業時間9", 9),
	OVERTIME_10(44, "残業時間10", 10),
	
	// 振替残業時間
	TRANS_OVERTIME_1(68, "振替残業時間1", 1),
	TRANS_OVERTIME_2(69, "振替残業時間2", 2),
	TRANS_OVERTIME_3(70, "振替残業時間3", 3),
	TRANS_OVERTIME_4(71, "振替残業時間4", 4),
	TRANS_OVERTIME_5(72, "振替残業時間5", 5),
	TRANS_OVERTIME_6(73, "振替残業時間6", 6),
	TRANS_OVERTIME_7(74, "振替残業時間7", 7),
	TRANS_OVERTIME_8(75, "振替残業時間8", 8),
	TRANS_OVERTIME_9(76, "振替残業時間9", 9),
	TRANS_OVERTIME_10(77, "振替残業時間10", 10),
	
	// 休出時間
	BREAKTIME_1(110, "休出時間1", 1),
	BREAKTIME_2(111, "休出時間2", 2),
	BREAKTIME_3(112, "休出時間3", 3),
	BREAKTIME_4(113, "休出時間4", 4),
	BREAKTIME_5(114, "休出時間5", 5),
	BREAKTIME_6(115, "休出時間6", 6),
	BREAKTIME_7(116, "休出時間7", 7),
	BREAKTIME_8(117, "休出時間8", 8),
	BREAKTIME_9(118, "休出時間9", 9),
	BREAKTIME_10(119, "休出時間10", 10),
	
	// 休出時間
	TRANSTIME_1(143, "振替時間1", 1),
	TRANSTIME_2(144, "振替時間2", 2),
	TRANSTIME_3(145, "振替時間3", 3),
	TRANSTIME_4(146, "振替時間4", 4),
	TRANSTIME_5(147, "振替時間5", 5),
	TRANSTIME_6(148, "振替時間6", 6),
	TRANSTIME_7(149, "振替時間7", 7),
	TRANSTIME_8(150, "振替時間8", 8),
	TRANSTIME_9(151, "振替時間9", 9),
	TRANSTIME_10(152, "振替時間10", 10);
	
	public final int itemId;
	public final String itemName;
	public final Integer frameNo;

	private MonthlyItems(int itemId, String itemName, Integer frameNo) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.frameNo = frameNo;
	}
	
	public static List<MonthlyItems> findOverTime() {
		return Arrays.stream(MonthlyItems.values())
			.filter(x -> x.itemId >= MonthlyItems.OVERTIME_1.itemId && x.itemId <= MonthlyItems.TRANS_OVERTIME_10.itemId)
			.collect(Collectors.toList());		
	}
	
	public static List<MonthlyItems> findBreakTime() {
		return Arrays.stream(MonthlyItems.values())
			.filter(x -> x.itemId >= MonthlyItems.BREAKTIME_1.itemId && x.itemId <= MonthlyItems.TRANSTIME_10.itemId)
			.collect(Collectors.toList());		
	}
}
