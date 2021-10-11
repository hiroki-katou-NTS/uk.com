package nts.uk.ctx.at.record.dom.remainingnumber.childcare;

import org.junit.Test;
import org.junit.runner.RunWith;
import static nts.arc.time.GeneralDate.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.CareManagementDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.CareTargetPeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.CareTargetPeriodWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareTargetChanged;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberOfCaregivers;

/**
 * 介護対象管理データ
 ****** 1.介護対象期間を確認
 *
 */
//@RunWith(JMockit.class)
	/**
	 * 介護対象期間を確認
	 */
//	@Test
//	public void testcareTargetPeriodWork() {
//		DatePeriod period = new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15));// 期間
//		Optional<GeneralDate> deadDay = Optional.empty(); //死亡年月日
//		ChildCareTargetChanged childCareTargetChanged//看護介護対象人数変更日（List）
//
//
//		careTargetPeriodWork
//	}
//	//看護介護対象人数変更日（List）
//	private List<ChildCareTargetChanged> childCareTargetChanged() {
//		return Arrays.asList(
//				ChildCareTargetChanged.of(new NumberOfCaregivers(1), ymd(2020, 10, 16)), // 人数、変更日
//				ChildCareTargetChanged.of(new NumberOfCaregivers(2), ymd(2020, 11, 1)));
//	}
//	// 介護対象管理データ
//	private CareManagementDate careManagementDate(boolean careManagement) {
//		return CareManagementDate.of(
//				"10f82569-6cfe-4992-9d5c-d9f3dd29b225", // 家族ID
//				careManagement, //介護対象(true / false)
//				DateHistoryItem)
//	}
//	// CareTargetPeriodWork
//	private CareTargetPeriodWork careTargetPeriodWork() {
//		return CareTargetPeriodWork.of(
//				childCareTargetChanged(),
//				CareTargetPeriod.of(new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15))));
//	}
