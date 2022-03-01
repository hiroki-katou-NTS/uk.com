package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.remainingnumber.childcare.NursingLeaveSettingHelper;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingHelper;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.TimeItemTypeAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.TimeItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRootHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeUseSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.ExtraTimeItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.UseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingHelper;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItemHelper;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeHelper;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemHelper;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesHelper;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayHelper;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingHelper;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSettingHelper;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingHelper;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationHelper;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameHelper;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.UseATR;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleHelper;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageHelper;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrameHelper;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameHelper;

@RunWith(JMockit.class)
public class NarrowDownListMonthlyAttdItemTest {
	
	@Injectable
	private NarrowDownListMonthlyAttdItem.Require require;

	/**
	 * test [prv-1] 利用できない残業項目を取得する
	 */
	@Test
	public void testGetOvertimeItemNotAvailable() {
		String companyId = "companyId";
		OvertimeWorkFrame overtimeWorkFrame1 = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(1, NotUseAtr.NOT_USE);
		OvertimeWorkFrame overtimeWorkFrame2 = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(2, NotUseAtr.NOT_USE);
		List<OvertimeWorkFrame> listOvertimeWorkFrame = new ArrayList<>();
		listOvertimeWorkFrame.add(overtimeWorkFrame1);
		listOvertimeWorkFrame.add(overtimeWorkFrame2);
		new Expectations() {{
			require.getAllOvertimeWorkFrame(companyId);
			result = listOvertimeWorkFrame;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getOvertimeItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(35,46,36,47))).isTrue();
	}
	
	/**
	 * test [prv-2] 利用できない休出項目を取得する
	 */
	@Test
	public void testGetHolidayItemNotAvailable() {
		String companyId = "companyId";
		WorkdayoffFrame workdayoffFrame1 = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(1, nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr.NOT_USE);
		WorkdayoffFrame workdayoffFrame2 = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(2, nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr.NOT_USE);
		List<WorkdayoffFrame> listWorkdayoffFrame = new ArrayList<>();
		listWorkdayoffFrame.add(workdayoffFrame1);
		listWorkdayoffFrame.add(workdayoffFrame2);
		new Expectations() {{
			require.getAllWorkdayoffFrame(companyId);
			result = listWorkdayoffFrame;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getHolidayItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(110,121,111,122))).isTrue();
	}
	
	/**
	 * test [prv-3] 利用できない乖離項目を取得する
	 */
	@Test
	public void testGetUnusableDivergenceItemNotAvailable() {
		String companyId = "companyId";
		DivergenceTimeRoot divergenceTimeRoot1 = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(1, DivergenceTimeUseSet.NOT_USE);
		DivergenceTimeRoot divergenceTimeRoot2 = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(2, DivergenceTimeUseSet.NOT_USE);
		List<DivergenceTimeRoot> listDivergenceTimeRoot = new ArrayList<>();
		listDivergenceTimeRoot.add(divergenceTimeRoot1);
		listDivergenceTimeRoot.add(divergenceTimeRoot2);
		
		new Expectations() {{
			require.getAllDivTime(companyId);
			result = listDivergenceTimeRoot;
			
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getUnusableDivergenceItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(389,390))).isTrue();
	}
	
