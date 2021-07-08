package nts.uk.ctx.exio.dom.input.canonicalize.methods.employee;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethod;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

/**
 * 社員コードを社員IDに正準化する
 * 複数レコードを処理する場合は、行数（メモリ負荷）が予測できないので、1行ずつ処理してConsumerで結果を送り返す設計思想としてある
 */
@Value
public class EmployeeCodeCanonicalization implements CanonicalizationMethod {
	
	/** 社員コードの項目No */
	final int itemNoEmployeeCode;
	
	/** 社員IDの項目No */
	final int itemNoEmployeeId;

	/**
	 * 正準化する
	 */
	@Override
	public void canonicalize(
			CanonicalizationMethod.Require require,
			ExecutionContext context,
			Consumer<IntermediateResult> intermediateResultProvider) {
		
		int rows = require.getMaxRowNumberOfRevisedData(context);
		for (int rowNo = 0; rowNo < rows; rowNo++) {
			
			val revisedData = require.getRevisedDataRecordByRowNo(context, rowNo).get();
			val result = canonicalize(require, revisedData);
			
			intermediateResultProvider.accept(result);
		}
	}

	/**
	 * 渡された編集済みデータを正準化する
	 * @param require
	 * @param revisedData
	 * @return
	 */
	public IntermediateResult canonicalize(
			CanonicalizationMethod.Require require,
			RevisedDataRecord revisedData) {
		
		String employeeCode = revisedData.getItemByNo(itemNoEmployeeCode).get().getString();

		return canonicalize(revisedData, getEmployeeId(require, employeeCode));
	}

	/**
	 * 指定された社員の編集済みデータを正準化する
	 * @param require
	 * @param context
	 * @param employeeCode
	 * @return
	 */
	public Stream<IntermediateResult> canonicalize(
			CanonicalizationMethod.Require require,
			ExecutionContext context,
			String employeeCode) {
		
		val revisedDataRecords = require.getRevisedDataRecordWhere(
				context,
				itemNoEmployeeCode,
				employeeCode);
		
		String employeeId = getEmployeeId(require, employeeCode);
		
		return revisedDataRecords.stream()
				.map(revisedData -> canonicalize(revisedData, employeeId));
	}

	private static String getEmployeeId(CanonicalizationMethod.Require require, String employeeCode) {
		
		return require.getEmployeeDataMngInfoByEmployeeCode(employeeCode)
				.orElseThrow(() -> new RuntimeException("社員が存在しない: " + employeeCode))
				.getEmployeeId();
	}

	private IntermediateResult canonicalize(RevisedDataRecord revisedData, String employeeId) {
		
		return IntermediateResult.create(
				revisedData,
				DataItem.of(itemNoEmployeeId, employeeId),
				itemNoEmployeeCode);
	}
	
	public static interface Require {
		
		Optional<EmployeeDataMngInfo> getEmployeeDataMngInfoByEmployeeCode(String employeeCode);
	}
}
