package nts.uk.ctx.exio.dom.input.canonicalize.existing;

import java.util.List;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.CreateDomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;

/**
 * 受入の影響を受ける既存データを補正する
 */
public class AdjustExistingData {

	/**
	 * 補正処理を全データ１トランザクションで実行する
	 * @param require
	 * @param context
	 * @return
	 */
	public static AtomTask adjust(RequireAll require, ExecutionContext context) {
		
		val canonicalization = CreateDomainCanonicalization.create(require, context.getDomainId());
		
		return canonicalization.adjust(
				require,
				require.getAllAnyRecordToChange(context),
				require.getAllAnyRecordToDelete(context));
	}
	
	public static interface RequireAll extends RequireCommon {

		List<AnyRecordToChange> getAllAnyRecordToChange(ExecutionContext context);
		
		List<AnyRecordToDelete> getAllAnyRecordToDelete(ExecutionContext context);
	}
	
	/**
	 * 補正処理を１社員１トランザクションで実行する
	 * @param require
	 * @param context
	 * @param employeeId
	 * @return
	 */
	public static AtomTask adjust(RequireEmployee require, ExecutionContext context, String employeeId) {

		val canonicalization = CreateDomainCanonicalization.create(require, context.getDomainId());
		
		int itemNoEmployeeId = canonicalization.getItemNoOfEmployeeId();
		
		return canonicalization.adjust(
				require,
				require.getAnyRecordToChangeWhere(context, itemNoEmployeeId, employeeId),
				require.getAnyRecordToDeleteWhere(context, itemNoEmployeeId, employeeId));
	}

	public static interface RequireEmployee extends RequireCommon {

		List<AnyRecordToChange> getAnyRecordToChangeWhere(ExecutionContext context, int keyItemNo, String keyValue);

		List<AnyRecordToDelete> getAnyRecordToDeleteWhere(ExecutionContext context, int keyItemNo, String keyValue);
	}
	
	public static interface RequireCommon extends
			CreateDomainCanonicalization.Require,
			DomainCanonicalization.RequireAdjsut {
		
	}
}
