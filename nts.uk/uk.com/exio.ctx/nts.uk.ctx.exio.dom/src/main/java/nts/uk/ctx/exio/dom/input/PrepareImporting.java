package nts.uk.ctx.exio.dom.input;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.gul.util.value.MutableValue;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeRevisedData;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.validation.ValidateData;

/**
 * 受入処理を準備する
 */
public class PrepareImporting {

	public static void prepare(
			Require require,
			String companyId,
			ExternalImportCode settingCode,
			InputStream csvFileStream) {
		
		val setting = require.getExternalImportSetting(companyId, settingCode)
				.orElseThrow(() -> new RuntimeException("not found: " + companyId + ", " + settingCode));
		val context = ExecutionContext.create(setting);
		
		// 受入データの組み立て
		assembleImportingData(require, csvFileStream, setting, context);
		
		// 編集済みデータの正準化
		CanonicalizeRevisedData.canonicalize(require, context);
	}

	/**
	 * 受入データを組み立てる
	 * @param require
	 * @param csvFileStream
	 * @param setting
	 * @param context
	 */
	private static void assembleImportingData(
			Require require,
			InputStream csvFileStream,
			ExternalImportSetting setting,
			ExecutionContext context) {
		
		val parser = setting.getCsvFileInfo().createParser();

		MutableValue<List<String>> columnNames = new MutableValue<>();
		parser.parse(
				csvFileStream,
				cn -> columnNames.set(cn),
				r -> processRecord(require, context, columnNames.get(), r));
	}

	/**
	 * 1レコード分の組み立て
	 * @param require
	 * @param context
	 * @param columnNames
	 * @param csvRecord
	 */
	private static void processRecord(
			Require require,
			ExecutionContext context,
			List<String> columnNames,
			CsvRecord csvRecord) {
		
		// TODO: データの組み立て
		//val revisedResults = ReviseCsvRecord.revise(require, context, csvRecord, null);
		RevisedDataRecord revisedData = null;
		
		ValidateData.validate(require, context, revisedData);
	}
	
	public static interface Require extends
			ValidateData.ValidateRequire,
			CanonicalizeRevisedData.Require {
		
		Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode settingCode);
	}
}
