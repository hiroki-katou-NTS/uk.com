package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeIndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 特別休暇付与残数データの正準化
 */
public class SpecialHolidayGrantRemainCanonicalization extends EmployeeIndependentCanonicalization {
	
	public SpecialHolidayGrantRemainCanonicalization(DomainWorkspace workspace) {
		super(workspace);
	}
	
	@Override
	protected IntermediateResult canonicalizeExtends(
			DomainCanonicalization.RequireCanonicalize require,
			ExecutionContext context, IntermediateResult interm) {
		return interm.addCanonicalized(getFixedItems());
	}
	
	private static CanonicalItemList getFixedItems() {
		return new CanonicalItemList()
			// ID
			.add(101, IdentifierUtil.randomUniqueId().toString())
			// 登録種別
			.add(103, 0)
			// 罪崩し日数
			.add(104, 0)
			// 上限超過消滅日数
			.add(105, 0);
	}
	
	@Override
	protected List<Integer> getPrimaryKeyItemNos(DomainWorkspace workspace){
		return Arrays.asList(101);
	}
	
	@Override
	protected String getParentTableName() {
		return "KRCDT_HD_SP_REMAIN";
	}
	
	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				DomainDataColumn.SID, 
				new DomainDataColumn("SPECIAL_LEAVE_CD", DataType.STRING), 
				new DomainDataColumn("GRANT_DATE", DataType.DATE));
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source).addItem("特別休暇コード").addItem("特別休暇付与日");
	}
}
