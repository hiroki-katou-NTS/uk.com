package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.TimeItemTypeAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.TimeItemName;

public class BonusPayTimeItemTest {

	/**
	 * test [1] 加給時間に対応する日次の勤怠項目を取得する
	 * @加給項目区分 == 加給	
	 */
	@Test
	public void testGetDaiLyAttendanceId_1() {
		List<Integer> listAttdId = new ArrayList<>();
		//加給項目NO == 1
		BonusPayTimeItem domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(316,336,346,356);
		//加給項目NO == 2
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 2, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(317,337,347,357);
		//加給項目NO == 3
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 3, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(318,338,348,358);
		//加給項目NO == 4
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 4, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(319,339,349,359);
		//加給項目NO == 5
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 5, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(320,340,350,360);
		//加給項目NO == 6
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 6, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(321,341,351,361);
		//加給項目NO == 7
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 7, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(322,342,352,362);
		//加給項目NO == 8
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 8, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(323,343,353,363);
		//加給項目NO == 9
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 9, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(324,344,354,364);
		//加給項目NO == 10
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 10, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(325,345,355,365);
	}
	
	/**
	 * test [1] 加給時間に対応する日次の勤怠項目を取得する
	 * @加給項目区分 != 加給	
	 */
	@Test
	public void testGetDaiLyAttendanceId_2() {
		List<Integer> listAttdId = new ArrayList<>();
		//加給項目NO == 1
		BonusPayTimeItem domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(366,386,396,406);
		//加給項目NO == 2
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 2, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(367,387,397,407);
		//加給項目NO == 3
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 3, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(368,388,398,408);
		//加給項目NO == 4
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 4, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(369,389,399,409);
		//加給項目NO == 5
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 5, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(370,390,400,410);
		//加給項目NO == 6
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 6, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(371,391,401,411);
		//加給項目NO == 7
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 7, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(372,392,402,412);
		//加給項目NO == 8
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 8, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(373,393,403,413);
		//加給項目NO == 9
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 9, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(374,394,404,414);
		//加給項目NO == 10
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 10, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(375,395,405,415);
	}
	
	/**
	 * test [2] 加給時間に対応する月次の勤怠項目を取得する
	 * @加給項目区分 == 加給	
	 */
	@Test
	public void testGetMonthlyAttendanceId_1() {
		List<Integer> listAttdId = new ArrayList<>();
		//加給項目NO == 1
		BonusPayTimeItem domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(334,344,2097,2117);
		//加給項目NO == 2
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 2, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(335,345,2098,2118);
		//加給項目NO == 3
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 3, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(336,346,2099,2119);
		//加給項目NO == 4
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 4, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(337,347,2100,2120);
		//加給項目NO == 5
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 5, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(338,348,2101,2121);
		//加給項目NO == 6
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 6, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(339,349,2102,2122);
		//加給項目NO == 7
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 7, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(340,350,2103,2123);
		//加給項目NO == 8
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 8, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(341,351,2104,2124);
		//加給項目NO == 9
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 9, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(342,352,2105,2125);
		//加給項目NO == 10
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 10, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(343,353,2106,2126);
	}
	
	/**
	 * test [2] 加給時間に対応する月次の勤怠項目を取得する
	 * @加給項目区分 != 加給	
	 */
	@Test
	public void testGetMonthlyAttendanceId_2() {
		List<Integer> listAttdId = new ArrayList<>();
		//加給項目NO == 1
		BonusPayTimeItem domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(354,364,2107,2127);
		//加給項目NO == 2
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 2, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(355,365,2108,2128);
		//加給項目NO == 3
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 3, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(356,366,2109,2129);
		//加給項目NO == 4
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 4, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(357,367,2110,2130);
		//加給項目NO == 5
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 5, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(358,368,2111,2131);
		//加給項目NO == 6
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 6, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(359,369,2112,2132);
		//加給項目NO == 7
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 7, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(360,370,2113,2133);
		//加給項目NO == 8
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 8, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(361,371,2114,2134);
		//加給項目NO == 9
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 9, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(362,372,2115,2135);
		//加給項目NO == 10
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 10, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceId();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(363,373,2116,2136);
	}
	
	/**
	 * test [3] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//加給項目NO == 1
		//@加給項目区分 == 加給
		//@使用区分 == 使用しない
		BonusPayTimeItem domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(316,336,346,356);
		//加給項目NO == 1
		//@加給項目区分 == 加給
		//@使用区分 != 使用しない
		domain = new BonusPayTimeItem("companyId", UseAtr.USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
		
		//加給項目NO == 1
		//@加給項目区分 != 加給
		//@使用区分 == 使用しない
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(366,386,396,406);
		//加給項目NO == 1
		//@加給項目区分 != 加給
		//@使用区分 != 使用しない
		domain = new BonusPayTimeItem("companyId", UseAtr.USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getDaiLyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}
	
	/**
	 * test [4] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//加給項目NO == 1
		//@加給項目区分 == 加給
		//@使用区分 == 使用しない
		BonusPayTimeItem domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(334,344,2097,2117);
		//加給項目NO == 1
		//@加給項目区分 == 加給
		//@使用区分 != 使用しない
		domain = new BonusPayTimeItem("companyId", UseAtr.USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.NORMAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
		
		//加給項目NO == 1
		//@加給項目区分 != 加給
		//@使用区分 == 使用しない
		domain = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(354,364,2107,2127);
		//加給項目NO == 1
		//@加給項目区分 != 加給
		//@使用区分 != 使用しない
		domain = new BonusPayTimeItem("companyId", UseAtr.USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.SPECIAL_TYPE);
		listAttdId  = domain.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}

}
