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
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.shr.com.context.AppContexts;

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
		List<String> importiongItem = require.getImportingItem();
		ConversionCodeType cct = context.getMode().getType();
		ConversionSource source = require.getConversionSource(context.getGroupId());
		ConversionSource sourceWithSuffix = editSourceTableName(source);
		List<ConversionTable> conversionTables = require.getConversionTable(sourceWithSuffix, context.getGroupId(), cct);
		
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
	
	private static ConversionSource editSourceTableName(ConversionSource base) {
		String cid = AppContexts.user().companyId();
		// TODO: 正準化テーブルのサフィックスの付与ルールは適正なクラスに委譲予定
		return new ConversionSource(
				base.getSourceId(),
				base.getCategory(),
				base.getSourceTableName() + "_" + cid.replace("-", "_"),
				base.getCondition(),
				base.getMemo(),
				base.getDateColumnName(),
				base.getStartDateColumnName(),
				base.getEndDateColumnName(),
				base.getDateType(),
				base.getPkColumns()
			);
	}

	public interface Require{
		List<String> getImportingItem();
		ConversionSource getConversionSource(int groupId);
		List<ConversionTable> getConversionTable(ConversionSource source, int groupId, ConversionCodeType cct);
		int execute(List<ConversionSQL> conversionSqls);
	}
	
}
