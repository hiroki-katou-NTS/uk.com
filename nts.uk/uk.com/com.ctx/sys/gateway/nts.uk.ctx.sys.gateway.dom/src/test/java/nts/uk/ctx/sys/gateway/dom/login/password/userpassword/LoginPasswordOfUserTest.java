package nts.uk.ctx.sys.gateway.dom.login.password.userpassword;

import static java.util.stream.Collectors.*;
import static nts.arc.time.GeneralDate.*;
import static nts.arc.time.GeneralDateTime.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDate;

public class LoginPasswordOfUserTest {
	
	@Test
	public void matches() {
		
		val target = Arrays.asList(
				Helper.Detail.of(ymd(2000, 1, 1), "a"),
				Helper.Detail.of(ymd(2000, 2, 1), "b"),
				Helper.Detail.of(ymd(2000, 3, 1), "c"))
				.stream().collect(Helper.createTarget());

		assertThat(target.matches("a")).as("a").isFalse();
		assertThat(target.matches("b")).as("b").isFalse();
		assertThat(target.matches("c")).as("c").isTrue();
		assertThat(target.matches("x")).as("x").isFalse();
	}	
	
	@Test
	public void matches_empty() {
		
		val target = Helper.createTarget(Collections.emptyList());

		assertThat(target.matches("a")).as("a").isFalse();
	}
	
	@Test
	public void getLatestPasswords() {
		
		val target = Arrays.asList(
				Helper.Detail.of(ymd(2000, 1, 1), "a"),
				Helper.Detail.of(ymd(2000, 2, 1), "b"),
				Helper.Detail.of(ymd(2000, 3, 1), "c"))
				.stream().collect(Helper.createTarget());
		
		val actual = target.getLatestPasswords(2);
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0).getHashedPassword()).isEqualTo(Helper.hash("c"));
		assertThat(actual.get(1).getHashedPassword()).isEqualTo(Helper.hash("b"));
	}

	@Test
	public void latestLog() {
		
		val target = Arrays.asList(
				ymd(2000, 1, 1),
				ymd(2020, 4, 1),
				ymd(1998, 9, 8))
				.stream()
				.map(Helper.Detail::changedAt)
				.collect(Helper.createTarget());
		
		val latestDateTime = target.getLatestPassword().get().getChangedDateTime();
		assertThat(latestDateTime).isEqualTo(ymdhms(2020, 4, 1, 0, 0, 0));
	}
	
	private static class Dummy {
		static final PasswordState PASS_STATE = null;
	}
	
	private static class Helper {
		
		static final String USER_ID = "user";
		
		static Collector<PasswordChangeLogDetail, Object, LoginPasswordOfUser> createTarget() {
			return Collectors.collectingAndThen(toList(), Helper::createTarget);
		}
		
		static LoginPasswordOfUser createTarget(List<PasswordChangeLogDetail> details) {
			return new LoginPasswordOfUser(USER_ID, Dummy.PASS_STATE, details);
		}

		static HashedLoginPassword hash(String password) {
			return HashedLoginPassword.hash(password, USER_ID);
		}
		
		static class Detail {
			
			static PasswordChangeLogDetail of(GeneralDate date, String password) {
				return new PasswordChangeLogDetail(midnightOf(date), hash(password));
			}

			static PasswordChangeLogDetail changedAt(GeneralDate date) {
				return new PasswordChangeLogDetail(midnightOf(date), hash("password"));
			}
		}
	}
}
