package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.annualleave;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.val;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
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
 * 年休付与残数データ 
 */
public class AnnualLeaveRemainingCanonicalization extends IndependentCanonicalization{
	
	@Override
	protected String getParentTableName() {
		return "KRCDT_HDPAID_REM";
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
	
	public AnnualLeaveRemainingCanonicalization(DomainWorkspace workspace) {
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
					require.add(ExternalImportError.record(interm.getRowNo(), "受入データの中にキーの重複があります。"));
					return; // 次のレコードへ
				}
				importingKeys.add(keyValue);
				
				super.canonicalize(require, context, interm, keyValue);
			}
		});
	}
	
	private KeyValues getPrimaryKeys(IntermediateResult interm, DomainWorkspace workspace) {
		//このドメインのKeyはSIDなので、Stringで取り出す。
		return new KeyValues(Arrays.asList(interm.getItemByNo(this.getItemNoOfEmployeeId()).get().getString(),
																	interm.getItemByNo(2).get().getDate()));
	}

	@Override
	protected IntermediateResult canonicalizeExtends(IntermediateResult targertResult) {
		return addFixedItems(targertResult);
	}

	/**
	 *  受入時に固定の値を入れる物たち
	 */
	private IntermediateResult addFixedItems(IntermediateResult interm) {
		return interm.addCanonicalized(CanonicalItem.of(100,IdentifierUtil.randomUniqueId()))
				  .addCanonicalized(CanonicalItem.of(102,GrantRemainRegisterType.MANUAL.value))
				  .addCanonicalized(CanonicalItem.of(103,BigDecimal.ZERO))
				  .addCanonicalized(CanonicalItem.of(104,BigDecimal.ZERO))
				  .addCanonicalized(CanonicalItem.of(105,BigDecimal.ZERO))
				  .addCanonicalized(CanonicalItem.of(106,0))
				  .addCanonicalized(CanonicalItem.of(107,0))
				  .addCanonicalized(CanonicalItem.of(108,0));
	}
	
	@Override
	protected List<Integer> getPrimaryKeyItemNos(DomainWorkspace workspace) {
		return Arrays.asList(101);//SID
	}
	
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return employeeCodeCanonicalization.appendMeta(source);
	}
}
