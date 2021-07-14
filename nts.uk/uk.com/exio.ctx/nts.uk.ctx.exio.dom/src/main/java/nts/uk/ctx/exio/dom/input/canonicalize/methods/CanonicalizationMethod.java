package nts.uk.ctx.exio.dom.input.canonicalize.methods;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.EmploymentHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.generic.EmployeeContinuousHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

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
	
	/**
	 * メタ情報を追記する
	 * @param source
	 * @return
	 */
	ImportingDataMeta appendMeta(ImportingDataMeta source);

	public static interface Require extends
			ImportingDataMeta.Require,
			EmployeeCodeCanonicalization.Require,
			EmployeeContinuousHistoryCanonicalization.RequireCanonicalize,
			EmploymentHistoryCanonicalization.RequireGetHistory {
		
		/** 受け入れた編集済みデータの行数を返す */
		int getMaxRowNumberOfRevisedData(ExecutionContext context);
		
		/** 受け入れた編集済みデータ全てから特定の項目Noの文字列値を返す */
		List<String> getStringsOfRevisedData(ExecutionContext context, int itemNo);

		/** 受け入れた編集済みデータから特定のCSV行番号のものを返す */
		Optional<RevisedDataRecord> getRevisedDataRecordByRowNo(ExecutionContext context, int rowNo);
		
		/** 受け入れた編集済みデータから特定の項目Noの値が条件に合致するものを返す */
		List<RevisedDataRecord> getRevisedDataRecordWhere(ExecutionContext context, int itemNoCondition, String conditionString);
		
		void save(ExecutionContext context, AnyRecordToChange recordToChange);
		
		void save(ExecutionContext context, AnyRecordToDelete recordToDelete);
	}
}
