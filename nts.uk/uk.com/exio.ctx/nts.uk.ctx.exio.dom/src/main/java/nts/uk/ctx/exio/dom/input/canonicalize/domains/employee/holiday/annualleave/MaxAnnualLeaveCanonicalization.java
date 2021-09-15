package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.annualleave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 年休上限データ 
 */
public class MaxAnnualLeaveCanonicalization extends IndependentCanonicalization {
	
	/** 社員コードの正準化 */
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public MaxAnnualLeaveCanonicalization() {
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(getItemNoMap());
	}

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}
	
	public static class Items {
		public static final int 社員コード = 1;
		public static final int 半日上限回数 = 2;
		public static final int 半休使用回数 = 3;
		public static final int 残回数 = 4;
		public static final int 時間年休上限時間 = 5;
		public static final int 時間年休使用時間 = 6;
		public static final int 残時間 = 7;
		public static final int SID = 100;
	}

	@Override
	protected String getParentTableName() {
		return "KRCDT_HDPAID_MAX";
	}

	@Override
	protected List<String> getChildTableNames() {
		return Collections.emptyList();
	}

	@Override
	protected List<DomainDataColumn> getDomainDataKeys() {
		return Arrays.asList(DomainDataColumn.SID);
	}

	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		// 受入データ内の重複チェック
		Set<KeyValues> importingKeys = new HashSet<>();
		
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms -> {
			for(val interm : interms) {
					val results = FixedItem.getLackItemError(interm);
					if(!results.isEmpty()) {
						results.stream().peek(result ->require.add(context, result));
						continue;
					}
					val keyValue = getPrimaryKeys(interm);
					if (importingKeys.contains(keyValue)) {
						require.add(context, ExternalImportError.record(interm.getRowNo(), "受入データの中にキーの重複があります。"));
						return; // 次のレコードへ
					}
					
					//既存データのチェックと保存は継承先に任せる
					super.canonicalize(require, context, interm, getPrimaryKeys(interm));
				}
		});
	}
	
	private KeyValues getPrimaryKeys(IntermediateResult interm) {
		//このドメインのKeyはSIDなので、Stringで取り出す。
		return new KeyValues(Arrays.asList(interm.getItemByNo(Items.SID).get().getString()));
	}
	
	@Override
	protected List<Integer> getPrimaryKeyItemNos(DomainWorkspace workspace) {
		return Arrays.asList(100);//SID
	}

	private static class FixedItem{
		//半日上限回数、半休使用回数、残回数
		private static final List<Integer> timesNumbers = new ArrayList<>();
		static {
			timesNumbers.add(2);
			timesNumbers.add(3);
			timesNumbers.add(4);
		}
		//時間年休上限時間、時間年休使用時間、残時間
		private static final List<Integer> timeNumbers = new ArrayList<>();
		static {
			timeNumbers.add(5);
			timeNumbers.add(6);
			timeNumbers.add(7);
		}
		
		/**
		 * 受入れる時は1セットとして考えなければならない
		 * 項目を歯抜けで受入れようとしている
		 * @param interm 
		 */
		public static List<ExternalImportError> getLackItemError(IntermediateResult interm) {
			val timeErrors = hasTimeAllItemNoOrAllNothing(interm, timesNumbers)
					.stream()
					.map(errorItemNo -> new ExternalImportError(interm.getRowNo(), errorItemNo, "値がありません。"))
					.collect(Collectors.toList());
			val timesErrors = hasTimeAllItemNoOrAllNothing(interm, timeNumbers)
					.stream()
					.map(errorItemNo -> new ExternalImportError(interm.getRowNo(), errorItemNo, "値がありません。"))
					.collect(Collectors.toList());
			if(timeErrors.isEmpty() && timesErrors.isEmpty()) {
				return Collections.emptyList();
			}
			return timeErrors.isEmpty() ? timesErrors : timeErrors;
		}
		
		/**
		 * All or Nothing でTrue,歯抜けの時はは抜けてる項目名 
		 */
		private static List<Integer> hasTimeAllItemNoOrAllNothing(IntermediateResult interm, List<Integer> items) {
			return items.stream().filter(t -> !interm.getItemByNo(t).isPresent()).collect(Collectors.toList());
		}
	}
	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return employeeCodeCanonicalization.appendMeta(source);
	}
}
