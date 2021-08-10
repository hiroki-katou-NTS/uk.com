package nts.uk.cnv.core.dom.conversiontable.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.cnv.core.dom.constants.Constants;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.OnSentence;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.pattern.manager.ParentJoinPatternManager;

@Getter
public class ReferencedParentPattern extends ConversionPattern {
	private ConversionInfo info;

	private String category;

	private String parentTable;

	private String parentColumn;

	private List<String> columns;


	public ReferencedParentPattern(ConversionInfo info, String category, String parentTable, String parentColumn, List<String> columns) {
		this.info = info;
		this.category = category;
		this.parentTable = parentTable;
		this.parentColumn = parentColumn;
		this.columns = columns;
	}

	@Override
	public ConversionSQL apply(ColumnName column, ConversionSQL conversionSql, boolean removeDuplicate) {
		String alias = "parent_" + this.parentColumn;
		Join mapping = this.getMappingTableJoin(alias);

		conversionSql.addJoin(mapping);

		ColumnExpression expression = new ColumnExpression(alias, ParentJoinPatternManager.parentValueColumnName);
		conversionSql.add(column, expression);
		if(removeDuplicate) {
			conversionSql.addGroupingColumn(expression);
		}

		return conversionSql;
	}

	private Join getMappingTableJoin(String alias) {

		List<OnSentence> on  = new ArrayList<>();
		on.add(new OnSentence(new ColumnName(alias, ParentJoinPatternManager.pk[0]), new ColumnName("", "'" + category + "'"), Optional.empty()));
		on.add(new OnSentence(new ColumnName(alias, ParentJoinPatternManager.pk[1]), new ColumnName("", "'" + parentTable + "'"), Optional.empty()));
		on.add(new OnSentence(new ColumnName(alias, ParentJoinPatternManager.pk[2]), new ColumnName("", "'" + parentColumn + "'"), Optional.empty()));
		for(int i = 0; i < columns.size(); i++) {
			on.add(new OnSentence(
					new ColumnName(alias, ParentJoinPatternManager.getSourcePkName(i)),
					new ColumnName(Constants.BaseTableAlias, columns.get(i)),
					Optional.empty()
				));
		}

		return new Join (
				ParentJoinPatternManager.mappingTableName(info, alias),
				JoinAtr.InnerJoin,
				on
			);
	}
}
