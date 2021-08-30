package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.EmployeeBasicCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.OccurenceHolidayCanonicalizationBase;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.year.EmployeeYearHolidaySettingCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethodRequire;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;

/**
 * 受入グループ別の正準化
 */
public interface DomainCanonicalization {

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
		CanonicalizationMethodRequire,
		IndependentCanonicalization.RequireCanonicalize,
		EmployeeBasicCanonicalization.RequireCanonicalize,
		EmployeeHistoryCanonicalization.RequireCanonicalize,
		AffWorkplaceHistoryCanonicalization.RequireCanonicalize,
		EmployeeYearHolidaySettingCanonicalization.RequireCanonicalize,
		OccurenceHolidayCanonicalizationBase.RequireCanonicalize {
		
		void save(ExecutionContext context, CanonicalizedDataRecord canonicalizedDataRecord);

	}
	
	public static interface RequireAdjsut extends
		IndependentCanonicalization.RequireAdjust,
		EmployeeBasicCanonicalization.RequireAdjust,
		EmployeeHistoryCanonicalization.RequireAdjust,
		AffWorkplaceHistoryCanonicalization.RequireAdjust,
		OccurenceHolidayCanonicalizationBase.RequireAdjust {
		
	}
}
