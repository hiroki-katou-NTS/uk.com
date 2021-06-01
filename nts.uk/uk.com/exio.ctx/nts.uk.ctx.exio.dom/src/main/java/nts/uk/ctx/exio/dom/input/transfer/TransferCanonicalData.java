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
			List<String> enabledColumns = require.getEnabledColumnList(conversionTable.getTargetTableName().getName());
			
			ConversionTable filteredConversionTable = conversionTable.filterColumns(enabledColumns);
			
			ConversionSQL conversionSql;
			if(meta.getContext().getMode().getType() == ConversionCodeType.INSERT) {
				conversionSql = filteredConversionTable.createConversionSql();
			}
			else {
				conversionSql = filteredConversionTable.createUpdateConversionSql();
			}
			int count = require.execute(conversionSql);
		}
	}
	
	public interface Require{
		CanonicalizedDataMeta getMetaData();
		List<ConversionTable> getConversionTable(int groupId);
		List<String> getEnabledColumnList(String tableName);
		int execute(ConversionSQL conversionSql);
	}
	
}
