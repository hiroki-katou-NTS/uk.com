package nts.uk.ctx.exio.dom.input.canonicalize.methods.employee;

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
 * 複数レコードを処理する場合は、行数（メモリ負荷）が予測できないので、1行ずつ処理してConsumerで結果を送り返す設計思想としてある
 */
@Value
public class EmployeeCodeCanonicalization implements CanonicalizationMethod {
	
	/** 社員コードの項目No */
	int itemNoEmployeeCode;
	
	/** 社員IDの項目No */
	int itemNoEmployeeId;

	/**
	 * 正準化する
	 */
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

	/**
	 * 渡された編集済みデータを正準化する
	 * @param require
	 * @param context
	 * @param revisedData
	 * @return
	 */
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

	/**
	 * 指定された社員の編集済みデータを正準化する
	 * @param require
	 * @param context
	 * @param employeeCode
	 * @param intermediateResultProvider
	 * @return
	 */
	public void canonicalize(
			CanonicalizationMethod.Require require,
			ExecutionContext context,
			String employeeCode,
			Consumer<IntermediateResult> intermediateResultProvider) {
		
		val revisedDataRecords = require.getRevisedDataRecordsByEmployeeCode(context, employeeCode);
		
		String employeeId = require.getEmployeeIdByEmployeeCode(context.getCompanyId(), employeeCode);
		
		revisedDataRecords.stream()
				.map(revisedData -> IntermediateResult.create(
						revisedData.getItems(),
						DataItem.of(itemNoEmployeeId, employeeId),
						itemNoEmployeeCode))
				.forEach(r -> intermediateResultProvider.accept(r));
	}

	
	public static interface Require {
		
		String getEmployeeIdByEmployeeCode(String companyId, String employeeCode);

		List<RevisedDataRecord> getRevisedDataRecordsByEmployeeCode(ExecutionContext context, String employeeCode);
		
		RevisedDataRecord getRevisedDataRecordByRowNo(ExecutionContext context, int rowNo);
	}
}
