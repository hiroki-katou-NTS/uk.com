package nts.uk.ctx.sys.gateway.dom.outage;

import static nts.uk.ctx.sys.gateway.dom.outage.OutageMode.*;
import static nts.uk.ctx.sys.gateway.dom.outage.SystemAvailability.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.outage.helper.RoleExpectation;
import nts.uk.ctx.sys.gateway.dom.outage.helper.RoleExpectation.RoleType;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopMessage;

public class PlannedOutageStateTest {
	
	static StopMessage MESSAGE = new StopMessage("message");

	// 通常運用中
	@Test
	public void no_outage_1() {
		
		val roles = RoleExpectation.setup(RoleType.EMPLOYEE);
		val target = new PlannedOutageState(AVAILABLE, Helper.DUMMY_MODE, Helper.DUMMY_MSG, Helper.DUMMY_MSG);
		val actual = target.statusFor(roles);

		assertThat(actual.isAvailable()).isTrue();
		assertThat(actual.isOutage()).isFalse();
	}
	
	// 前段階
	@Test
	public void no_outage_2() {

		val roles = RoleExpectation.setup(RoleType.EMPLOYEE);
		val target = new PlannedOutageState(PRE_OUTAGE, Helper.DUMMY_MODE, Helper.DUMMY_MSG, Helper.DUMMY_MSG);
		val actual = target.statusFor(roles);

		assertThat(actual.isAvailable()).isTrue();
		assertThat(actual.isOutage()).isFalse();
	}

	// 利用停止, テナント管理者
	@Test
	public void tenant_admin() {

		testOutageAvailable(RoleType.TENANT_ADMIN, OutageMode.ADMINISTRATOR);
		testOutageAvailable(RoleType.TENANT_ADMIN, OutageMode.PERSON_IN_CHARGE);
	}
	
	// 利用停止, 会社管理者
	@Test
	public void company_admin() {

		testOutageAvailable(RoleType.COMPANY_ADMIN, OutageMode.ADMINISTRATOR);
		testOutageAvailable(RoleType.COMPANY_ADMIN, OutageMode.PERSON_IN_CHARGE);
	}
	
	// 利用停止, 担当者
	@Test
	public void person_in_charge() {

		testOutageRejected(RoleType.PERSON_IN_CHARGE, OutageMode.ADMINISTRATOR);
		testOutageAvailable(RoleType.PERSON_IN_CHARGE, OutageMode.PERSON_IN_CHARGE);
	}
	
	// 利用停止, 一般社員
	@Test
	public void employee() {

		testOutageRejected(RoleType.EMPLOYEE, OutageMode.ADMINISTRATOR);
		testOutageRejected(RoleType.EMPLOYEE, OutageMode.PERSON_IN_CHARGE);
	}

	
	/**
	 * 利用停止中だが利用可能なテストケース
	 * @param role
	 * @param mode
	 */
	private void testOutageAvailable(RoleType role, OutageMode mode) {
		
		val roles = RoleExpectation.setup(role);

		val target = new PlannedOutageState(ON_OUTAGE, mode, Helper.DUMMY_MSG, Helper.DUMMY_MSG);
		val actual = target.statusFor(roles);

		assertThat(actual.isAvailable()).isTrue();
		assertThat(actual.isOutage()).isTrue();
		assertThat(actual.getMessage().get()).isEqualTo("Msg_1475");
	}
	
	/**
	 * 利用停止中で利用できないテストケース
	 * @param role
	 * @param mode
	 */
	private void testOutageRejected(RoleType role, OutageMode mode) {

		val roles = RoleExpectation.setup(role);

		val target = new PlannedOutageState(ON_OUTAGE, mode, Helper.DUMMY_MSG, MESSAGE);
		val actual = target.statusFor(roles);
		
		assertThat(actual.isAvailable()).isFalse();
		assertThat(actual.isOutage()).isTrue();
		assertThat(actual.getMessage().get()).isEqualTo(MESSAGE.v());
	}
	
	private static class Helper {
		static OutageMode DUMMY_MODE = ADMINISTRATOR;
		static StopMessage DUMMY_MSG = new StopMessage("dummy");
	}
}
