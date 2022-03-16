package nts.uk.ctx.exio.dom.input.setting;

import java.io.InputStream;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingMapping;
import nts.uk.ctx.exio.dom.input.validation.ValidateData;

/**
 * ドメイン受入設定
 */
@Getter
@AllArgsConstructor
public class DomainImportSetting implements DomainAggregate {
	/** 受入ドメインID */
	private ImportingDomainId domainId;

	/** 受入モード */
	@Setter
	private ImportingMode importingMode;

	/** 組立方法 */
	private ExternalImportAssemblyMethod assembly;

	/**
	 * マッピングを更新する
	 * @param itemList
	 */

	public void merge(RequireMerge require, List<Integer> itemList, ExternalImportCode settingCode, ImportingDomainId domainID) {
		
		val mappingRequire = new ImportingMapping.RequireMerge() {
			@Override
			public void deleteReviseItems(List<Integer> itemNos) {
				require.deleteReviseItems(settingCode, domainID, itemNos);
			}
		};
		
		this.assembly.merge(mappingRequire, itemList);
	}
	
	public static interface RequireMerge {
		void deleteReviseItems(ExternalImportCode settingCode, ImportingDomainId domainId, List<Integer> itemNos);
	}
	
	public static interface RequireChangeDomain {
		void deleteReviseItems(ExternalImportCode settingCode);
	}

	/**
	 * 編集し、組み立てる
	 * @param require
	 * @param context
	 * @param csvFileInfo
	 * @param csvFileStream
	 * @return 編集に成功した行数
	 */
	public int assemble(RequireAssemble require, ExecutionContext context, ExternalImportCsvFileInfo csvFileInfo,InputStream csvFileStream) {
		return csvFileInfo.parse(csvFileStream, r -> processRecord(require, context, r));
	}

	/**
	 * 1レコード分の組み立て
	 * @param require
	 * @param context
	 * @param csvRecord
	 * @return 処理に成功したか
	 */
	private boolean processRecord(
			RequireAssemble require,
			ExecutionContext context,
			CsvRecord csvRecord) {

		val optRevisedData = assembly.assemble(require, context, csvRecord);
		if(!optRevisedData.isPresent()) {
			// データの組み立て結果が空の場合
			return false;
		}

		val revisedData = optRevisedData.get();

		ValidateData.validate(require, context, revisedData)
			.ifRight(validatedValue -> require.save(context, validatedValue))
			.ifLeft(errors -> errors.forEach(error -> {
				require.add(ExternalImportError.of(revisedData.getRowNo(), context.getDomainId(), error));
			}));
	}

	public static interface RequireAssemble extends
			ExternalImportAssemblyMethod.Require,
			ValidateData.ValidateRequire {

		void save(ExecutionContext context, RevisedDataRecord revisedDataRecord);
	}

	public ExecutionContext executionContext(String companyId, ExternalImportCode code) {
		return new ExecutionContext(
				companyId,
				code.v(),
				domainId,
				importingMode);
	}
}
