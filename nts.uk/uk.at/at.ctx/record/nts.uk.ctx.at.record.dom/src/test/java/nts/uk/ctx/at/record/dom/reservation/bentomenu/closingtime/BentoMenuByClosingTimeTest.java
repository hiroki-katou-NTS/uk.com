package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.Helper.Setting.ReserRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.AchievementMethod;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.Achievements;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ContentChangeDeadline;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ContentChangeDeadlineDay;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationOrderMngAtr;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;

public class BentoMenuByClosingTimeTest {

	@Test
	public void createForCurrent_frame1() {
		
		String roleID = "roleID";
		ReservationSetting reservationSetting = new ReservationSetting(
				"companyID", 
				OperationDistinction.BY_COMPANY, 
				new CorrectionContent(
						ContentChangeDeadline.ALLWAY_FIXABLE, 
						ContentChangeDeadlineDay.ONE, 
						ReservationOrderMngAtr.CAN_MANAGE, 
						Arrays.asList("roleID")), 
				new Achievements(AchievementMethod.ALL_DATA), 
				Arrays.asList(ReserRecTimeZone.ReserFrame1), 
				true);
		List<Bento> bentoLst = Collections.emptyList();
		Map<ReservationClosingTimeFrame, Boolean> orderAtr = new HashMap<>();
		orderAtr.put(ReservationClosingTimeFrame.FRAME1, true);
		GeneralDate orderDate = GeneralDate.today();
		
		BentoMenuByClosingTime target = BentoMenuByClosingTime.createForCurrent(roleID, reservationSetting, bentoLst, orderAtr, orderDate);
		
		assertThat(target.isReservationTime1Atr()).isTrue();
		assertThat(target.isReservationTime2Atr()).isFalse();
	}

	@Test
	public void createForCurrent_frame2() {
		
		String roleID = "roleID";
		ReservationSetting reservationSetting = new ReservationSetting(
				"companyID", 
				OperationDistinction.BY_COMPANY, 
				new CorrectionContent(
						ContentChangeDeadline.ALLWAY_FIXABLE, 
						ContentChangeDeadlineDay.ONE, 
						ReservationOrderMngAtr.CAN_MANAGE, 
						Arrays.asList("roleID")), 
				new Achievements(AchievementMethod.ALL_DATA), 
				Arrays.asList(ReserRecTimeZone.ReserFrame2), 
				true);
		List<Bento> bentoLst = Collections.emptyList();
		Map<ReservationClosingTimeFrame, Boolean> orderAtr = new HashMap<>();
		orderAtr.put(ReservationClosingTimeFrame.FRAME2, true);
		GeneralDate orderDate = GeneralDate.today();
		
		BentoMenuByClosingTime target = BentoMenuByClosingTime.createForCurrent(roleID, reservationSetting, bentoLst, orderAtr, orderDate);
		
		assertThat(target.isReservationTime1Atr()).isFalse();
		assertThat(target.isReservationTime2Atr()).isTrue();
	}

	@Test
	public void createForCurrent_both() {
		
		String roleID = "roleID";
		ReservationSetting reservationSetting = new ReservationSetting(
				"companyID", 
				OperationDistinction.BY_COMPANY, 
				new CorrectionContent(
						ContentChangeDeadline.ALLWAY_FIXABLE, 
						ContentChangeDeadlineDay.ONE, 
						ReservationOrderMngAtr.CAN_MANAGE, 
						Arrays.asList("roleID")), 
				new Achievements(AchievementMethod.ALL_DATA), 
				Arrays.asList(ReserRecTimeZone.ReserFrame1, ReserRecTimeZone.ReserFrame2), 
				true);
		List<Bento> bentoLst = Collections.emptyList();
		Map<ReservationClosingTimeFrame, Boolean> orderAtr = new HashMap<>();
		orderAtr.put(ReservationClosingTimeFrame.FRAME1, true);
		orderAtr.put(ReservationClosingTimeFrame.FRAME2, true);
		GeneralDate orderDate = GeneralDate.today();
		
		BentoMenuByClosingTime target = BentoMenuByClosingTime.createForCurrent(roleID, reservationSetting, bentoLst, orderAtr, orderDate);
		
		assertThat(target.isReservationTime1Atr()).isTrue();
		assertThat(target.isReservationTime2Atr()).isTrue();
	}

	@Test
	public void createForCurrent_neither() {
		
		String roleID = "roleID";
		ReservationSetting reservationSetting = new ReservationSetting(
				"companyID", 
				OperationDistinction.BY_COMPANY, 
				new CorrectionContent(
						ContentChangeDeadline.ALLWAY_FIXABLE, 
						ContentChangeDeadlineDay.ONE, 
						ReservationOrderMngAtr.CAN_MANAGE, 
						Arrays.asList("roleID")), 
				new Achievements(AchievementMethod.ALL_DATA), 
				Collections.emptyList(), 
				true);
		List<Bento> bentoLst = Collections.emptyList();
		Map<ReservationClosingTimeFrame, Boolean> orderAtr = new HashMap<>();
		GeneralDate orderDate = GeneralDate.today();
		
		BentoMenuByClosingTime target = BentoMenuByClosingTime.createForCurrent(roleID, reservationSetting, bentoLst, orderAtr, orderDate);
		
		assertThat(target.isReservationTime1Atr()).isFalse();
		assertThat(target.isReservationTime2Atr()).isFalse();
	}

	@Test
	public void getters() {
		BentoMenuByClosingTime target = new BentoMenuByClosingTime(
				Collections.emptyList(), 
				Collections.emptyList(), 
				Arrays.asList(Helper.Setting.ReserRecTimeZone.ReserFrame1, Helper.Setting.ReserRecTimeZone.ReserFrame2), 
				false, 
				false);
		NtsAssert.invokeGetters(target);
	}

}
