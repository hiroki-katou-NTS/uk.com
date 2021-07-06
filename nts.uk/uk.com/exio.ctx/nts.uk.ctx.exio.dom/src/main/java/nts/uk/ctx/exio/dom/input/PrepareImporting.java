package nts.uk.ctx.exio.dom.input;

import static java.util.stream.Collectors.*;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeRevisedData;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
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

		val assembly = require.getAssemblyMethod(context.getCompanyId(), context.getExternalImportCode())
				.orElseThrow(() -> new RuntimeException("組立方法が取得できない: " + context.toString()));
		
		require.setupWorkspace(context);
		
		// 受入データの組み立て
		val meta = assembleImportingData(require, context, csvFileStream, setting, assembly);
		
		// 編集済みデータの正準化
		CanonicalizeRevisedData.canonicalize(require, context, meta);
	}

	/**
	 * 受入データを組み立てる
	 * @param require
	 * @param csvFileStream
	 * @param setting
	 * @param context
	 */
	private static ImportingDataMeta assembleImportingData(
			Require require,
			ExecutionContext context,
			InputStream csvFileStream,
			ExternalImportSetting setting,
			ExternalImportAssemblyMethod assembly) {
		
		val parser = setting.getCsvFileInfo().createParser();

		parser.parse(
				csvFileStream,
				cn -> { }, // 今のところヘッダ行を取得する必要が無い
				r -> processRecord(require, context, assembly, r));
		
		return ImportingDataMeta.create(require, context, assembly.getAllItemNo());
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
			ExternalImportAssemblyMethod assembly,
			CsvRecord csvRecord) {
		
		val optRevisedData = assembly.assembleExternalImportData(require, context, csvRecord);
		if(!optRevisedData.isPresent()) {
			// データの組み立て結果が空の場合
			return;
		}
		
		val revisedData = optRevisedData.get();
		
		ValidateData.validate(require, context, revisedData);
		
		require.save(context, revisedData);
	}

	
	public static interface Require extends
	ExternalImportAssemblyMethod.Require, 
			ValidateData.ValidateRequire,
			CanonicalizeRevisedData.Require {
		
		void setupWorkspace(ExecutionContext context);
		
		Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode settingCode);

		Optional<ExternalImportAssemblyMethod> getAssemblyMethod(String companyId, ExternalImportCode settingCode);
		
		ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo);
		
		void save(ExecutionContext context, RevisedDataRecord revisedDataRecord);
	}
}
