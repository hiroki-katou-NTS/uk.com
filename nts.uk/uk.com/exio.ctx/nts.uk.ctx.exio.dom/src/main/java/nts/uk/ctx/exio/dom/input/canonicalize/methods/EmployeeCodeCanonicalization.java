package nts.uk.ctx.exio.dom.input.canonicalize.methods;

import java.util.Optional;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.gul.util.Either;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.ItemNoMap;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.employeebasic.EmployeeIdIdentifier;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.IntermediateResult;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;
import nts.uk.ctx.exio.dom.input.errors.RecordError;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

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
	
	public EmployeeCodeCanonicalization(ItemNoMap map) {
		this(map.getItemNo("社員コード"), map.getItemNo("SID"));
	}
	
	/**
	 * 渡された編集済みデータを正準化する
	 * @param require
	 * @param revisedData
	 * @return
	 */
	public Either<ErrorMessage, IntermediateResult> canonicalize(
			CanonicalizationMethodRequire require,
			ExecutionContext context,
			RevisedDataRecord revisedData) {
		
		String employeeCode = revisedData.getItemByNo(itemNoEmployeeCode).get().getString();

		return getEmployeeId(require, context, employeeCode)
				.map(employeeId -> canonicalize(revisedData, employeeId));
	}

	/**
	 * 指定された社員の編集済みデータを正準化する
	 * @param require
	 * @param context
	 * @param employeeCode
	 * @return
	 */
	public Either<Stream<RecordError>, Stream<IntermediateResult>> canonicalize(
			CanonicalizationMethodRequire require,
			ExecutionContext context,
			String employeeCode) {
		
		val revisedDataRecords = require.getRevisedDataRecordWhere(
				context,
				itemNoEmployeeCode,
				employeeCode);
		
		return getEmployeeId(require, context, employeeCode)
				.map(employeeId -> revisedDataRecords.stream()
							.map(r -> canonicalize(r, employeeId)))
				.mapLeft(error -> revisedDataRecords.stream()
							.map(r -> new RecordError(r.getRowNo(), error.getText())));
	}

	private static Either<ErrorMessage, String> getEmployeeId(CanonicalizationMethodRequire require, 	ExecutionContext context, String employeeCode) {
		
		Optional<String> employeeId = EmployeeIdIdentifier.getEmployeeId(require, context, employeeCode); 
		
		return Either.rightOptional(
				employeeId,
				() -> new ErrorMessage("未登録の社員コードです。"));
	}

	private IntermediateResult canonicalize(RevisedDataRecord revisedData, String employeeId) {
		return IntermediateResult.create(revisedData)
				.addCanonicalized(CanonicalItem.of(itemNoEmployeeId, employeeId));
	}
	
	public static interface Require extends EmployeeIdIdentifier.GetEmployeeIdRequire{
	}

	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return source.addItem("SID");
	}
}
