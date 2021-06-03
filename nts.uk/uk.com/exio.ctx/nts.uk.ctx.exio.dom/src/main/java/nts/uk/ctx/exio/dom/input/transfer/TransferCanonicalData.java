package nts.uk.ctx.exio.dom.input.transfer;

import java.util.List;

import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMeta;

/**
 * 正準化データを移送する
 * @author ai_muto
 */
public class TransferCanonicalData {
	public static void transfer(Require require) {
		CanonicalizedDataMeta meta = require.getMetaData();
		List<ConversionTable> conversionTables = require.getConversionTable(meta.getContext().getGroupId());
		
		for(ConversionTable conversionTable : conversionTables) {
			// 受入項目の列名リストを元に移送する列をフィルタ
			ConversionTable filteredConversionTable = conversionTable.filterColumns(meta.getImportingItemNames());
			
			ConversionSQL conversionSql;
			// TODO: Insert & Update両方のモードは未対応
			if(meta.getContext().getMode().getType() == ConversionCodeType.INSERT) {
				conversionSql = filteredConversionTable.createConversionSql();
			}
			else {
				conversionSql = filteredConversionTable.createUpdateConversionSql();
			}
			// TODO: 移送前のデータ削除処理の組み込み
			
			// 移送処理の実行
			int count = require.execute(conversionSql);
			// 処理件数のチェック…？
		}
	}
	
	public interface Require{
		CanonicalizedDataMeta getMetaData();
		List<ConversionTable> getConversionTable(int groupId);
		int execute(ConversionSQL conversionSql);
	}
	
}
