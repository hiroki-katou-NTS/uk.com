package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PremiumItemTest {

	/**
	 * test [1] 割増項目に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//割増項目NO == 1
		PremiumItem domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(1), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(426,1295,1310,1320,1339)) ).isTrue();
		//割増項目NO == 2
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(2), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(427,1296,1311,1321,1340)) ).isTrue();
		//割増項目NO == 3
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(3), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(428,1297,1312,1322,1341)) ).isTrue();
		//割増項目NO == 4
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(4), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(429,1298,1313,1323,1342)) ).isTrue();
		//割増項目NO == 5
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(5), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(430,1299,1314,1324,1343)) ).isTrue();
		//割増項目NO == 6
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(6), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(431,1300,1315,1325,1344)) ).isTrue();
		//割増項目NO == 7
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(7), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(432,1301,1316,1326,1345)) ).isTrue();
		//割増項目NO == 8
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(8), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(433,1302,1317,1327,1346)) ).isTrue();
		//割増項目NO == 9
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(9), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(434,1303,1318,1328,1347)) ).isTrue();
		//割増項目NO == 10
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(10), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(435,1304,1319,1329,1348)) ).isTrue();
	}
	
	/**
	 * test [2] 割増項目に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGgtMonthlyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//割増項目NO == 1
		PremiumItem domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(1), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(378,2083)) ).isTrue();
		//割増項目NO == 2
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(2), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(379,2084)) ).isTrue();
		//割増項目NO == 3
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(3), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(380,2085)) ).isTrue();
		//割増項目NO == 4
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(4), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(381,2086)) ).isTrue();
		//割増項目NO == 5
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(5), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(382,2087)) ).isTrue();
		//割増項目NO == 6
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(6), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(383,2088)) ).isTrue();
		//割増項目NO == 7
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(7), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(384,2089)) ).isTrue();
		//割増項目NO == 8
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(8), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(385,2090)) ).isTrue();
		//割増項目NO == 9
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(9), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(386,2091)) ).isTrue();
		//割増項目NO == 10
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(10), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId.containsAll(Arrays.asList(387,2092)) ).isTrue();
	}

	/**
	 * test [3] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//割増項目NO == 1 && @使用区分 == 使用しない
		PremiumItem domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(1), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getDailyAttendanceIdNotAvailable();
		assertThat( listAttdId.containsAll(Arrays.asList(426,1295,1310,1320,1339)) ).isTrue();
		
		//割増項目NO == 1 && @使用区分 != 使用しない
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(1), new PremiumName("name"), UseAttribute.Use);
		listAttdId  = domain.getDailyAttendanceIdNotAvailable();
		assertThat( listAttdId).isEmpty();
	}
	
	/**
	 * test [4] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//割増項目NO == 1 && @使用区分 == 使用しない
		PremiumItem domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(1), new PremiumName("name"), UseAttribute.NotUse);
		listAttdId  = domain.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId.containsAll(Arrays.asList(378,2083)) ).isTrue();
		
		//割増項目NO == 1 && @使用区分 != 使用しない
		domain = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(1), new PremiumName("name"), UseAttribute.Use);
		listAttdId  = domain.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId).isEmpty();
	}
	
}
