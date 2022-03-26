package nts.uk.ctx.at.record.dom.attendanceitem.dailyattditem;

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
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.AnnualPaidLeaveSettingHelper;
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.ComSubstVacationHelper;
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.CompensatoryLeaveComSettingHelper;
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.DivergenceTimeRootHelper;
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.OptionalItemHelper;
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.OvertimeWorkFrameHelper;
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.TemporaryWorkUseManageHelper;
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.TimeSpecialLeaveManagementHelper;
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.WorkManagementMultipleHelper;
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.WorkdayoffFrameHelper;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodHelper;
import nts.uk.ctx.at.record.dom.remainingnumber.childcare.NursingLeaveSettingHelper;
import nts.uk.ctx.at.record.dom.workrecord.goout.MaxGoOut;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManage;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.TimeItemTypeAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.TimeItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeUseSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.ExtraTimeItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.UseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationMethod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.MaximumNumberOfSupport;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.UseATR;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class NarrowDownListDailyAttdItemTest {
	
	@Injectable
	private NarrowDownListDailyAttdItem.Require require;

	/**
	 * test [prv-1] 利用できない応援作業項目を取得する
	 * require.応援の運用設定を取得する(会社ID) is not empty
	 */
@Test
public void testGetSupportWorkItemNotAvailable() {
		String companyId = "companyId";
		SupportOperationSetting domain = new SupportOperationSetting(true, true, new MaximumNumberOfSupport(15));
		new Expectations() {{
			require.getSupportOperationSetting( anyString );
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getSupportWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(1071, 1080,1081, 1090,1091, 1100,1101, 1110,1111, 1120))).isTrue();
}
	
	/**
	 * test [prv-1] 利用できない応援作業項目を取得する
	 * require.応援の運用設定を取得する(会社ID) is empty
	 * require.作業運用設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetSupportWorkItemNotAvailable_2() {
		String companyId = "companyId";
		TaskOperationSetting domain = new TaskOperationSetting(TaskOperationMethod.DO_NOT_USE); 
		new Expectations() {{
			require.getSupportOperationSetting( anyString );
			
			require.getTasksOperationSetting(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getSupportWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(921, 2070, 931, 2290, 941, 2330, 961, 2350, 971, 2370, 981,
				2390, 991, 2410, 1001, 2430, 1011, 2450, 1021, 2470, 1031, 2490, 1041, 2510, 1051, 2530, 1061, 2550,
				1071, 2570, 1081, 2590, 1091, 2610, 1101, 2630, 1111, 2650))).isTrue();
	}
	
	/**
	 * test [prv-1] 利用できない応援作業項目を取得する
	 * require.応援の運用設定を取得する(会社ID) is empty
	 * require.作業運用設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetSupportWorkItemNotAvailable_3() {
		String companyId = "companyId";
		new Expectations() {{
			require.getSupportOperationSetting( anyString );
			
			require.getTasksOperationSetting(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getSupportWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-2] 利用できない残業項目を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getOvertimeItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(216,217,221,222))).isTrue();
	}
	
	/**
	 * test [prv-3] 利用できない休出項目を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getHolidayItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(266,267,271,272))).isTrue();
	}
	
	/**
	 * test [prv-4] 利用できない乖離項目を取得する
	 */
	@Test
	public void testGetUnusableDivergenceItemNotAvailable() {
		String companyId = "companyId";
		DivergenceTimeRoot divergenceTimeRoot1 = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(1, DivergenceTimeUseSet.NOT_USE);
		DivergenceTimeRoot divergenceTimeRoot2 = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(2, DivergenceTimeUseSet.NOT_USE);
		DivergenceTimeRoot divergenceTimeRoot3 = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(3, DivergenceTimeUseSet.USE);
		DivergenceTimeRoot divergenceTimeRoot4 = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(4, DivergenceTimeUseSet.USE);
		List<DivergenceTimeRoot> listDivergenceTimeRoot = new ArrayList<>();
		listDivergenceTimeRoot.add(divergenceTimeRoot1);
		listDivergenceTimeRoot.add(divergenceTimeRoot2);
		listDivergenceTimeRoot.add(divergenceTimeRoot3);
		listDivergenceTimeRoot.add(divergenceTimeRoot4);
		
		DivergenceReasonInputMethod divergenceReasonInputMethod1 = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(1, false, false);
		DivergenceReasonInputMethod divergenceReasonInputMethod2 = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(2, true, true);
		DivergenceReasonInputMethod divergenceReasonInputMethod3 = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(3, false, false);
		DivergenceReasonInputMethod divergenceReasonInputMethod4 = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(4, true, true);
		DivergenceReasonInputMethod divergenceReasonInputMethod5 = DivergenceReasonInputMethodHelper.createByNoAndUseAtr(5, false, false);
		List<DivergenceReasonInputMethod> listDivergenceReasonInputMethod = new ArrayList<>();
		listDivergenceReasonInputMethod.add(divergenceReasonInputMethod1);
		listDivergenceReasonInputMethod.add(divergenceReasonInputMethod2);
		listDivergenceReasonInputMethod.add(divergenceReasonInputMethod3);
		listDivergenceReasonInputMethod.add(divergenceReasonInputMethod4);
		listDivergenceReasonInputMethod.add(divergenceReasonInputMethod5);
		new Expectations() {{
			require.getAllDivTime(companyId);
			result = listDivergenceTimeRoot;
			
			require.getDivergenceReasonInputMethod(companyId);
			result = listDivergenceReasonInputMethod;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getUnusableDivergenceItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(436,437,441,442,
				438,439,//no 1
				443,444,//no 2
				448,449))).isTrue();// no3
		//no 4,no5 not contain
		assertThat(result.containsAll(Arrays.asList(453,454,458,459))).isFalse();
	}
	
	/**
	 * test [prv-5] 利用できない加給項目を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getPaidItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(316,336,317,337,366,386,367,387))).isTrue();
	}
	
	/**
	 * test [prv-7] 利用できない任意項目を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getAnyItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(641,642))).isTrue();
	}
	
	/**
	 * test [prv-8] 利用できない割増項目を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getExtraItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(426,1295,427,1296))).isTrue();
	}
	
	/**
	 * test [prv-9] 利用できない外出項目を取得する
	 * require.外出管理を取得する(会社ID) is empty
	 */
	@Test
	public void testGetOutOfServiceItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findOutManageByID(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getOutOfServiceItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-9] 利用できない外出項目を取得する
	 * require.外出管理を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetOutOfServiceItemNotAvailable_2() {
		String companyId = "companyId";
		OutManage domain = new OutManage("companyID", new MaxGoOut(6), GoingOutReason.PRIVATE); 
		new Expectations() {{
			require.findOutManageByID(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getOutOfServiceItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(128,129,135,136,142,143,149,150))).isTrue();
	}
	
	/**
	 * test [prv-10] 利用できない変形労働を取得する
	 * require.変形労働の集計設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetUnusableVariantLabor_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findAggDeformedLaborSettingByCid(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getUnusableVariantLabor"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-10] 利用できない変形労働を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getUnusableVariantLabor"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(551,1122))).isTrue();
	}
	
	/**
	 * test [prv-11] 利用できないフレックス勤務項目を取得する
	 * require.フレックス勤務の設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetFlexWorkItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findFlexWorkSet(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getFlexWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}

	/**
	 * test [prv-11] 利用できないフレックス勤務項目を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getFlexWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(466,470))).isTrue();
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getUnusableAnnualLeaveItems"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(539, 540))).isTrue();
	}
	
	/**
	 * test [prv-13] 利用できない代休項目を取得する
	 * require.代休管理設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetUnavailableSubstituteHolidayItem_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findCompensatoryLeaveComSetting(companyId);
			result = null;
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getUnavailableSubstituteHolidayItem"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-13] 利用できない代休項目を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getUnavailableSubstituteHolidayItem"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(541, 542,505, 517))).isTrue();
	}
	
	/**
	 * test [prv-14] 利用できない振休項目を取得する
	 * require.振休管理設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetUnavailableHolidayItem_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findComSubstVacationById(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getUnavailableHolidayItem"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-14] 利用できない振休項目を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getUnavailableHolidayItem"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(1144))).isTrue();
	}
	
	/**
	 * test [prv-15] 利用できない時間特別休暇項目を取得する
	 * require.時間特別休暇の管理設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetSpecialVacationItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findTimeSpecialLeaveManagementSetting(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getSpecialVacationItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-15] 利用できない時間特別休暇項目を取得する
	 * require.時間特別休暇の管理設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetSpecialVacationItemNotAvailable_2() {
		String companyId = "companyId";
		TimeSpecialLeaveManagementSetting domain = TimeSpecialLeaveManagementHelper
				.createManagementSettingManageDistinctIsNo(ManageDistinct.NO);
		new Expectations() {{
			require.findTimeSpecialLeaveManagementSetting(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getSpecialVacationItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(504,516))).isTrue();
	}
	
	/**
	 * test [prv-16] 利用できない介護看護項目を取得する
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
		List<Integer> result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getNursingItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(1126, 1130,1125, 1129))).isTrue();
	}
	
	/**
	 * test [prv-17] 利用できない複数回勤務項目を取得する
	 * require.複数回勤務管理を取得する(会社ID) is empty
	 */
	@Test
	public void testGetMultipleWorkItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findWorkManagementMultiple(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getMultipleWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-17] 利用できない複数回勤務項目を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getMultipleWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(5, 6, 40))).isTrue();
	}
	
	/**
	 * test [prv-18] 利用できない臨時勤務項目を取得する
	 * require.臨時勤務利用管理(会社ID) is empty
	 */
	@Test
	public void testGetTemporaryWorkItemNotAvailable_1() {
		String companyId = "companyId";
		new Expectations() {{
			require.findTemporaryWorkUseManage(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getTemporaryWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}
	
	/**
	 * test [prv-18] 利用できない臨時勤務項目を取得する
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
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getTemporaryWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(50, 51, 52))).isTrue();
	}
	
	/**
	 * test [1] 絞り込む
	 * All require is empty
	 */
	@Test
	public void testGet_1() {
		String companyId = "companyId";
		List<Integer> listAttdIdInput = Arrays.asList(1125, 1126,5, 6, 40,50, 51, 52);
		new Expectations() {{
			//1
			require.getSupportOperationSetting( anyString );
			//2
			require.getAllOvertimeWorkFrame(companyId);
			result = new ArrayList<>();
			//3
			require.getAllWorkdayoffFrame(companyId);
			result = new ArrayList<>();
			//4
			require.getAllDivTime(companyId);
			result = new ArrayList<>();
			//5,6
			require.getListBonusPayTimeItem(companyId);
			result = new ArrayList<>();
			//7
			require.findAllOptionalItem(companyId);
			result = new ArrayList<>();
			//8
			require.findPremiumItemByCompanyID(companyId);
			result = new ArrayList<>();
			//9
			require.findOutManageByID(companyId);
			//10
			require.findAggDeformedLaborSettingByCid(companyId);
			//11
			require.findFlexWorkSet(companyId);
			//12
			require.findByCompanyId(companyId);
			result = null;
			//13
			require.findCompensatoryLeaveComSetting(companyId);
			result = null;
			//14
			require.findComSubstVacationById(companyId);
			//15
			require.findTimeSpecialLeaveManagementSetting(companyId);
			//16
			require.findNursingLeaveSetting(companyId);
			result = new ArrayList<>();
			//17
			require.findWorkManagementMultiple(companyId);
			//18
			require.findTemporaryWorkUseManage(companyId);
		}};
		List<Integer>  result = NarrowDownListDailyAttdItem.get(require, companyId, listAttdIdInput);
		assertThat( result )
		.extracting( d -> d)
		.containsExactly(1125, 1126,5, 6, 40,50, 51, 52);
	}
	
	/**
	 * test [1] 絞り込む
	 *  require 複数回勤務管理を取得する is not empty
	 *  require 臨時勤務利用管理を取得する is not empty
	 */
	@Test
	public void testGet_2() {
		String companyId = "companyId";
		List<Integer> listAttdIdInput = Arrays.asList(1130,1125, 1126,5, 6, 40,50, 51, 52);
		WorkManagementMultiple workManagementMultiple = WorkManagementMultipleHelper.createWorkManagementMultiple_NotUse(UseATR.notUse);
		TemporaryWorkUseManage temporaryWorkUseManage = TemporaryWorkUseManageHelper.createTemporaryWorkUseManage_NotUse(nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr.NOTUSE);
		new Expectations() {{
			//1
			require.getSupportOperationSetting( anyString );
			//2
			require.getAllOvertimeWorkFrame(companyId);
			result = new ArrayList<>();
			//3
			require.getAllWorkdayoffFrame(companyId);
			result = new ArrayList<>();
			//4
			require.getAllDivTime(companyId);
			result = new ArrayList<>();
			//5,6
			require.getListBonusPayTimeItem(companyId);
			result = new ArrayList<>();
			//7
			require.findAllOptionalItem(companyId);
			result = new ArrayList<>();
			//8
			require.findPremiumItemByCompanyID(companyId);
			result = new ArrayList<>();
			//9
			require.findOutManageByID(companyId);
			//10
			require.findAggDeformedLaborSettingByCid(companyId);
			//11
			require.findFlexWorkSet(companyId);
			//12
			require.findByCompanyId(companyId);
			result = null;
			//13
			require.findCompensatoryLeaveComSetting(companyId);
			result = null;
			//14
			require.findComSubstVacationById(companyId);
			//15
			require.findTimeSpecialLeaveManagementSetting(companyId);
			//16
			require.findNursingLeaveSetting(companyId);
			result = new ArrayList<>();
			//17
			require.findWorkManagementMultiple(companyId);
			result = Optional.of(workManagementMultiple);
			//18
			require.findTemporaryWorkUseManage(companyId);
			result = Optional.of(temporaryWorkUseManage);
		}};
		List<Integer>  result = NarrowDownListDailyAttdItem.get(require, companyId, listAttdIdInput);
		assertThat( result )
		.extracting( d -> d)
		.containsExactly(1125,1126);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
