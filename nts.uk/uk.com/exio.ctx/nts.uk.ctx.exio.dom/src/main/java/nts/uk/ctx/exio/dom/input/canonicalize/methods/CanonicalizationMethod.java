package nts.uk.ctx.exio.dom.input.canonicalize.methods;

import java.util.function.Consumer;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.EmploymentHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.history.EmployeeContinuousHistoryCanonicalization;

/**
 * 正準化の方法
 */
public interface CanonicalizationMethod {
	
	/**
	 * 正準化する
	 * 性能上の都合で1行ずつ永続化処理せねばならないケースもあるため、中間結果は関数経由で返す
	 * @param require
	 * @param context
	 * @param intermediateResultProvider 1レコードずつの中間結果を返す関数
	 * @return
	 */
	public void canonicalize(
			Require require,
			ExecutionContext context,
			Consumer<IntermediateResult> intermediateResultProvider);

	public static interface Require extends
			EmployeeCodeCanonicalization.Require,
			EmployeeContinuousHistoryCanonicalization.RequireCanonicalize,
			EmploymentHistoryCanonicalization.RequireGetHistory {
		
		/** 受け入れた編集済みデータの行数を返す */
		int getNumberOfRowsRevisedData();
		
		void save(AnyRecordToChange recordToChange);
		
		void save(AnyRecordToDelete recordToDelete);
	}
}
