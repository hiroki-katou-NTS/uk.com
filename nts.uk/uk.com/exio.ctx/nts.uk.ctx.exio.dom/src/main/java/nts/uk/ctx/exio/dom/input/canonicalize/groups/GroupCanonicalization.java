package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethod;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.history.EmployeeContinuousHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;

/**
 * 受入グループ別の正準化
 */
public interface GroupCanonicalization {

	/**
	 * 正準化する
	 * @param require
	 * @param context
	 */
	void canonicalize(RequireCanonicalize require, ExecutionContext context);
	
	/**
	 * メタ情報を追記する
	 * @param source
	 * @return
	 */
	ImportingDataMeta appendMeta(ImportingDataMeta source);
	
	/**
	 * 受入に影響される既存データを補正する
	 * @param require
	 * @param recordsToChange
	 * @param recordsToDelete
	 */
	AtomTask adjust(
			RequireAdjsut require,
			List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete);
	
	/**
	 * この受入グループにおける社員IDの項目Noを返す
	 * @return
	 * @throws UnSupportedOperationException そもそも社員IDの項目が存在しないグループに対して実行した場合
	 */
	int getItemNoOfEmployeeId();
	
	public static interface RequireCanonicalize extends
		CanonicalizationMethod.Require,
		IndependentCanonicalization.RequireCanonicalize,
		DailyPerformanceCanonicalization.RequireCanonicalize {
		
		void save(ExecutionContext context, CanonicalizedDataRecord canonicalizedDataRecord);
	}
	
	public static interface RequireAdjsut extends
		IndependentCanonicalization.RequireAdjust,
		EmployeeContinuousHistoryCanonicalization.RequireAdjust,
		DailyPerformanceCanonicalization.RequireAdjust {
		
	}
}
