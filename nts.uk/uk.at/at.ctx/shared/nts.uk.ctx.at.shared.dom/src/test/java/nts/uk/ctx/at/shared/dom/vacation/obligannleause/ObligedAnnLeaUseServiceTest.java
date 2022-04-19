package nts.uk.ctx.at.shared.dom.vacation.obligannleause;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingDataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import mockit.Mocked;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@RunWith(JUnit4.class)
public class ObligedAnnLeaUseServiceTest {

	@Inject
	ObligedAnnLeaUseService obligedAnnLeaUseService;

	static class Dummy{
		private static String EMP_ID = "EMP_ID";
		private static ReferenceAtr REF_ATR = ReferenceAtr.APP_AND_SCHE;
	}

	@Test
	public void 使用義務日数取得_按分しない_期間重複なし(){
		val obligDays = new AnnualLeaveUsedDayNumber(5.0);
		val distributeAtr = false;
		val referenceDate = GeneralDate.ymd(2001,4,1);
		val grantDates = Arrays.asList(
				GeneralDate.ymd(2000,10,1),
				GeneralDate.ymd(2001,10,1),
				GeneralDate.ymd(2002,10,1),
				GeneralDate.ymd(2003,10,1));
		val remainingData = createRemains(grantDates);
		val obligedAnnualLeaveUse = ObligedAnnualLeaveUse.create(Dummy.EMP_ID, distributeAtr, Dummy.REF_ATR, obligDays, remainingData);

//		new Expectations(){{
//			obligedAnnLeaUseService.checkNeedForProportion(referenceDate, obligedAnnualLeaveUse);
//			result = false;
//		}};

		val result = obligedAnnLeaUseService.getObligedUseDays(referenceDate, obligedAnnualLeaveUse);
		assertThat(result.get().getObligedUseDays()).isEqualTo(5.0);
	}

	@Test
	public void 使用義務日数取得_按分しない_期間重複あり(){
		val obligDays = new AnnualLeaveUsedDayNumber(5.0);
		val distributeAtr = false;
		val referenceDate = GeneralDate.ymd(2001,4,1);
		val grantDates = Arrays.asList(
				GeneralDate.ymd(2000,4,1),
				GeneralDate.ymd(2000,10,1),
				GeneralDate.ymd(2001,10,1),
				GeneralDate.ymd(2002,10,1),
				GeneralDate.ymd(2003,10,1));
		val remainingData = createRemains(grantDates);
		val obligedAnnualLeaveUse = ObligedAnnualLeaveUse.create(Dummy.EMP_ID, distributeAtr, Dummy.REF_ATR, obligDays, remainingData);

//		new Expectations(){{
//			obligedAnnLeaUseService.checkNeedForProportion(referenceDate, obligedAnnualLeaveUse);
//			result = false;
//		}};

		val result = obligedAnnLeaUseService.getObligedUseDays(referenceDate, obligedAnnualLeaveUse);
		assertThat(result.get().getObligedUseDays()).isEqualTo(5.0);
	}

	@Test
	public void 使用義務日数取得_按分する_期間重複なし(){
		val obligDays = new AnnualLeaveUsedDayNumber(5.0);
		val distributeAtr = true;
		val referenceDate = GeneralDate.ymd(2001,4,1);
		val grantDates = Arrays.asList(
				GeneralDate.ymd(2000,10,1),
				GeneralDate.ymd(2001,10,1),
				GeneralDate.ymd(2002,10,1),
				GeneralDate.ymd(2003,10,1));
		val remainingData = createRemains(grantDates);
		val obligedAnnualLeaveUse = ObligedAnnualLeaveUse.create(Dummy.EMP_ID, distributeAtr, Dummy.REF_ATR, obligDays, remainingData);

		new Expectations(){{
			obligedAnnLeaUseService.checkNeedForProportion(referenceDate, obligedAnnualLeaveUse);
			result = false;
		}};

		val result = obligedAnnLeaUseService.getObligedUseDays(referenceDate, obligedAnnualLeaveUse);
		assertThat(result.get().getObligedUseDays()).isEqualTo(5.0);
	}

	@Test
	public void 使用義務日数取得_按分する_期間重複あり(){
		val obligDays = new AnnualLeaveUsedDayNumber(5.0);
		val distributeAtr = true;
		val referenceDate = GeneralDate.ymd(2001,4,1);
		val grantDates = Arrays.asList(
				GeneralDate.ymd(2000,4,1),
				GeneralDate.ymd(2000,10,1),
				GeneralDate.ymd(2001,10,1),
				GeneralDate.ymd(2002,10,1),
				GeneralDate.ymd(2003,10,1));
		val remainingData = createRemains(grantDates);
		val obligedAnnualLeaveUse = ObligedAnnualLeaveUse.create(Dummy.EMP_ID, distributeAtr, Dummy.REF_ATR, obligDays, remainingData);

		new Expectations(){{
			obligedAnnLeaUseService.checkNeedForProportion(referenceDate, obligedAnnualLeaveUse);
			result = true;
		}};

		val result = obligedAnnLeaUseService.getObligedUseDays(referenceDate, obligedAnnualLeaveUse);
		assertThat(result.get().getObligedUseDays()).isEqualTo(5.0);
	}



	private static List<AnnualLeaveGrantRemainingData> createRemains(List<GeneralDate> grantDates){
		return grantDates.stream()
				.map(gd -> {
					return AnnualLeaveGrantRemainingData.of(
							LeaveGrantRemainingDataHelper.leaveGrantRemainingData(
								Dummy.EMP_ID, gd,
									2.0, 0,
									1.0, 0,
									1.0, 0));
				}).collect(Collectors.toList());
	}
}
