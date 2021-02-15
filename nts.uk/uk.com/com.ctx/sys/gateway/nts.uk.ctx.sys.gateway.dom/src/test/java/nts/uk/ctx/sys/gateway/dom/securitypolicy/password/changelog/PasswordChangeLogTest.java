package nts.uk.ctx.sys.gateway.dom.securitypolicy.password.changelog;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

public class PasswordChangeLogTest {

	@Test
	public void latestLog() {
		
		val target = new PasswordChangeLog("user", Arrays.asList(
				changedAt(GeneralDate.ymd(2000, 1, 1)),
				changedAt(GeneralDate.ymd(2020, 4, 1)),
				changedAt(GeneralDate.ymd(1998, 9, 8))));
		
		val actual = target.latestLog().getChangedDateTime();
		assertThat(actual).isEqualTo(GeneralDateTime.ymdhms(2020, 4, 1, 0, 0, 0));
	}
	
	@Test
	public void getter() {

		val target = PasswordChangeLog.firstPassword("user", "hash");
		NtsAssert.invokeGetters(target);
	}

	private static PasswordChangeLogDetail changedAt(GeneralDate date) {
		return new PasswordChangeLogDetail(GeneralDateTime.midnightOf(date), "hash");
	}
}
