package nts.uk.ctx.at.shared.dom.specialholiday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.PerServiceLengthTableCD;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.GrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveAppSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveGrantSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialHolidayInfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.specialholiday.export.NextSpecialLeaveGrant;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.PeriodGrantDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.primitive.Memo;

/**
 * 特別休暇
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class SpecialHoliday extends AggregateRoot {

	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/** 特別休暇名称 */
	private SpecialHolidayName specialHolidayName;

	/** 付与・期限情報 */
	private GrantRegular grantRegular;

	/** 特別休暇利用条件 */
	private SpecialLeaveRestriction specialLeaveRestriction;

	/** 対象項目 */
	private TargetItem targetItem;

	/**自動付与区分 */
	public NotUseAtr autoGrant;

	/** 連続で取得する */
	public NotUseAtr continuousAcquisition;

	/** メモ */
	private Memo memo;

	@Override
	public void validate() {
		super.validate();
	}
	
	/**
	 * [C-0] 特別休暇
	 */
	public SpecialHoliday(String companyId, SpecialHolidayCode specialHolidayCode,
			SpecialHolidayName specialHolidayName, GrantRegular grantRegular,
			SpecialLeaveRestriction specialLeaveRestriction, TargetItem targetItem, NotUseAtr autoGrant,
			NotUseAtr continuousAcquisition, Memo memo) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.specialHolidayName = specialHolidayName;
		this.grantRegular = grantRegular;
		this.specialLeaveRestriction = specialLeaveRestriction;
		this.targetItem = targetItem;
		this.autoGrant = autoGrant;
		this.continuousAcquisition = continuousAcquisition;
		this.memo = memo;
	}
	
	/**
	 * [1] 利用できない月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItems(Require require){
		Optional<TimeSpecialLeaveManagementSetting> timeMana =  require.findByCompany(companyId);
		if (!timeMana.isPresent() || (timeMana.isPresent() && timeMana.get().getManageType() == ManageDistinct.NO)) { // nhờ dev update ver 3 sửa cho điều kiện này khi update tài liệu
			return SpecialHoliday.getAttendanceItemsTimeSpecialHoliday(Arrays.asList(this.specialHolidayCode));
		}
		return new ArrayList<>();
	}
	
	/**
	 * [S-1] 時間特別休暇に対応する月次の勤怠項目を取得する
	 */
	public static List<Integer> getMonthlyAttdItemsCorrespondSpecialHoliday(List<SpecialHolidayCode> lstCode){
		List<Integer> lstId = new ArrayList<>();
		
		lstId.addAll(SpecialHoliday.getAttendanceItemsDaySpecialHoliday(lstCode));
		
		lstId.addAll(SpecialHoliday.getAttendanceItemsTimeSpecialHoliday(lstCode));
		
		return lstId;
	}
	
	/**
	 * [prv-1] 時間特別休暇に対応する日数系の月次の勤怠項目を取得する
	 */
	private static List<Integer> getAttendanceItemsDaySpecialHoliday(List<SpecialHolidayCode> lstCode){
		
		List<Integer> lstId = new ArrayList<>();
		for(SpecialHolidayCode code : lstCode) {
			switch(code.v()) {
				case 1 :
					lstId.addAll(Arrays.asList(840,841,845,849,851,852,853,857,846,858));
					break;
				case 2 :
					lstId.addAll(Arrays.asList(861,862,866,870,872,873,874,878,867,879));
					break;
				case 3 :
					lstId.addAll(Arrays.asList(882,883,887,891,893,894,895,899,888,900));
					break;
				case 4 :
					lstId.addAll(Arrays.asList(903,904,908,912,914,915,916,920,909,921));	
					break;
				case 5 :
					lstId.addAll(Arrays.asList(924,925,929,933,935,936,937,941,930,942));
					break;
				case 6 :
					lstId.addAll(Arrays.asList(945,946,950,954,956,957,958,962,951,963));
					break;
				case 7 :
					lstId.addAll(Arrays.asList(966,967,971,975,977,978,979,983,972,984));
					break;
				case 8 :
					lstId.addAll(Arrays.asList(987,988,992,996,998,999,1000,1004,993,1005));	
					break;
				case 9 :
					lstId.addAll(Arrays.asList(1008,1009,1013,1017,1019,1020,1021,1025,1014,1026));
					break;
				case 10 :
					lstId.addAll(Arrays.asList(1029,1030,1034,1038,1040,1041,1042,1046,1035,1047));
					break;
				case 11 :
					lstId.addAll(Arrays.asList(1050,1051,1055,1059,1061,1062,1063,1067,1056,1068));
					break;
				case 12 :
					lstId.addAll(Arrays.asList(1071,1072,1076,1080,1082,1083,1084,1088,1077,1089));
					break;
				case 13 :
					lstId.addAll(Arrays.asList(1092,1093,1097,1101,1103,1104,1105,1109,1098,1110));
					break;
				case 14 :
					lstId.addAll(Arrays.asList(1113,1114,1118,1122,1124,1125,1126,1130,1119,1131));
					break;
				case 15 :
					lstId.addAll(Arrays.asList(1134,1135,1139,1143,1145,1146,1147,1151,1140,1152));
					break;
				case 16 :
					lstId.addAll(Arrays.asList(1155,1156,1160,1164,1166,1167,1168,1172,1161,1173));
					break;
				case 17 :
					lstId.addAll(Arrays.asList(1176,1177,1181,1185,1187,1188,1189,1193,1182,1194));
					break;
				case 18 :
					lstId.addAll(Arrays.asList(1197,1198,1202,1206,1208,1209,1210,1214,1203,1215));
					break;
				case 19 :
					lstId.addAll(Arrays.asList(1218,1219,1223,1227,1229,1230,1231,1235,1224,1236));
					break;
				default :
					lstId.addAll(Arrays.asList(1239,1240,1244,1248,1250,1251,1252,1256,1245,1257));
					break;
			}
		}
		return lstId;
	}
	
	/**
	 * [prv-2] 時間特別休暇に対応する時間系の月次の勤怠項目を取得する
	 */
	private static List<Integer> getAttendanceItemsTimeSpecialHoliday(List<SpecialHolidayCode> lstCode){
		List<Integer> lstId = new ArrayList<>();
		for(SpecialHolidayCode code : lstCode) {
			switch(code.v()) {
			case 1 :
				lstId.addAll(Arrays.asList(1446,1447,1448,1449,1450,1451,1452,1453,1454,1455,1456));
				break;
			case 2 :
				lstId.addAll(Arrays.asList(1457,1458,1459,1460,1461,1462,1463,1464,1465,1466,1467));
				break;
			case 3 :
				lstId.addAll(Arrays.asList(1468,1469,1470,1471,1472,1473,1474,1475,1476,1477,1478));
				break;
			case 4 :
				lstId.addAll(Arrays.asList(1479,1480,1481,1482,1483,1484,1485,1486,1487,1488,1489));
				break;
			case 5 :
				lstId.addAll(Arrays.asList(1490,1491,1492,1493,1494,1495,1496,1497,1498,1499,1500));
				break;
			case 6 :
				lstId.addAll(Arrays.asList(1501,1502,1503,1504,1505,1506,1507,1508,1509,1510,1511));
				break;
			case 7 :
				lstId.addAll(Arrays.asList(1512,1513,1514,1515,1516,1517,1518,1519,1520,1521,1522));
				break;
			case 8 :
				lstId.addAll(Arrays.asList(1523,1524,1525,1526,1527,1528,1529,1530,1531,1532,1533));
				break;
			case 9 :
				lstId.addAll(Arrays.asList(1534,1535,1536,1537,1538,1539,1540,1541,1542,1543,1544));	
				break;
			case 10 :
				lstId.addAll(Arrays.asList(1545,1546,1547,1548,1549,1550,1551,1552,1553,1554,1555));	
				break;
			case 11 :
				lstId.addAll(Arrays.asList(1556,1557,1558,1559,1560,1561,1562,1563,1564,1565,1566));	
				break;
			case 12 :
				lstId.addAll(Arrays.asList(1567,1568,1569,1570,1571,1572,1573,1574,1575,1576,1577));	
				break;
			case 13 :
				lstId.addAll(Arrays.asList(1578,1579,1580,1581,1582,1583,1584,1585,1586,1587,1588));	
				break;
			case 14 :
				lstId.addAll(Arrays.asList(1589,1590,1591,1592,1593,1594,1595,1596,1597,1598,1599));	
				break;
			case 15 :
				lstId.addAll(Arrays.asList(1600,1601,1602,1603,1604,1605,1606,1607,1608,1609,1610));	
				break;
			case 16 :
				lstId.addAll(Arrays.asList(1611,1612,1613,1614,1615,1616,1617,1618,1619,1620,1621));		
				break;
			case 17 :
				lstId.addAll(Arrays.asList(1622,1623,1624,1625,1626,1627,1628,1629,1630,1631,1632));	
				break;
			case 18 :
				lstId.addAll(Arrays.asList(1633,1634,1635,1636,1637,1638,1639,1640,1641,1642,1643));	
				break;
			case 19 :
				lstId.addAll(Arrays.asList(1644,1645,1646,1647,1648,1649,1650,1651,1652,1653,1654));	
				break;
			default :
				lstId.addAll(Arrays.asList(1655,1656,1657,1658,1659,1660,1661,1662,1663,1664,1665));
				break;
			}
		}
		return lstId;
	}
	

	public SpecialHoliday(String companyId, SpecialHolidayCode specialHolidayCode,
			SpecialHolidayName specialHolidayName, NotUseAtr autoGrant, Memo memo) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.specialHolidayName = specialHolidayName;
		this.autoGrant = autoGrant;
		this.memo = memo;
		this.grantRegular = new GrantRegular();
		this.specialLeaveRestriction = new SpecialLeaveRestriction();
		this.targetItem = new TargetItem();
		this.continuousAcquisition = NotUseAtr.NOT_USE;
	}

	public SpecialHoliday(String companyId, SpecialHolidayCode specialHolidayCode,
			SpecialHolidayName specialHolidayName, GrantRegular grantRegular,
			SpecialLeaveRestriction specialLeaveRestriction, Memo memo) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.specialHolidayName = specialHolidayName;
		this.grantRegular = grantRegular;
		this.specialLeaveRestriction = specialLeaveRestriction;
		this.targetItem = new TargetItem();
		this.memo = memo;
		this.continuousAcquisition = NotUseAtr.NOT_USE;
		this.autoGrant = NotUseAtr.NOT_USE;
	}

	public static SpecialHoliday createFromJavaType(
			String companyId, int specialHolidayCode, String specialHolidayName,
			int autoGrant, String memo) {
		return new SpecialHoliday(companyId,
				new SpecialHolidayCode(specialHolidayCode),
				new SpecialHolidayName(specialHolidayName),
				EnumAdaptor.valueOf(autoGrant, NotUseAtr.class),
				new Memo(memo));
	}

	public static SpecialHoliday of(
			String companyId,
			int specialHolidayCode,
			String specialHolidayName,
			GrantRegular grantRegular,
			SpecialLeaveRestriction specialLeaveRestriction,
			TargetItem targetItem,
			int autoGrant,
			String memo,
			NotUseAtr continuousAcquisition) {
		return new SpecialHoliday(companyId,
				new SpecialHolidayCode(specialHolidayCode),
				new SpecialHolidayName(specialHolidayName),
				grantRegular,
				specialLeaveRestriction,
				targetItem,
				EnumAdaptor.valueOf(autoGrant, NotUseAtr.class),
				continuousAcquisition, new Memo(memo));
	}
	
	 /* 次回特別休暇付与を計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * int spLeaveCD 特別休暇コード
	 * @param period 期間
	 * @return 次回特休付与リスト
	 */
	public List<NextSpecialLeaveGrant> calcSpecialLeaveGrantInfo(
			Require require,
			CacheCarrier cacheCarrier,
			NextSpecialHolidayGrantParameter parameter) {

		// 自動付与区分を確認
		if (this.getAutoGrant().equals(NotUseAtr.NOT_USE)) {
			return new ArrayList<>();
		}
				
		return this.getGrantRegular().getNextSpecialLeaveGrant(require, cacheCarrier, parameter,
				this.getSpecialLeaveRestriction());
		
	}	
	
	
	/**
	 * 社員に依存しない特別休暇情報一覧を作成する
	 * @param cid
	 * @param period
	 * @param specialLeaveCode
	 * @param speGrantDate
	 * @param annGrantDate
	 * @param entryDate
	 * @param specialSetting
	 * @param grantDays
	 * @param grantTableCd
	 * @param require
	 * @param cacheCarrier
	 * @return
	 */
	public List<SpecialHolidayInfor> createNotDepentInfoGrantInfo(String cid,
			DatePeriod period,
			SpecialHolidayCode specialLeaveCode,
			GeneralDate speGrantDate,
			GeneralDate annGrantDate,
			GeneralDate entryDate,
			SpecialLeaveAppSetting specialSetting,
			Optional<GrantNumber> grantDays,
			Optional<PerServiceLengthTableCD> grantTableCd,
			Require require,
			CacheCarrier cacheCarrier){
		
		NextSpecialHolidayGrantParameterGrantDate grantDate = new NextSpecialHolidayGrantParameterGrantDate(entryDate,
				annGrantDate, speGrantDate);
		
		SpecialLeaveBasicInfo basicInfo = new SpecialLeaveBasicInfo(cid, "", specialLeaveCode, UseAtr.USE,
				specialSetting, new SpecialLeaveGrantSetting(speGrantDate, grantDays, grantTableCd));
		
		
		NextSpecialHolidayGrantParameter parameter = new NextSpecialHolidayGrantParameter(cid, Optional.empty(),
				specialLeaveCode, period, basicInfo, Optional.of(grantDate));
		
		List<NextSpecialLeaveGrant> nextSpecialLeaveGrant = calcSpecialLeaveGrantInfo(require, cacheCarrier, parameter);
		
		
		return nextSpecialLeaveGrant
				.stream().map(x -> new SpecialHolidayInfor(x)).collect(Collectors.toList());
	}

	
	public static interface Require extends SpecialLeaveRestriction.Require, SpecialLeaveBasicInfo.Require,
			GrantDate.Require, PeriodGrantDate.Require {

		/**
		 * [R-1] 時間特別休暇の管理設定を取得する
		 *
		 */
		Optional<TimeSpecialLeaveManagementSetting> findByCompany(String companyId);

		Optional<ElapseYear> elapseYear(String companyId, int specialHolidayCode);

		Optional<EmpEnrollPeriodImport> getLatestEnrollmentPeriod(String lstEmpId, DatePeriod datePeriod);
	}
}