	/**
	 * test [prv-4] 利用できない加給項目を取得する
	 */
	@Test
	public void testGetPaidItemNotAvailable() {
		String companyId = "companyId";
		//@加給項目区分 == 加給	
		BonusPayTimeItem bonusPayTimeItem1 = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.NORMAL_TYPE);
		BonusPayTimeItem bonusPayTimeItem2 = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 2, TimeItemTypeAtr.NORMAL_TYPE);
		//@加給項目区分 != 加給	
		BonusPayTimeItem bonusPayTimeItem3 = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 1, TimeItemTypeAtr.SPECIAL_TYPE);
		BonusPayTimeItem bonusPayTimeItem4 = new BonusPayTimeItem("companyId", UseAtr.NOT_USE, new TimeItemName("timeItemName"), 2, TimeItemTypeAtr.SPECIAL_TYPE);
		List<BonusPayTimeItem> listBonusPayTimeItem = new ArrayList<>();
		listBonusPayTimeItem.add(bonusPayTimeItem1);
		listBonusPayTimeItem.add(bonusPayTimeItem2);
		listBonusPayTimeItem.add(bonusPayTimeItem3);
		listBonusPayTimeItem.add(bonusPayTimeItem4);
		new Expectations() {{
			require.getListBonusPayTimeItem(companyId);
			result = listBonusPayTimeItem;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getPaidItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(334,344,335,345,354,364,355,365))).isTrue();
	}
	
	/**
	 * test [prv-6] 利用できない任意項目を取得する
	 */
	@Test
	public void testGetAnyItemNotAvailable() {
		String companyId = "companyId";
		OptionalItem optionalItem1 = OptionalItemHelper.createOptionalItemByNoAndUseAtr(1, OptionalItemUsageAtr.NOT_USE);
		OptionalItem optionalItem2 = OptionalItemHelper.createOptionalItemByNoAndUseAtr(2, OptionalItemUsageAtr.NOT_USE);
		List<OptionalItem> listOptionalItem = new ArrayList<>();
		listOptionalItem.add(optionalItem1);
		listOptionalItem.add(optionalItem2);
		new Expectations() {{
			require.findAllOptionalItem(companyId);
			result = listOptionalItem;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getAnyItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(589,590))).isTrue();
	}
	
	/**
	 * test [prv-7] 利用できない割増項目を取得する
	 */
	@Test
	public void testGetExtraItemNotAvailable() {
		String companyId = "companyId";
		PremiumItem premiumItem1 = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(1), new PremiumName("name"), UseAttribute.NotUse);
		PremiumItem premiumItem2 = new PremiumItem("companyID", ExtraTimeItemNo.valueOf(2), new PremiumName("name"), UseAttribute.NotUse);
		List<PremiumItem> listPremiumItem = new ArrayList<>();
		listPremiumItem.add(premiumItem1);
		listPremiumItem.add(premiumItem2);
		new Expectations() {{
			require.findPremiumItemByCompanyID(companyId);
			result = listPremiumItem;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getExtraItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(378,2083,379,2084))).isTrue();
	}
	
	/**
	 * test [prv-8] 利用できない変形労働を取得する
	 * require.変形労働の集計設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetUnusableVariantLabor_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findAggDeformedLaborSettingByCid(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getUnusableVariantLabor"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-8] 利用できない変形労働を取得する
	 * require.変形労働の集計設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetUnusableVariantLabor_2() {
		AggDeformedLaborSetting domain = new AggDeformedLaborSetting(new CompanyId("companyId"), nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr.NOTUSE);
		String companyId = "companyId";
		new Expectations() {{
			require.findAggDeformedLaborSettingByCid(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getUnusableVariantLabor"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(588,1351,1352,1353,1354,1355))).isTrue();
	}
	
	/**
	 * test [prv-9] 利用できないフレックス勤務項目を取得する
	 * require.フレックス勤務の設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetFlexWorkItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findFlexWorkSet(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getFlexWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}

	/**
	 * test [prv-9] 利用できないフレックス勤務項目を取得する
	 * require.フレックス勤務の設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetFlexWorkItemNotAvailable_2() {
		String companyId = "companyId";
		FlexWorkSet domain = new FlexWorkSet(new CompanyId("companyId"), nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr.NOTUSE);
		new Expectations() {{
			require.findFlexWorkSet(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getFlexWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(12, 13, 14, 15, 16, 17))).isTrue();
	}
	
	/**
	 * test [prv-10] 利用できない回数集計項目を取得する
	 */
	@Test
	public void testGetTotalTimesItemNotAvailable() {
		String companyId = "companyId";
		TotalTimes totalTimes1 = TotalTimesHelper.createTotalTimesByNoAndAtr(1,nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr.NotUse);
		TotalTimes totalTimes2 = TotalTimesHelper.createTotalTimesByNoAndAtr(2,nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr.NotUse);
		List<TotalTimes> listTotalTimes = new ArrayList<>();
		listTotalTimes.add(totalTimes1);
		listTotalTimes.add(totalTimes2);
		new Expectations() {{
			require.getAllTotalTimes(companyId);
			result = listTotalTimes;
		}};
		List<Integer> result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getTotalTimesItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(476,506,477,507))).isTrue();
	}
	
	/**
	 * test [prv-11] 利用できない時間外超過項目を取得する
	 * require.時間外超過設定を取得する(会社ID) is empty
	 */
	@Test
	public void testgetOutsideOTSettingItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.reportById(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getOutsideOTSettingItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-11] 利用できない時間外超過項目を取得する
	 * require.時間外超過設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testgetOutsideOTSettingItemNotAvailable_2() {
		String companyId = "companyId";
		// 超過時間1 : 超過時間NO = 1 
		Overtime overtimeNo1 = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.ONE,
				UseClassification.UseClass_NotUse);
		// 超過時間2 : 超過時間NO = 2 
		Overtime overtimeNo2 = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.TWO,
				UseClassification.UseClass_NotUse);
		// 超過時間一覧
		List<Overtime> overtimes = new ArrayList<>();
		overtimes.add(overtimeNo1);
		overtimes.add(overtimeNo2);

		// 内訳項目1 : 内訳項目NO = 1 
		OutsideOTBRDItem outsideOTBRDItem1 = OutsideOTBRDItemHelper
				.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.ONE, UseClassification.UseClass_NotUse);
		// 内訳項目2 : 内訳項目NO = 2 
		OutsideOTBRDItem outsideOTBRDItem2 = OutsideOTBRDItemHelper
				.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.TWO, UseClassification.UseClass_NotUse);
		// 内訳項目一覧
		List<OutsideOTBRDItem> breakdownItems = new ArrayList<>();
		breakdownItems.add(outsideOTBRDItem1);
		breakdownItems.add(outsideOTBRDItem2);

		OutsideOTSetting domain = OutsideOTSettingHelper.createOutsideOTSettingDefault(overtimes,
				breakdownItems);
		new Expectations() {{
			require.reportById(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getOutsideOTSettingItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(536,537,538,539,540,541,542,543,544,545,536,546,556,566,576,2069))).isTrue();
	}
	
	
	/**
	 * test [prv-12] 利用できない年休項目を取得する
	 * require.年休設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetUnusableAnnualLeaveItems_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findByCompanyId(companyId);
			result = null;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getUnusableAnnualLeaveItems"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-12] 利用できない年休項目を取得する
	 * require.年休設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetUnusableAnnualLeaveItems_2() {
		String companyId = "companyId";
		AnnualPaidLeaveSetting domain = AnnualPaidLeaveSettingHelper
				.createAnnualPaidLeaveSetting(ManageDistinct.NO);
		new Expectations() {{
			require.findByCompanyId(companyId);
			result = domain;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getUnusableAnnualLeaveItems"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(189, 794,1434, 1435))).isTrue();
	}
	
	/**
	 * test [prv-13] 利用できない積立年休項目を取得する
	 * require.積立年休設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetRetentionYearlySettingItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findRetentionYearlySettingByCompanyId(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getRetentionYearlySettingItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-13] 利用できない積立年休項目を取得する
	 * require.積立年休設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetRetentionYearlySettingItemNotAvailable_2() {
		String companyId = "companyId";
		RetentionYearlySetting retentionYearlySetting = RetentionYearlySettingHelper.createRetentionYearlySetting();
		new Expectations() {{
			require.findRetentionYearlySettingByCompanyId(companyId);
			result = Optional.of(retentionYearlySetting);
			
			require.findByCid(anyString);
			result = null;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getRetentionYearlySettingItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(187, 830))).isTrue();
	}
	
	/**
	 * test [prv-14] 利用できない代休項目を取得する
	 * require.代休管理設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetUnavailableSubstituteHolidayItem_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findCompensatoryLeaveComSetting(companyId);
			result = null;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getUnavailableSubstituteHolidayItem"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-14] 利用できない代休項目を取得する
	 * require.代休管理設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetUnavailableSubstituteHolidayItem_2() {
		String companyId = "companyId";
		TimeVacationDigestUnit timeUnit = CompensatoryLeaveComSettingHelper
				.createCompensatoryDigestiveTimeUnit(ManageDistinct.NO);
		CompensatoryLeaveComSetting domain = CompensatoryLeaveComSettingHelper
				.createCompensatoryLeaveComSetting(ManageDistinct.NO, timeUnit);
		new Expectations() {{
			require.findCompensatoryLeaveComSetting(companyId);
			result = domain;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getUnavailableSubstituteHolidayItem"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(1260, 1262,188, 1666))).isTrue();
	}
	
	/**
	 * test [prv-15] 利用できない振休項目を取得する
	 * require.振休管理設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetUnavailableHolidayItem_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findComSubstVacationById(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getUnavailableHolidayItem"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-15] 利用できない振休項目を取得する
	 * require.振休管理設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetUnavailableHolidayItem_2() {
		String companyId = "companyId";
		ComSubstVacation domain = ComSubstVacationHelper.createComSubstVacation(ManageDistinct.NO);
		new Expectations() {{
			require.findComSubstVacationById(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getUnavailableHolidayItem"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(1270, 1271))).isTrue();
	}
	
	/**
	 * test [prv-16] 利用できない特別休暇項目を取得する
	 */
	@Test
	public void testGetSpecialHolidayItemNotAvailable() {
		String companyId = "companyId";
		SpecialHoliday specialHoliday1 = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(1));
		SpecialHoliday specialHoliday2 = SpecialHolidayHelper.createSpecialHolidayTest(new SpecialHolidayCode(2));
		List<SpecialHoliday> listSpecialHoliday = new ArrayList<>();
		listSpecialHoliday.add(specialHoliday1);
		listSpecialHoliday.add(specialHoliday2);
		new Expectations() {{
			require.findByCompany(anyString);
			
			require.findSpecialHolidayByCompanyId(companyId);
			result = listSpecialHoliday;
			
		}};
		List<Integer> result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getSpecialHolidayItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(1446, 1447,1457, 1458,
				882,883,903,904,924,925,945,946,966,967,987,988,1008,1009,
				1029,1030,1050,1051,1071,1072,1092,1093,1113,1114,1134,1135,1155,1156,
				1176,1177,1197,1198,1218,1219,1239,1240,1468,1469,1479,1480,1490,1491,
				1501,1502,1512,1513,1523,1524,1534,1535,1545,1546,1556,1557,1567,1568,
				1578,1579,1589,1590,1600,1601,1611,1612,1622,1623,1633,1634,1644,1645,1655,1656))).isTrue();
	}
	
	/**
	 * test [prv-17] 利用できない欠勤項目を取得する
	 */
	@Test
	public void testGetAbsenceFrameItemNotAvailable() {
		String companyId = "companyId";
		AbsenceFrame absenceFrame1 = AbsenceFrameHelper.createAbsenceFrame(1);
		AbsenceFrame absenceFrame2 = AbsenceFrameHelper.createAbsenceFrame(2);
		List<AbsenceFrame> listAbsenceFrame = new ArrayList<>();
		listAbsenceFrame.add(absenceFrame1);
		listAbsenceFrame.add(absenceFrame2);
		new Expectations() {{
			require.findAllAbsenceFrame(companyId);
			result = listAbsenceFrame;
		}};
		List<Integer> result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getAbsenceFrameItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(239,1288,240,1289))).isTrue();
	}
	
	/**
	 * test [prv-18] 利用できない特別休暇枠項目を取得する
	 */
	@Test
	public void testGetSpecialHolidayFrameItemNotAvailable() {
		String companyId = "companyId";
		SpecialHolidayFrame specialHolidayFrame1 = SpecialHolidayFrameHelper.createSpecialHolidayFrame(1);
		SpecialHolidayFrame specialHolidayFrame2 = SpecialHolidayFrameHelper.createSpecialHolidayFrame(2);
		List<SpecialHolidayFrame> listSpecialHolidayFrame = new ArrayList<>();
		listSpecialHolidayFrame.add(specialHolidayFrame1);
		listSpecialHolidayFrame.add(specialHolidayFrame2);
		new Expectations() {{
			require.findAllSpecialHolidayFrame(companyId);
			result = listSpecialHolidayFrame;
		}};
		List<Integer> result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getSpecialHolidayFrameItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(270,1319,271,1320))).isTrue();
	}
	
	/**
	 * test [prv-19] 利用できない介護看護項目を取得する
	 */
	@Test
	public void testGetNursingItemNotAvailable() {
		String companyId = "companyId";
		NursingLeaveSetting nursingLeaveSetting1 = NursingLeaveSettingHelper
				.createNursingLeaveSetting(NursingCategory.Nursing);
		NursingLeaveSetting nursingLeaveSetting2 = NursingLeaveSettingHelper
				.createNursingLeaveSetting(NursingCategory.ChildNursing);
		List<NursingLeaveSetting> listNursingLeaveSetting = new ArrayList<>();
		listNursingLeaveSetting.add(nursingLeaveSetting1);
		listNursingLeaveSetting.add(nursingLeaveSetting2);
		new Expectations() {{
			require.findNursingLeaveSetting(companyId);
			result = listNursingLeaveSetting;
		}};
		List<Integer> result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getNursingItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(1673, 1674, 2254, 2255, 1279, 1280, 2252, 2253, 1671, 1672, 2250, 2251, 1275, 1276, 2248, 2249))).isTrue();
	}
	
	/**
	 * test [prv-20] 利用できない公休項目を取得する
	 * require.公休設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetPublicHolidaySettingItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.getPublicHolidaySetting(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getPublicHolidaySettingItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-20] 利用できない公休項目を取得する
	 * require.公休設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetPublicHolidaySettingItemNotAvailable_2() {
		String companyId = "companyId";
		PublicHolidaySetting domain = PublicHolidaySettingHelper.createPublicHolidaySetting(0);
		new Expectations() {{
			require.getPublicHolidaySetting(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getPublicHolidaySettingItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(2256, 2257))).isTrue();
	}
	
	/**
	 * test [prv-21] 利用できない複数回勤務項目を取得する
	 * require.複数回勤務管理を取得する(会社ID) is empty
	 */
	@Test
	public void testGetMultipleWorkItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findWorkManagementMultiple(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getMultipleWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-21] 利用できない複数回勤務項目を取得する
	 * require.複数回勤務管理を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetMultipleWorkItemNotAvailable_2() {
		String companyId = "companyId";
		WorkManagementMultiple domain = WorkManagementMultipleHelper.createWorkManagementMultiple_NotUse(UseATR.notUse);
		new Expectations() {{
			require.findWorkManagementMultiple(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getMultipleWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(1396))).isTrue();
	}
	
	/**
	 * test [prv-22] 利用できない臨時勤務項目を取得する
	 * require.臨時勤務利用管理(会社ID) is empty
	 */
	@Test
	public void testGetTemporaryWorkItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findTemporaryWorkUseManage(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getTemporaryWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-22] 利用できない臨時勤務項目を取得する
	 * require.臨時勤務利用管理(会社ID) is not empty
	 */
	@Test
	public void testGetTemporaryWorkItemNotAvailable_2() {
		String companyId = "companyId";
		TemporaryWorkUseManage domain = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage_NotUse(nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr.NOTUSE);
		new Expectations() {{
			require.findTemporaryWorkUseManage(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListMonthlyAttdItem(),
							"getTemporaryWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(1397, 1849))).isTrue();
	}
	
	/**
	 * test [1] 絞り込む
	 * All require is empty
	 */
	@Test
	public void testGet_1() {
		String companyId = "companyId";
		List<Integer> listAttdIdInput = Arrays.asList(2256, 2257,1396,1397, 1849);
		new Expectations() {{
			//1
			require.getAllOvertimeWorkFrame(companyId);
			result = new ArrayList<>();
			//2
			require.getAllWorkdayoffFrame(companyId);
			result = new ArrayList<>();
			//3
			require.getAllDivTime(companyId);
			result = new ArrayList<>();
			//4,5
			require.getListBonusPayTimeItem(companyId);
			//6
			require.findAllOptionalItem(companyId);
			result = new ArrayList<>();
			//7
			require.findPremiumItemByCompanyID(companyId);
			result = new ArrayList<>();
			//8
			require.findAggDeformedLaborSettingByCid(companyId);
			//9
			require.findFlexWorkSet(companyId);
			//10
			require.getAllTotalTimes(companyId);
			result = new ArrayList<>();
			//11
			require.reportById(companyId);
			//12
			require.findByCompanyId(companyId);
			result = null;
			//13
			require.findRetentionYearlySettingByCompanyId(companyId);
			//14
			require.findCompensatoryLeaveComSetting(companyId);
			result = null;
			//15
			require.findComSubstVacationById(companyId);
			//16
			require.findSpecialHolidayByCompanyId(companyId);
			result = new ArrayList<>();
			//17
			require.findAllAbsenceFrame(companyId);
			result = new ArrayList<>();
			//18
			require.findAllSpecialHolidayFrame(companyId);
			result = new ArrayList<>();
			//19
			require.findNursingLeaveSetting(companyId);
			result = new ArrayList<>();
			//20
			require.getPublicHolidaySetting(companyId);
			//21
			require.findWorkManagementMultiple(companyId);
			//22
			require.findTemporaryWorkUseManage(companyId);
		}};
		List<Integer>  result = NarrowDownListMonthlyAttdItem.get(require, companyId, listAttdIdInput);
		assertThat( result )
		.extracting( d -> d)
		.containsExactly(2256, 2257,1396,1397, 1849);
	}
	
	/**
	 * test [1] 絞り込む
	 *  require 複数回勤務管理を取得する is not empty
	 *  require 臨時勤務利用管理を取得する is not empty
	 */
	@Test
	public void testGet_2() {
		String companyId = "companyId";
		List<Integer> listAttdIdInput = Arrays.asList(2256, 2257,1396,1397, 1849);
		WorkManagementMultiple workManagementMultiple = WorkManagementMultipleHelper.createWorkManagementMultiple_NotUse(UseATR.notUse);
		TemporaryWorkUseManage temporaryWorkUseManage = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage_NotUse(nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr.NOTUSE);
		new Expectations() {{
			//1
			require.getAllOvertimeWorkFrame(companyId);
			result = new ArrayList<>();
			//2
			require.getAllWorkdayoffFrame(companyId);
			result = new ArrayList<>();
			//3
			require.getAllDivTime(companyId);
			result = new ArrayList<>();
			//4,5
			require.getListBonusPayTimeItem(companyId);
			//6
			require.findAllOptionalItem(companyId);
			result = new ArrayList<>();
			//7
			require.findPremiumItemByCompanyID(companyId);
			result = new ArrayList<>();
			//8
			require.findAggDeformedLaborSettingByCid(companyId);
			//9
			require.findFlexWorkSet(companyId);
			//10
			require.getAllTotalTimes(companyId);
			result = new ArrayList<>();
			//11
			require.reportById(companyId);
			//12
			require.findByCompanyId(companyId);
			result = null;
			//13
			require.findRetentionYearlySettingByCompanyId(companyId);
			//14
			require.findCompensatoryLeaveComSetting(companyId);
			result = null;
			//15
			require.findComSubstVacationById(companyId);
			//16
			require.findSpecialHolidayByCompanyId(companyId);
			result = new ArrayList<>();
			//17
			require.findAllAbsenceFrame(companyId);
			result = new ArrayList<>();
			//18
			require.findAllSpecialHolidayFrame(companyId);
			result = new ArrayList<>();
			//19
			require.findNursingLeaveSetting(companyId);
			result = new ArrayList<>();
			//20
			require.getPublicHolidaySetting(companyId);
			//21
			require.findWorkManagementMultiple(companyId);
			result = Optional.of(workManagementMultiple);
			//22
			require.findTemporaryWorkUseManage(companyId);
			result = Optional.of(temporaryWorkUseManage);
		}};
		List<Integer>  result = NarrowDownListMonthlyAttdItem.get(require, companyId, listAttdIdInput);
		assertThat( result )
		.extracting( d -> d)
		.containsExactly(2256, 2257);
	}

}
