package nts.uk.ctx.exio.dom.input.transfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.core.dom.conversionsql.WhereSentence;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.ctx.exio.dom.input.ExecutionContext;

/**
 * 正準化データを移送する
 * @author ai_muto
 */
public class TransferCanonicalData {
	public static AtomTask transferAll(Require require, ExecutionContext context) {
		return transfer(require, context, Collections.emptyList());
	}
	
	public static AtomTask transferWithWhere(Require require, ExecutionContext context, String columnName, String value) {
		WhereSentence whereSentence = new WhereSentence(
				new ColumnName("", columnName),
				RelationalOperator.Equal,
				Optional.of(new ColumnExpression("'" + value + "'"))
			);
		
		return transfer(require, context, Arrays.asList(whereSentence));
	}
	
	private static AtomTask transfer(Require require, ExecutionContext context, List<WhereSentence> wherelist) {
		List<String> importiongItem = require.getImportiongItem();
		ConversionCodeType cct = context.getMode().getType();
		List<ConversionTable> conversionTables = require.getConversionTable(context.getGroupId(), cct);
		
		List<ConversionSQL> conversionSql = new ArrayList<>();
		for(ConversionTable conversionTable : conversionTables) {
			wherelist.stream().forEach(where -> {
				conversionTable.getWhereList().add(where);
			});
			
			// 受入項目の列名リストを元に移送する列をフィルタ
			ConversionTable filteredConversionTable = conversionTable.filterColumns(importiongItem);
			
			// TODO: Insert & Update両方のモードは未対応
			if(context.getMode().getType() == ConversionCodeType.INSERT) {
				conversionSql.add(filteredConversionTable.createConversionSql());
			}
			else {
				conversionSql.add(filteredConversionTable.createUpdateConversionSql());
			}
		}

		// 移送処理の実行
		return AtomTask.of(() -> {
			require.execute(conversionSql);
		});
	}
	
	public interface Require{
		List<String> getImportiongItem();
		List<ConversionTable> getConversionTable(int groupId, ConversionCodeType cct);
		int execute(List<ConversionSQL> conversionSqls);
	}
	
}
