package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeUtil;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.KeyValues;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 年休上限データ 
 */
public class MaxYearHolidayCanonicalization extends IndependentCanonicalization{

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
	
	/** 社員コードの正準化 */
	private final EmployeeCodeCanonicalization employeeCodeCanonicalization;
	
	public MaxYearHolidayCanonicalization(DomainWorkspace workspace) {
		super(workspace);
		this.employeeCodeCanonicalization = new EmployeeCodeCanonicalization(workspace);
	}

	@Override
	public void canonicalize(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		CanonicalizeUtil.forEachEmployee(require, context, employeeCodeCanonicalization, interms -> {
			for(val interm : interms) {
					val result = FixedItem.getLackItemError(interm);
					if(result.isPresent()) {
						require.add(context, ExternalImportError.record(interm.getRowNo(), result.get().getText()));
						continue;
					}
					//既存データのチェックと保存は継承先に任せる
					super.canonicalize(require, context, interm, new KeyValues(Arrays.asList(interm.getItemByNo(this.getItemNoOfEmployeeId()))));
				}
		});
	}

	private static class FixedItem{
		//半日上限回数、半休使用回数、残回数
		private static final Map<Integer, String> timesItems = new HashMap<>();
		static {
			timesItems.put(2, "半日上限回数");
			timesItems.put(3, "半休使用回数");
			timesItems.put(4, "残回数");
		}
		//時間年休上限時間、時間年休使用時間、残時間
		private static final Map<Integer, String> timeNumbers = new HashMap<>();
		static {
			timeNumbers.put(5, "時間年休上限時間");
			timeNumbers.put(6, "時間年休使用時間");
			timesItems.put(7, "残時間");
		}
		
		/**
		 * 受入れる時は1セットとして考えなければならない
		 * 項目を歯抜けで受入れようとしている
		 * @param interm 
		 */
		public static Optional<ErrorMessage> getLackItemError(IntermediateResult interm) {
			val time = hasTimeAllItemNoOrAllNothing(interm, timeNumbers);
			val times = hasTimeAllItemNoOrAllNothing(interm, timesItems);
			if(!(time.isPresent() && times.isPresent())) {
				return Optional.empty();
			}
			val errorMessage = time.isPresent() ? time.get() : times.get();
			return Optional.of(errorMessage);
		}
		
		/**
		 * All or Nothing でTrue,歯抜けの時はは抜けてる項目名 
		 */
		private static Optional<ErrorMessage> hasTimeAllItemNoOrAllNothing(IntermediateResult interm, Map<Integer, String> items) {
			if(items.keySet().stream().allMatch(itemNo -> interm.getItemByNo(itemNo).isPresent())
		   || items.keySet().stream().allMatch(itemNo -> !interm.getItemByNo(itemNo).isPresent())) {
				return Optional.empty();
			}
			return Optional.of(new ErrorMessage(
					items.entrySet().stream()
					.filter(no -> !interm.getItemByNo(no.getKey()).isPresent())
					.map(item -> item.getValue())
					.collect(Collectors.joining("、"))
					+ "の値がありません。"
				));
		}
	}
}
