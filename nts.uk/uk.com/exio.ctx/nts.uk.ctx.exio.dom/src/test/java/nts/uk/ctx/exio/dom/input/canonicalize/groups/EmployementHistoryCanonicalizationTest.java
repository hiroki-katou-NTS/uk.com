package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import java.util.Arrays;
import java.util.List;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethod;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;

public class EmployementHistoryCanonicalizationTest {

	@Injectable
	CanonicalizationMethod.Require require;

	//@Test
	public void canonicalizeHistory() {
		
		val empCodeCano = new EmployeeCodeCanonicalization(0, 1);
		val target = new EmploymentHistoryCanonicalization(2, 3, 4, empCodeCano);
		val context = Helper.context(ImportingMode.DELETE_RECORD_BEFOREHAND);
		List<IntermediateResult> employeeCanonicalized = Arrays.asList(
				new IntermediateResult(0, null, null, null)
				);
		
		new Expectations() {{

		}};
		
		List<IntermediateResult> actual = NtsAssert.Invoke.privateMethod(
				target, "canonicalizeHistory", require, context, employeeCanonicalized);
	}

	static class Helper {
		public static ExecutionContext context(ImportingMode mode) {
			return new ExecutionContext("companyId", "settingCode", ImportingGroupId.TASK, mode);
		}
	}

}
