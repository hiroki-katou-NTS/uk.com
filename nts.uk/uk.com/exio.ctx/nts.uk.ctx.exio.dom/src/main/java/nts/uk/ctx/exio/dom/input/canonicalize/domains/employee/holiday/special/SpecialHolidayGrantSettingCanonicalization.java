package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItemList;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.EmployeeIndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 社員の特別休暇付与設定の正準化
 */
public class SpecialHolidayGrantSettingCanonicalization extends EmployeeIndependentCanonicalization implements DomainCanonicalization{
	
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public static SpecialHolidayGrantSettingCanonicalization create(DomainWorkspace workspace) {
		return new SpecialHolidayGrantSettingCanonicalization(workspace);
	}
	
	public SpecialHolidayGrantSettingCanonicalization(DomainWorkspace workspace) {
		super(workspace);
		employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}
	
	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		// 社員ごとに正準化
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms -> {
			// 1レコードごとに正準化
			for (val interm : interms) {
				val keyValues = new KeyValues(getPrimaryKeys(interm, workspace));
				// 固定値の追加
				interm.addCanonicalized(getFixedItems());
				
				canonicalize(require, context, interm, keyValues);
			}
		});
	}
	
	private static CanonicalItemList getFixedItems() {
		return new CanonicalItemList()
			// 適用設定
			.add(102, 0)
			// 付与日数
			.addNull(103)
			// 勤続年数テーブル
			.addNull(104)
			// 付与日テーブル
			.addNull(105);
	}
	
	private static List<Object> getPrimaryKeys(IntermediateResult record, DomainWorkspace workspace) {
		return Arrays.asList(
				record.getItemByNo(workspace.getItemByName("SID").getItemNo()).get().getString(), 
				record.getItemByNo(workspace.getItemByName("特別休暇コード").getItemNo()).get().getString());
	}
	
	@Override
	protected String getParentTableName() {
		return "KRCMT_HDSP_BASIC";
	}
	
	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}
	
	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(
				DomainDataColumn.SID, 
				new DomainDataColumn("SPECIAL_LEAVE_CD", DataType.STRING));
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source);
	}
}
