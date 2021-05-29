package nts.uk.ctx.exio.dom.input.canonicalize.methods.employee;

import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethod;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;

/**
 * 社員コードを社員IDに正準化する
 */
@Value
public class EmployeeCodeCanonicalization implements CanonicalizationMethod {
	
	/** 社員コードの項目No */
	int itemNoEmployeeCode;
	
	/** 社員IDの項目No */
	int itemNoEmployeeId;

	public IntermediateResult canonicalize(
			CanonicalizationMethod.Require require,
			ExecutionContext context,
			RevisedDataRecord revisedData) {
		
		val itemEmployeeCode = revisedData.getItemByNo(itemNoEmployeeCode).get();
		String employeeCode = itemEmployeeCode.getString();
		String employeeId = require.getEmployeeIdByEmployeeCode(context.getCompanyId(), employeeCode);
		
		return IntermediateResult.create(
				revisedData.getItems(),
				DataItem.of(itemNoEmployeeId, employeeId),
				itemNoEmployeeCode);
	}

	public List<IntermediateResult> canonicalize(
			CanonicalizationMethod.Require require,
			ExecutionContext context,
			String employeeCode,
			List<RevisedDataRecord> revisedDataRecords) {
		
		if (revisedDataRecords.isEmpty()) {
			return Collections.emptyList();
		}
		
		// 社員コードのチェック
		revisedDataRecords.stream()
				.map(r -> r.getItemByNo(itemNoEmployeeCode).get().getString())
				.filter(code -> !code.equals(employeeCode))
				.findFirst()
				.ifPresent(code -> {
					throw new RuntimeException("指定外の社員コードが混在している（指定：" + employeeCode  +", 混在：" + code + "）");
				});
		
		String employeeId = require.getEmployeeIdByEmployeeCode(context.getCompanyId(), employeeCode);
		
		return revisedDataRecords.stream()
				.map(revisedData -> IntermediateResult.create(
						revisedData.getItems(),
						DataItem.of(itemNoEmployeeId, employeeId),
						itemNoEmployeeCode))
				.collect(toList());
	}

	@Override
	public void canonicalize(
			CanonicalizationMethod.Require require,
			ExecutionContext context,
			Consumer<IntermediateResult> intermediateResultProvider) {
		
		int rows = require.getNumberOfRowsRevisedData();
		for (int rowNo = 0; rowNo < rows; rowNo++) {
			
			val revisedData = require.getRevisedDataRecordByRowNo(context, rowNo);
			val result = canonicalize(require, context, revisedData);
			
			intermediateResultProvider.accept(result);
		}
	}
	
	public static interface Require {
		
		String getEmployeeIdByEmployeeCode(String companyId, String employeeCode);
		
		RevisedDataRecord getRevisedDataRecordByRowNo(ExecutionContext context, int rowNo);
	}
}
