package nts.uk.ctx.exio.dom.input.canonicalize.methods;

import java.util.Optional;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 社員コードを社員IDに正準化する
 * 複数レコードを処理する場合は、行数（メモリ負荷）が予測できないので、1行ずつ処理してConsumerで結果を送り返す設計思想としてある
 */
@Value
@AllArgsConstructor
public class EmployeeCodeCanonicalization {
	
	/** 社員コードの項目No */
	final int itemNoEmployeeCode;
	
	/** 社員IDの項目No */
	final int itemNoEmployeeId;
	
	public EmployeeCodeCanonicalization(DomainWorkspace workspace) {
		itemNoEmployeeCode = workspace.getItemByName("社員コード").getItemNo();
		itemNoEmployeeId = workspace.getItemByName("SID").getItemNo();
	}

	/**
	 * 渡された編集済みデータを正準化する
	 * @param require
	 * @param revisedData
	 * @return
	 */
	public IntermediateResult canonicalize(
			CanonicalizationMethodRequire require,
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
			CanonicalizationMethodRequire require,
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

	private static String getEmployeeId(CanonicalizationMethodRequire require, String employeeCode) {
		
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

	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return source.addItem("SID");
	}
}
