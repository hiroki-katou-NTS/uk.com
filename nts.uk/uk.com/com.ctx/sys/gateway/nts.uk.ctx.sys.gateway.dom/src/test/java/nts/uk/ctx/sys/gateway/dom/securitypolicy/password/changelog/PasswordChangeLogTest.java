package nts.uk.ctx.sys.gateway.dom.securitypolicy.password.changelog;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	@Test
	public void getLasterLog() {
		List<PasswordChangeLogDetail> lists = new ArrayList<>(); 
		
		val oldDummy     = new PasswordChangeLogDetail(GeneralDateTime.now(), "");
		val latestDummy = new PasswordChangeLogDetail(GeneralDateTime.now().addDays(1), ""); 
		
		lists.add(oldDummy);
		lists.add(latestDummy);
		val target = new PasswordChangeLog("", lists);
		
		assertThat(target.latestLog()).isEqualTo(latestDummy);
		
	}
}
