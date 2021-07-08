package nts.uk.ctx.exio.dom.input.canonicalize;

import java.util.function.Consumer;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

public class CanonicalizeUtil {

	public static void forEachRow(
			CanonicalizationMethod.Require require,
			ExecutionContext context,
			Consumer<RevisedDataRecord> process) {

		int rowsCount = require.getMaxRowNumberOfRevisedData(context);
		
		for (int rowNo = 1; rowNo <= rowsCount; rowNo++) {
			require.getRevisedDataRecordByRowNo(context, rowNo).ifPresent(process);
		}
	}
}
