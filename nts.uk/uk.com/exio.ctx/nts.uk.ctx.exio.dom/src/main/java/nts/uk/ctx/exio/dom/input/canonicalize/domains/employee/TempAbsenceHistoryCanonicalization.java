package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import java.util.Arrays;
import java.util.List;

import lombok.val;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.AffWorkplaceHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 休職休業履歴の正準化
 */
public class TempAbsenceHistoryCanonicalization extends EmployeeHistoryCanonicalization implements  DomainCanonicalization {
	
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public static DomainCanonicalization create(DomainWorkspace workspace) {
		return new TempAbsenceHistoryCanonicalization(workspace);
	}
	
	public TempAbsenceHistoryCanonicalization(DomainWorkspace workspace) {
		super(workspace, HistoryType.UNDUPLICATABLE);
		employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}
	
	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		// 社員ごとに正準化
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms -> {
			val historyCanonicalizedInterms = canonicalizeHistory(require, context, interms);
			// 1レコードごとに正準化
			for (val interm : historyCanonicalizedInterms) {
				// 固定値の追加
				val added = interm.addCanonicalized(getFixedItems());
				require.save(context, added.complete());
			}
		});
	}
	
	private static CanonicalItemList getFixedItems() {
		return new CanonicalItemList()
			//　履歴ID
			.add(102, IdentifierUtil.randomUniqueId().toString())
			// 社会保険支給対象区分
			.addNull(103)
			// 家族メンバーID
			.addNull(104)
			// 多胎妊娠区分
			.addNull(105)
			// 同一家族の休業有無
			.addNull(106)
			// 子の区分
			.addNull(107)
			// 縁組成立の年月日
			.addNull(108)
			// 配偶者が休業しているか
			.addNull(109)
			// 同一家族の短時間勤務日数
			.addNull(110);
	}
	
	@Override
	protected String getParentTableName() {
		return "BSYMT_TEMP_ABS_HIST";
	}
	
	@Override
	protected List<String> getChildTableNames() {
		return Arrays.asList("BSYMT_TEMP_ABS_HIST_ITEM");
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				new DomainDataColumn("社員コード", DataType.STRING),
				new DomainDataColumn("開始日", DataType.DATE),
				new DomainDataColumn("終了日", DataType.DATE)
		);
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source)
				.addItem("社会保険支給対象区分")
				.addItem("家族メンバーID")
				.addItem("多胎妊娠区分")
				.addItem("同一家族の休業有無")
				.addItem("子の区分")
				.addItem("縁組成立の年月日")
				.addItem("配偶者が休業しているか")
				.addItem("同一家族の短時間勤務日数");
	}
}
