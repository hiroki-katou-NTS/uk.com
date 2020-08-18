package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.shr.com.history.DateHistoryItem;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class BentoMenuHistoryTest {


	@Test
	public void getters() {

		BentoMenuHistory bentoMenuHistory = new BentoMenuHistory("cid",new ArrayList<>());
		NtsAssert.invokeGetters(bentoMenuHistory);
	}
	@Test
	public void toDomainTest() {

		BentoMenuHistory bentoMenuHistory = new BentoMenuHistory("cid",new ArrayList<>());
		val result = BentoMenuHistory.toDomain("cid",new ArrayList<>());
		assertThat(result).isEqualToComparingFieldByField(bentoMenuHistory);

	}
	@Test
	public void toDomainTest_2() {

		val result = BentoMenuHistory.toDomain("cid",Arrays.asList(DateHistoryItem.createNewHistory(
				new DatePeriod(GeneralDate.today(),GeneralDate.max()))));
		assertThat(result.getHistoryItems().size()).isEqualTo(1);
		assertThat(result.companyId).isEqualTo("cid");
	}

}
