package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.dom.reservation.Helper;

public class CorrectionContentTest {
	
	@Test
	public void getters() {
		CorrectionContent target = Helper.Setting.DUMMY.getCorrectionContent();
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void changeReservationDetail_always() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByChangeDeadline(ContentChangeDeadline.ALLWAY_FIXABLE);
		GeneralDate reservationDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today(); 
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1; 
		assertThat(correctionContent.canChangeReservationDetail(reservationDate, reservationTime, frameNo, orderDate, reservationRecTimeZone)).isEqualTo(true);
	}
	
	@Test
	public void changeReservationDetail_time_fail() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByChangeDeadline(ContentChangeDeadline.MODIFIED_DURING_RECEPTION_HOUR);
		GeneralDate reservationDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(400);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today(); 
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1; 
		assertThat(correctionContent.canChangeReservationDetail(reservationDate, reservationTime, frameNo, orderDate, reservationRecTimeZone)).isEqualTo(false);
	}
	
	@Test
	public void changeReservationDetail_time_success() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByChangeDeadline(ContentChangeDeadline.MODIFIED_DURING_RECEPTION_HOUR);
		GeneralDate reservationDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today(); 
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1; 
		assertThat(correctionContent.canChangeReservationDetail(reservationDate, reservationTime, frameNo, orderDate, reservationRecTimeZone)).isEqualTo(true);
	}
	
	@Test
	public void changeReservationDetail_day_fail() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByChangeDeadline(ContentChangeDeadline.MODIFIED_FROM_ORDER_DATE);
		GeneralDate reservationDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today().addDays(-2); 
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1; 
		assertThat(correctionContent.canChangeReservationDetail(reservationDate, reservationTime, frameNo, orderDate, reservationRecTimeZone)).isEqualTo(false);
	}
	
	@Test
	public void changeReservationDetail_day_success() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByChangeDeadline(ContentChangeDeadline.MODIFIED_FROM_ORDER_DATE);
		GeneralDate reservationDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today(); 
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1; 
		assertThat(correctionContent.canChangeReservationDetail(reservationDate, reservationTime, frameNo, orderDate, reservationRecTimeZone)).isEqualTo(true);
	}
	
	@Test
	public void employeeChangeReservation_orderAtr_fail() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByModifiLst(Arrays.asList("roleID"));
		String roleID = "roleID_1";
		GeneralDate reservationDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today().addDays(-1); 
		boolean orderAtr = true;
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		assertThat(correctionContent.canEmployeeChangeReservation(roleID, reservationDate, reservationTime, frameNo, orderDate, orderAtr, reservationRecTimeZone)).isEqualTo(false);
	}
	
	@Test
	public void employeeChangeReservation_orderAtr_success() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByModifiLst(Arrays.asList("roleID"));
		String roleID = "roleID";
		GeneralDate reservationDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today().addDays(-1); 
		boolean orderAtr = true;
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		assertThat(correctionContent.canEmployeeChangeReservation(roleID, reservationDate, reservationTime, frameNo, orderDate, orderAtr, reservationRecTimeZone)).isEqualTo(true);
	}
	
	@Test
	public void employeeChangeReservation_no_orderAtr_day_success() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByModifiLst(Arrays.asList("roleID"));
		String roleID = "roleID";
		GeneralDate reservationDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today().addDays(-1); 
		boolean orderAtr = false;
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		assertThat(correctionContent.canEmployeeChangeReservation(roleID, reservationDate, reservationTime, frameNo, orderDate, orderAtr, reservationRecTimeZone)).isEqualTo(true);
	}
	
	@Test
	public void employeeChangeReservation_no_orderAtr_role_success() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByModifiLst(Arrays.asList("roleID"));
		String roleID = "roleID";
		GeneralDate reservationDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today().addDays(-2); 
		boolean orderAtr = false;
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		assertThat(correctionContent.canEmployeeChangeReservation(roleID, reservationDate, reservationTime, frameNo, orderDate, orderAtr, reservationRecTimeZone)).isEqualTo(true);
	}
	
	@Test
	public void employeeChangeReservation_no_orderAtr_role_fail() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByModifiLst(Arrays.asList("roleID"));
		String roleID = "roleID_1";
		GeneralDate reservationDate = GeneralDate.today();
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today().addDays(-2); 
		boolean orderAtr = false;
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		assertThat(correctionContent.canEmployeeChangeReservation(roleID, reservationDate, reservationTime, frameNo, orderDate, orderAtr, reservationRecTimeZone)).isEqualTo(false);
	}
	
	@Test
	public void changeReservationFromOrderDate_fail() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByChangeDeadlineDay(ContentChangeDeadlineDay.ONE);
		GeneralDate orderDate = GeneralDate.today().addDays(-2);
		assertThat(correctionContent.canChangeReservationFromOrderDate(orderDate)).isEqualTo(false);
	}
	
	@Test
	public void changeReservationFromOrderDate_success() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByChangeDeadlineDay(ContentChangeDeadlineDay.TWO);
		GeneralDate orderDate = GeneralDate.today().addDays(2);
		assertThat(correctionContent.canChangeReservationFromOrderDate(orderDate)).isEqualTo(true);
	}
	
	@Test
	public void changeReservationDuringTime_fail() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByChangeDeadline(ContentChangeDeadline.MODIFIED_DURING_RECEPTION_HOUR);
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		ClockHourMinute reservationTime = new ClockHourMinute(400);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today(); 
		assertThat(correctionContent.canChangeReservationDuringTime(reservationTime, frameNo, orderDate, reservationRecTimeZone)).isEqualTo(false);
	}
	
	@Test
	public void changeReservationDuringTime_success() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByChangeDeadline(ContentChangeDeadline.MODIFIED_DURING_RECEPTION_HOUR);
		ReservationRecTimeZone reservationRecTimeZone = Helper.Setting.ReserRecTimeZone.ReserFrame1;
		ClockHourMinute reservationTime = new ClockHourMinute(500);
		int frameNo = 1;
		GeneralDate orderDate = GeneralDate.today(); 
		assertThat(correctionContent.canChangeReservationDuringTime(reservationTime, frameNo, orderDate, reservationRecTimeZone)).isEqualTo(true);
	}
	
	@Test
	public void manageOrderFunction_fail() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByOrderMng(ReservationOrderMngAtr.CANNOT_MANAGE);
		assertThat(correctionContent.manageOrderFunction()).isEqualTo(false);
	}
	
	@Test
	public void manageOrderFunction_success() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByOrderMng(ReservationOrderMngAtr.CAN_MANAGE);
		assertThat(correctionContent.manageOrderFunction()).isEqualTo(true);
	}
	
	@Test
	public void roleCanModifi_fail() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByModifiLst(Arrays.asList("roleID"));
		String roleID = "roleID_1";
		assertThat(correctionContent.roleCanModifi(roleID)).isEqualTo(false);
	}
	
	@Test
	public void roleCanModifi_success() {
		CorrectionContent correctionContent = Helper.Setting.CorrecContent.createByModifiLst(Arrays.asList("roleID"));
		String roleID = "roleID";
		assertThat(correctionContent.roleCanModifi(roleID)).isEqualTo(true);
	}
}
