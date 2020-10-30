package nts.uk.cnv.dom.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.Join;
import nts.uk.cnv.dom.conversionsql.JoinAtr;
import nts.uk.cnv.dom.conversionsql.OnSentence;
import nts.uk.cnv.dom.conversionsql.SelectSentence;
import nts.uk.cnv.dom.conversionsql.TableName;
import nts.uk.cnv.dom.pattern.manager.ParentJoinPatternManager;
import nts.uk.cnv.dom.service.ConversionInfo;

@Getter
public class ReferencedParentPattern extends ConversionPattern {

	private String category;

	private String parentTable;

	private String parentColumn;

	private List<String> columns;


	public ReferencedParentPattern(ConversionInfo info, String category, String parentTable, String parentColumn, List<String> columns) {
		super(info);
		this.category = category;
		this.parentTable = parentTable;
		this.parentColumn = parentColumn;
		this.columns = columns;
	}

	@Override
	public ConversionSQL apply(ConversionSQL conversionSql) {
		String alias = "parent_" + this.parentColumn;
		Join mapping = this.getMappingTableJoin(info, alias);

		conversionSql.getFrom().addJoin(mapping);

		conversionSql.getSelect().add(SelectSentence.createNotFormat(alias, ParentJoinPatternManager.parentValueColumnName));

		return conversionSql;
	}

	private Join getMappingTableJoin(ConversionInfo info, String alias) {

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
				new TableName(info.getTargetDatabaseName(), info.getTargetSchema(), ParentJoinPatternManager.parentMappingTable, alias),
				JoinAtr.InnerJoin,
				on
			);
	}
}
