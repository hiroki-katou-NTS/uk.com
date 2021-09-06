package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.annualleave;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 社員の年休付与設定
 */
public class EmployeeAnnualLeaveSettingCanonicalization extends IndependentCanonicalization {

	@Override
	protected String getParentTableName() {
		return "KRCMT_HDPAID_BASIC";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(DomainDataColumn.SID);
	}
	
	
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public EmployeeAnnualLeaveSettingCanonicalization(DomainWorkspace workspace) {
		super(workspace);
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}
	
	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require,ExecutionContext context) {
		// 受入データ内の重複チェック
		Set<KeyValues> importingKeys = new HashSet<>();

		//社員コード⇒IDの正準化
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms ->{
			for(val interm : interms) {
				val keyValue = getPrimaryKeys(interm, workspace);
				if (importingKeys.contains(keyValue)) {
					require.add(context, ExternalImportError.record(interm.getRowNo(), "受入データの中にキーの重複があります。"));
					return; // 次のレコードへ
				}
				importingKeys.add(keyValue);
				
				super.canonicalize(require, context, interm, keyValue);
			}
		});
	}
	
	private KeyValues getPrimaryKeys(IntermediateResult interm, DomainWorkspace workspace) {
		//このドメインのKeyはSIDなので、Stringで取り出す。
		return new KeyValues(Arrays.asList(interm.getItemByNo(this.getItemNoOfEmployeeId()).get().getString()));
	}

	@Override
	protected List<Integer> getPrimaryKeyItemNos(DomainWorkspace workspace) {
		return Arrays.asList(getItemNoOfEmployeeId());//SID
	}
	
	/**
	 * 追加の正準化処理が必要ならoverrideすること
	 * @param targetContainers
	 */
	protected IntermediateResult canonicalizeExtends(IntermediateResult targertResult) {
		return addFixedItems(targertResult);
	}
	
	/**
	 *  受入時に固定の値を入れる物たち
	 */
	private IntermediateResult addFixedItems(IntermediateResult interm) {
	    return interm.addCanonicalized(CanonicalItem.nullValue(100), 100)
	    		    		   .optionalItem(CanonicalItem.of(2, 0));
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return employeeCodeCanonicalization.appendMeta(source);
	}
}
