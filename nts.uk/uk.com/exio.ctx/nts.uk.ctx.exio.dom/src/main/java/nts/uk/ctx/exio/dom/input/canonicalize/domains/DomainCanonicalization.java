package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.AffJobTitleHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.AffWorkplaceHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.CardNumberCanonicalaization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.employeebasic.EmployeeBasicCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.OccurenceHolidayCanonicalizationBase;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.organization.workplace.WorkplaceCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeIndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.recode.stamp.StampCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethodRequire;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;

/**
 * 受入グループ別の正準化
 */
public interface DomainCanonicalization {

	ItemNoMap getItemNoMap();

	default int getItemNoByName(String itemName) {
		return getItemNoMap().getItemNo(itemName);
	}

	default String getItemNameByNo(int itemNo) {
		return getItemNoMap().getItemName(itemNo);
	}

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
	 * 他のドメインの移送マッピングを流用する場合はOverrideすること
	 *　(exp...打刻E版、Smile休職情報)
	 */
	default ImportingDomainId getTransferDomainId(ExecutionContext context) {
		return context.getDomainId();
	}
	
	/**
	 * 受入に影響される既存データを補正する
	 * @param require
	 * @param context
	 * @param recordsToChange
	 * @param recordsToDelete
	 */
	AtomTask adjust(
			RequireAdjsut require,
			ExecutionContext context,
			List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete);

	/**
	 * この受入グループにおける社員IDの項目Noを返す
	 * @return
	 * @throws UnSupportedOperationException そもそも社員IDの項目が存在しないグループに対して実行した場合
	 */
	default int getItemNoOfEmployeeId() {
		return this.getItemNoByName("SID");
	}

	public static interface RequireCanonicalize extends
			CanonicalizationMethodRequire,
			IndependentCanonicalization.RequireCanonicalize,
			WorkplaceCanonicalization.RequireCanonicalize,
			EmployeeIndependentCanonicalization.RequireCanonicalize,
			EmployeeBasicCanonicalization.RequireCanonicalize,
			EmployeeHistoryCanonicalization.RequireCanonicalize,
			AffJobTitleHistoryCanonicalization.RequireCanonicalize,
			AffWorkplaceHistoryCanonicalization.RequireCanonicalize,
			OccurenceHolidayCanonicalizationBase.RequireCanonicalize,
			CardNumberCanonicalaization.RequireCanonicalize,
			StampCanonicalization.RequireCanonicalize{

		void save(ExecutionContext context, CanonicalizedDataRecord canonicalizedDataRecord);

	}

	public static interface RequireAdjsut extends
			IndependentCanonicalization.RequireAdjust,
			WorkplaceCanonicalization.RequireAdjust,
			EmployeeBasicCanonicalization.RequireAdjust,
			EmployeeHistoryCanonicalization.RequireAdjust,
			AffJobTitleHistoryCanonicalization.RequireAdjust,
			AffWorkplaceHistoryCanonicalization.RequireAdjust,
			OccurenceHolidayCanonicalizationBase.RequireAdjust,
			CardNumberCanonicalaization.RequireAdjust{

	}
}
