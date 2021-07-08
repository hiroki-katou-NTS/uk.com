package nts.uk.ctx.exio.dom.input.transfer;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.WhereSentence;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.core.dom.conversiontable.pattern.NotChangePattern;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.WorkspaceTableName;

/**
 * 正準化データを移送する
 * @author ai_muto
 */
public class TransferCanonicalData {
	
	public static AtomTask transferAll(Require require, ExecutionContext context) {
		return transfer(require, context, Collections.emptyList());
	}
	
	public static AtomTask transferByEmployee(Require require, ExecutionContext context, String employeeId) {
		
		val where = WhereSentence.equal("SID", ColumnExpression.stringLiteral(employeeId));
		return transfer(require, context, Arrays.asList(where));
	}
	
	private static AtomTask transfer(Require require, ExecutionContext context, List<WhereSentence> wherelist) {
		
		List<String> importingItem = new ArrayList<>(require.getImportingDataMeta(context).getItemNames());
		importingItem.add("CONTRACT_CD");
		importingItem.add("CID");
		
		ConversionCodeType cct = context.getMode().getType();
		
		ImportingGroup importingGroup = require.getImportingGroup(context.getGroupId());
		
		ConversionSource source = require.getConversionSource(importingGroup.getName());
		ConversionSource sourceWithSuffix = editSourceTableName(source, context, importingGroup.getName());
		List<ConversionTable> conversionTables = require.getConversionTable(sourceWithSuffix, importingGroup.getName(), cct);
		
		List<ConversionSQL> conversionSql = new ArrayList<>();
		for(ConversionTable conversionTable : conversionTables) {
			wherelist.stream().forEach(where -> {
				conversionTable.getWhereList().add(where);
			});
			
			// 受入項目の列名リストを元に移送する列をフィルタ
			ConversionTable filteredConversionTable = new ConversionTable(
					conversionTable.getSpec(),
					conversionTable.getTargetTableName(),
					conversionTable.getDateColumnName(),
					conversionTable.getStartDateColumnName(),
					conversionTable.getEndDateColumnName(),
					conversionTable.getWhereList(),
					conversionTable.getConversionMap().stream()
						.filter(m -> importingItem.contains(((NotChangePattern) m.getPattern()).getSourceColumn()))
						.collect(toList())
					);
			
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
	
	private static ConversionSource editSourceTableName(ConversionSource base, ExecutionContext context, String groupName) {
		
		val tableName = new WorkspaceTableName(context, groupName);
		
		// TODO: 正準化テーブルのサフィックスの付与ルールは適正なクラスに委譲予定
		return new ConversionSource(
				base.getSourceId(),
				base.getCategory(),
				tableName.asCanonicalized(),
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
		ImportingDataMeta getImportingDataMeta(ExecutionContext context);
		ImportingGroup getImportingGroup(ImportingGroupId groupId);
		ConversionSource getConversionSource(String groupName);
		List<ConversionTable> getConversionTable(ConversionSource source, String groupName, ConversionCodeType cct);
		int execute(List<ConversionSQL> conversionSqls);
	}
	
}
