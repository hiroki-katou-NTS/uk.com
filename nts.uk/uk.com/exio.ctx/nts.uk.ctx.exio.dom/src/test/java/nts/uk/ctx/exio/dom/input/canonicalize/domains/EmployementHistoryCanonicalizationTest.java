package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import static nts.arc.time.GeneralDate.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Verifications;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.canonicalize.Helper;
import nts.uk.ctx.exio.dom.input.canonicalize.Helper.Dummy;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.EmploymentHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.shr.com.history.DateHistoryItem;

public class EmployementHistoryCanonicalizationTest {

	@Injectable
	DomainCanonicalization.RequireCanonicalize require;
	
	
	static class ItemNo {
		static final int EMP_CD = 1;
		static final int START_DATE = 2;
		static final int END_DATE = 3;
		static final int EMP_ID = 101;
		static final int HIST_ID = 102;
	}

	static final String EMPLOYEE_CODE = "employeeCode";
	static final String EMPLOYEE_ID = "employeeId";
	static final String EXIST_HIST_ID = "existingHistoryId";
	static final String NEW_HIST_ID = "newHistoryId";
	static final DatePeriod EXIST_HIST_PERIOD = new DatePeriod(ymd(2000, 1, 1), ymd(9999, 12, 31));

	@Test
	public void canonicalizeHistory() {
		
		val existingHistoryItem = new DateHistoryItem(EXIST_HIST_ID, EXIST_HIST_PERIOD);
		val existingHistoryItems = new ArrayList<>(Arrays.asList(existingHistoryItem));
		
		val history = new EmploymentHistory(Dummy.COMPANY_ID, EMPLOYEE_ID, existingHistoryItems);
		
		val newHistoryPeriod = new DatePeriod(ymd(2021, 4, 1), EXIST_HIST_PERIOD.end());
		IntermediateResult employeeCanonicalized = Helper.Interm.of(
				Helper.ItemList.of(new DataItem(ItemNo.EMP_ID, EMPLOYEE_ID)),
				Helper.ItemList.of(new DataItem(ItemNo.EMP_CD, EMPLOYEE_CODE)),
				Helper.ItemList.of(
						new DataItem(ItemNo.START_DATE, newHistoryPeriod.start()),
						new DataItem(ItemNo.END_DATE, newHistoryPeriod.end())));
		
		new Expectations() {{
			require.getEmploymentHistory(EMPLOYEE_ID);
			result = Optional.of(history);
		}};
		
		new MockUp<IdentifierUtil>() {
			@Mock public String randomUniqueId() { return NEW_HIST_ID; }
		};

		val empCodeCano = new EmployeeCodeCanonicalization(ItemNo.EMP_CD, ItemNo.EMP_ID);
		val target = new EmploymentHistoryCanonicalization(
				ItemNo.START_DATE, ItemNo.END_DATE, ItemNo.HIST_ID, empCodeCano);
		
		val context = Helper.context(ImportingMode.UPDATE_ONLY);
		
		List<IntermediateResult> actual = NtsAssert.Invoke.privateMethod(
				target, "canonicalizeHistory", require, context, Arrays.asList(employeeCanonicalized));
		
		assertThat(actual.size()).isEqualTo(1);
		
		// 正準化結果を検証
		Helper.asserts(actual.get(0))
			.equal(ItemNo.HIST_ID, NEW_HIST_ID);
		
		new Verifications() {{
			
			// 既存レコードの変更を検証
			AnyRecordToChange toChange;
			require.save(context, (AnyRecordToChange) (toChange = withCapture())); times = 1;
			
			assertThat(toChange.getKey(ItemNo.HIST_ID).asString()).isEqualTo(EXIST_HIST_ID);
		}};
	}

}
