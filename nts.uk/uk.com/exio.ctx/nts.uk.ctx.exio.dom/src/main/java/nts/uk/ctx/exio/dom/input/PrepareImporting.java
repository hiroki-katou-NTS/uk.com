package nts.uk.ctx.exio.dom.input;

import java.io.InputStream;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizeRevisedData;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;

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
		
		require.setupWorkspace(context);
		
		// 受入データの組み立て
		setting.assemble(require, context, csvFileStream);
		
		// 編集済みデータの正準化
		val meta = ImportingDataMeta.create(require, context, setting.getAssembly().getAllItemNo());
		CanonicalizeRevisedData.canonicalize(require, context, meta);
	}
	
	public static interface Require extends
			ExternalImportSetting.RequireAssemble,
			CanonicalizeRevisedData.Require {
		
		void setupWorkspace(ExecutionContext context);
		
		Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode settingCode);

		ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo);
	}
}
