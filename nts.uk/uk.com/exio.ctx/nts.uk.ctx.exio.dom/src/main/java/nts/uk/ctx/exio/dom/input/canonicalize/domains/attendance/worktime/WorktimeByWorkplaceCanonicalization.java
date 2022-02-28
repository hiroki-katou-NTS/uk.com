package nts.uk.ctx.exio.dom.input.canonicalize.domains.attendance.worktime;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.WorkplaceCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

import java.util.*;

/**
 * 職場別就業時間帯の正準化
 */
public class WorktimeByWorkplaceCanonicalization extends IndependentCanonicalization {

	private final WorkplaceCodeCanonicalization workplaceCodeCanonicalization;

	public WorktimeByWorkplaceCanonicalization() {
		workplaceCodeCanonicalization = new WorkplaceCodeCanonicalization(Items.基準日, Items.職場コード, Items.職場ID);
	}

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 職場コード = 1;
		public static final int 就業時間帯コード = 2;
		public static final int 職場ID = 101;
		public static final int 基準日 = 102;
	}

	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {

		val workspace = require.getDomainWorkspace(context.getDomainId());

		// 受入データ内の重複チェック
		Set<KeyValues> importingKeys = new HashSet<>();

		CanonicalizeUtil.forEachRow(require, context, revisedData -> {

			IntermediateResult interm = IntermediateResult.create(revisedData);
			// 職場コードの正準化
			interm =  interm.optionalItem(CanonicalItem.of(Items.基準日, GeneralDate.today()));
			val either = workplaceCodeCanonicalization.canonicalize(require, interm, interm.getRowNo());
			if (either.isLeft()) {
				require.add(ExternalImportError.of(context.getDomainId(), either.getLeft()));
				return;
			}
			interm = either.getRight();

			// キー重複チェック
			KeyValues key = KeyValues.create(interm, workspace.getPkItemNos());
			if (importingKeys.contains(key)) {
				require.add(ExternalImportError.record(interm.getRowNo(), context.getDomainId(), "受入データの中にキーの重複があります。"));
				return;
			}
			importingKeys.add(key);

			super.canonicalize(require, context, interm, key);
		});
	}

	@Override
	protected String getParentTableName() {
		return "KSHMT_WT_COM_WKP";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				new DomainDataColumn("WORKTIME_CD", DataType.STRING),
				new DomainDataColumn("WKP_ID", DataType.STRING));
	}
}
