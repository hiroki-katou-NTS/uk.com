package nts.uk.ctx.at.shared.dom.specialholiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantRegular;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.primitive.Memo;

@RunWith(JMockit.class)
public class SpecialHolidayTest {
	@Injectable
	private SpecialHoliday.Require require;

	@Test
	public void getters() {
		SpecialHoliday specialHoliday = new SpecialHoliday("000000000008-0006", new SpecialHolidayCode(1),
				new SpecialHolidayName("Name"), new GrantRegular(), new SpecialLeaveRestriction(), new TargetItem(),
				NotUseAtr.USE, NotUseAtr.USE, new Memo("Memo"));
		NtsAssert.invokeGetters(specialHoliday);
	}

	/**
	 * Test [1] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItems() {
		// ========== Case 1
			// $時間特別休暇の管理設定.時間休暇消化単位.管理区分 == 管理しない
			Optional<TimeSpecialLeaveManagementSetting> timeMana = Optional.of(new TimeSpecialLeaveManagementSetting(
					"000000000008-0006", // dummy
					TimeDigestiveUnit.OneHour, // dummy
					ManageDistinct.NO));
	
			new Expectations() {
				{
					require.findByCompany("000000000008-0006");
					result = timeMana;
				}
			};

			// 特別休暇コード = 1 :
			SpecialHoliday specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(1));
			List<Integer> lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1446, 1447, 1448, 1449, 1450, 1451, 1452, 1453, 1454, 1455, 1456);
	
			// 特別休暇コード = 2 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(2));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1457, 1458, 1459, 1460, 1461, 1462, 1463, 1464, 1465, 1466, 1467);
	
			// 特別休暇コード = 3 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(3));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1468, 1469, 1470, 1471, 1472, 1473, 1474, 1475, 1476, 1477, 1478);
	
			// 特別休暇コード = 4 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(4));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1479, 1480, 1481, 1482, 1483, 1484, 1485, 1486, 1487, 1488, 1489);
	
			// 特別休暇コード = 5 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(5));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1490, 1491, 1492, 1493, 1494, 1495, 1496, 1497, 1498, 1499, 1500);
	
			// 特別休暇コード = 6 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(6));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1501, 1502, 1503, 1504, 1505, 1506, 1507, 1508, 1509, 1510, 1511);
	
			// 特別休暇コード = 7 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(7));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1512, 1513, 1514, 1515, 1516, 1517, 1518, 1519, 1520, 1521, 1522);
	
			// 特別休暇コード = 8 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(8));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1523, 1524, 1525, 1526, 1527, 1528, 1529, 1530, 1531, 1532, 1533);
	
			// 特別休暇コード = 9 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(9));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1534, 1535, 1536, 1537, 1538, 1539, 1540, 1541, 1542, 1543, 1544);
	
			// 特別休暇コード = 10 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(10));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1545, 1546, 1547, 1548, 1549, 1550, 1551, 1552, 1553, 1554, 1555);
	
			// 特別休暇コード = 11 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(11));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1556, 1557, 1558, 1559, 1560, 1561, 1562, 1563, 1564, 1565, 1566);
	
			// 特別休暇コード = 12 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(12));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1567, 1568, 1569, 1570, 1571, 1572, 1573, 1574, 1575, 1576, 1577);
	
			// 特別休暇コード = 13 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(13));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1578, 1579, 1580, 1581, 1582, 1583, 1584, 1585, 1586, 1587, 1588);
	
			// 特別休暇コード = 14 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(14));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1589, 1590, 1591, 1592, 1593, 1594, 1595, 1596, 1597, 1598, 1599);
	
			// 特別休暇コード = 15 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(15));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1600, 1601, 1602, 1603, 1604, 1605, 1606, 1607, 1608, 1609, 1610);
	
			// 特別休暇コード = 16 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(16));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1611, 1612, 1613, 1614, 1615, 1616, 1617, 1618, 1619, 1620, 1621);
	
			// 特別休暇コード = 17 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(17));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1622, 1623, 1624, 1625, 1626, 1627, 1628, 1629, 1630, 1631, 1632);
	
			// 特別休暇コード = 18 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(18));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1642, 1643);
	
			// 特別休暇コード = 19 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(19));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1644, 1645, 1646, 1647, 1648, 1649, 1650, 1651, 1652, 1653, 1654);
	
			// 特別休暇コード = 20 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(20));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1655, 1656, 1657, 1658, 1659, 1660, 1661, 1662, 1663, 1664, 1665);
			
		// ========== Case 2
			// $時間特別休暇の管理設定.時間休暇消化単位.管理区分 == 管理する
			Optional<TimeSpecialLeaveManagementSetting> timeMana2 = Optional.of(new TimeSpecialLeaveManagementSetting(
					"000000000008-0006", // dummy
					TimeDigestiveUnit.OneHour,// dummy
					ManageDistinct.YES));

			new Expectations() {
				{
					require.findByCompany("000000000008-0006");
					result = timeMana2;
				}
			};

			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(1));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId.isEmpty()).isTrue();
			
		// ========== Case 3	
			// 時間特別休暇の管理設定 = empty
			new Expectations() {
				{
					require.findByCompany("000000000008-0006");
				}
			};

			// 特別休暇コード = 1 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(1));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1446, 1447, 1448, 1449, 1450, 1451, 1452, 1453, 1454, 1455, 1456);
	
			// 特別休暇コード = 2 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(2));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1457, 1458, 1459, 1460, 1461, 1462, 1463, 1464, 1465, 1466, 1467);
	
			// 特別休暇コード = 3 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(3));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1468, 1469, 1470, 1471, 1472, 1473, 1474, 1475, 1476, 1477, 1478);
	
			// 特別休暇コード = 4 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(4));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1479, 1480, 1481, 1482, 1483, 1484, 1485, 1486, 1487, 1488, 1489);
	
			// 特別休暇コード = 5 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(5));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1490, 1491, 1492, 1493, 1494, 1495, 1496, 1497, 1498, 1499, 1500);
	
			// 特別休暇コード = 6 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(6));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1501, 1502, 1503, 1504, 1505, 1506, 1507, 1508, 1509, 1510, 1511);
	
			// 特別休暇コード = 7 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(7));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1512, 1513, 1514, 1515, 1516, 1517, 1518, 1519, 1520, 1521, 1522);
	
			// 特別休暇コード = 8 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(8));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1523, 1524, 1525, 1526, 1527, 1528, 1529, 1530, 1531, 1532, 1533);
	
			// 特別休暇コード = 9 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(9));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1534, 1535, 1536, 1537, 1538, 1539, 1540, 1541, 1542, 1543, 1544);
	
			// 特別休暇コード = 10 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(10));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1545, 1546, 1547, 1548, 1549, 1550, 1551, 1552, 1553, 1554, 1555);
	
			// 特別休暇コード = 11 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(11));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1556, 1557, 1558, 1559, 1560, 1561, 1562, 1563, 1564, 1565, 1566);
	
			// 特別休暇コード = 12 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(12));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1567, 1568, 1569, 1570, 1571, 1572, 1573, 1574, 1575, 1576, 1577);
	
			// 特別休暇コード = 13 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(13));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1578, 1579, 1580, 1581, 1582, 1583, 1584, 1585, 1586, 1587, 1588);
	
			// 特別休暇コード = 14 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(14));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1589, 1590, 1591, 1592, 1593, 1594, 1595, 1596, 1597, 1598, 1599);
	
			// 特別休暇コード = 15 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(15));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1600, 1601, 1602, 1603, 1604, 1605, 1606, 1607, 1608, 1609, 1610);
	
			// 特別休暇コード = 16 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(16));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1611, 1612, 1613, 1614, 1615, 1616, 1617, 1618, 1619, 1620, 1621);
	
			// 特別休暇コード = 17 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(17));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1622, 1623, 1624, 1625, 1626, 1627, 1628, 1629, 1630, 1631, 1632);
	
			// 特別休暇コード = 18 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(18));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1642, 1643);
	
			// 特別休暇コード = 19 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(19));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1644, 1645, 1646, 1647, 1648, 1649, 1650, 1651, 1652, 1653, 1654);
	
			// 特別休暇コード = 20 :
			specialHoliday = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(20));
			lstId = specialHoliday.getMonthlyAttendanceItems(require);
			assertThat(lstId).extracting(d -> d).containsExactly(1655, 1656, 1657, 1658, 1659, 1660, 1661, 1662, 1663, 1664, 1665);
	}
	
	/**
	 * Test [S-1] 時間特別休暇に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttdItemsCorrespondSpecialHoliday() {
		// 特別休暇コード = 1 :
		List<Integer> lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(1)));
		assertThat(lstId).extracting(d -> d).containsExactly(840,841,845,849,851,852,853,857,846,858, 1446, 1447, 1448, 1449, 1450, 1451, 1452, 1453, 1454, 1455, 1456);
		
		// 特別休暇コード = 2 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(2)));
		assertThat(lstId).extracting(d -> d).containsExactly(861,862,866,870,872,873,874,878,867,879, 1457, 1458, 1459, 1460, 1461, 1462, 1463, 1464, 1465, 1466, 1467);

		// 特別休暇コード = 3 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(3)));
		assertThat(lstId).extracting(d -> d).containsExactly(882,883,887,891,893,894,895,899,888,900, 1468, 1469, 1470, 1471, 1472, 1473, 1474, 1475, 1476, 1477, 1478);

		// 特別休暇コード = 4 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(4)));
		assertThat(lstId).extracting(d -> d).containsExactly(903,904,908,912,914,915,916,920,909,921, 1479, 1480, 1481, 1482, 1483, 1484, 1485, 1486, 1487, 1488, 1489);

		// 特別休暇コード = 5 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(5)));
		assertThat(lstId).extracting(d -> d).containsExactly(924,925,929,933,935,936,937,941,930,942, 1490, 1491, 1492, 1493, 1494, 1495, 1496, 1497, 1498, 1499, 1500);

		// 特別休暇コード = 6 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(6)));
		assertThat(lstId).extracting(d -> d).containsExactly(945,946,950,954,956,957,958,962,951,963, 1501, 1502, 1503, 1504, 1505, 1506, 1507, 1508, 1509, 1510, 1511);

		// 特別休暇コード = 7 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(7)));
		assertThat(lstId).extracting(d -> d).containsExactly(966,967,971,975,977,978,979,983,972,984, 1512, 1513, 1514, 1515, 1516, 1517, 1518, 1519, 1520, 1521, 1522);

		// 特別休暇コード = 8 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(8)));
		assertThat(lstId).extracting(d -> d).containsExactly(987,988,992,996,998,999,1000,1004,993,1005, 1523, 1524, 1525, 1526, 1527, 1528, 1529, 1530, 1531, 1532, 1533);

		// 特別休暇コード = 9 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(9)));
		assertThat(lstId).extracting(d -> d).containsExactly(1008,1009,1013,1017,1019,1020,1021,1025,1014,1026, 1534, 1535, 1536, 1537, 1538, 1539, 1540, 1541, 1542, 1543, 1544);

		// 特別休暇コード = 10 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(10)));
		assertThat(lstId).extracting(d -> d).containsExactly(1029,1030,1034,1038,1040,1041,1042,1046,1035,1047, 1545, 1546, 1547, 1548, 1549, 1550, 1551, 1552, 1553, 1554, 1555);

		// 特別休暇コード = 11 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(11)));
		assertThat(lstId).extracting(d -> d).containsExactly(1050,1051,1055,1059,1061,1062,1063,1067,1056,1068, 1556, 1557, 1558, 1559, 1560, 1561, 1562, 1563, 1564, 1565, 1566);

		// 特別休暇コード = 12 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(12)));
		assertThat(lstId).extracting(d -> d).containsExactly(1071,1072,1076,1080,1082,1083,1084,1088,1077,1089, 1567, 1568, 1569, 1570, 1571, 1572, 1573, 1574, 1575, 1576, 1577);

		// 特別休暇コード = 13 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(13)));
		assertThat(lstId).extracting(d -> d).containsExactly(1092,1093,1097,1101,1103,1104,1105,1109,1098,1110, 1578, 1579, 1580, 1581, 1582, 1583, 1584, 1585, 1586, 1587, 1588);

		// 特別休暇コード = 14 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(14)));
		assertThat(lstId).extracting(d -> d).containsExactly(1113,1114,1118,1122,1124,1125,1126,1130,1119,1131, 1589, 1590, 1591, 1592, 1593, 1594, 1595, 1596, 1597, 1598, 1599);

		// 特別休暇コード = 15 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(15)));
		assertThat(lstId).extracting(d -> d).containsExactly(1134,1135,1139,1143,1145,1146,1147,1151,1140,1152, 1600, 1601, 1602, 1603, 1604, 1605, 1606, 1607, 1608, 1609, 1610);

		// 特別休暇コード = 16 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(16)));
		assertThat(lstId).extracting(d -> d).containsExactly(1155,1156,1160,1164,1166,1167,1168,1172,1161,1173, 1611, 1612, 1613, 1614, 1615, 1616, 1617, 1618, 1619, 1620, 1621);

		// 特別休暇コード = 17 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(17)));
		assertThat(lstId).extracting(d -> d).containsExactly(1176,1177,1181,1185,1187,1188,1189,1193,1182,1194, 1622, 1623, 1624, 1625, 1626, 1627, 1628, 1629, 1630, 1631, 1632);

		// 特別休暇コード = 18 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(18)));
		assertThat(lstId).extracting(d -> d).containsExactly(1197,1198,1202,1206,1208,1209,1210,1214,1203,1215, 1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1642, 1643);

		// 特別休暇コード = 19 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(19)));
		assertThat(lstId).extracting(d -> d).containsExactly(1218,1219,1223,1227,1229,1230,1231,1235,1224,1236, 1644, 1645, 1646, 1647, 1648, 1649, 1650, 1651, 1652, 1653, 1654);

		// 特別休暇コード = 20 :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(20)));
		assertThat(lstId).extracting(d -> d).containsExactly(1239,1240,1244,1248,1250,1251,1252,1256,1245,1257, 1655, 1656, 1657, 1658, 1659, 1660, 1661, 1662, 1663, 1664, 1665);
		
		// コードリスト = [1,2] :
		lstId = SpecialHoliday.getMonthlyAttdItemsCorrespondSpecialHoliday(Arrays.asList(new SpecialHolidayCode(1), new SpecialHolidayCode(2)));
		List<Integer> lstCanGet = Arrays.asList(840, 841, 845, 849, 851, 852, 853, 857, 846, 858, 1446,
				1447, 1448, 1449, 1450, 1451, 1452, 1453, 1454, 1455, 1456, 861, 862, 866, 870, 872, 873, 874, 878,
				867, 879, 1457, 1458, 1459, 1460, 1461, 1462, 1463, 1464, 1465, 1466, 1467);
		assertThat(lstId.containsAll(lstCanGet)).isTrue();

	}
}
