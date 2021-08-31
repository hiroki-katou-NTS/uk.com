package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.val;
import nts.gul.text.IdentifierUtil;
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
 * 特別休暇付与残数データの正準化
 */
public class SpecialHolidayGrantRemainCanonicalization extends EmployeeIndependentCanonicalization implements DomainCanonicalization{
	
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public static SpecialHolidayGrantRemainCanonicalization create(DomainWorkspace workspace) {
		return new SpecialHolidayGrantRemainCanonicalization(workspace);
	}
	
	public SpecialHolidayGrantRemainCanonicalization(DomainWorkspace workspace) {
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
			// ID
			.add(101, IdentifierUtil.randomUniqueId().toString())
			// 登録種別
			.add(103, 0)
			// 罪崩し日数
			.add(104, 0)
			// 上限超過消滅日数
			.add(105, 0);
	}
	
	private static List<Object> getPrimaryKeys(IntermediateResult record, DomainWorkspace workspace) {
		return Arrays.asList(
				record.getItemByNo(workspace.getItemByName("SID").getItemNo()).get().getString(), 
				record.getItemByNo(workspace.getItemByName("SPECIAL_LEAVE_CD").getItemNo()).get().getString(), 
				record.getItemByNo(workspace.getItemByName("GRANT_DATE").getItemNo()).get().getString());
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
				new DomainDataColumn("GRANT_DATE", DataType.DATETIME));
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return super.appendMeta(source)
				.addItem("ID")
				.addItem("登録種別")
				.addItem("積み崩し日数")
				.addItem("上限超過消滅日数");
	}
}
