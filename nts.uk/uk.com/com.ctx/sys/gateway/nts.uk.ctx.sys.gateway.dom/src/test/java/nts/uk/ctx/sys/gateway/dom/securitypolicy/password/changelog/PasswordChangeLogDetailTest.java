package nts.uk.ctx.sys.gateway.dom.securitypolicy.password.changelog;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

public class PasswordChangeLogDetailTest {

	@Test
	public void ageInDays() {
		
		val today = GeneralDate.ymd(2020, 12, 31);
		GeneralDateTime.FAKED_NOW = GeneralDateTime.midnightOf(today);
		
		val changedAt = today.addDays(-30);
		val target = new PasswordChangeLogDetail(GeneralDateTime.midnightOf(changedAt), "hash");
		
		int actual = target.ageInDays();
		assertThat(actual).isEqualTo(30);
	}

	@Test
	public void getter() {
		
		val target = new PasswordChangeLogDetail(GeneralDateTime.now(), "hash");
		NtsAssert.invokeGetters(target);
	}
}
