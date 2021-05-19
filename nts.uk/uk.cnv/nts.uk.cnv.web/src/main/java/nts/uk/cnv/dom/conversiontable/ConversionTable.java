package nts.uk.cnv.dom.conversiontable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.dom.conversionsql.ColumnName;
import nts.uk.cnv.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.dom.conversionsql.FromSentence;
import nts.uk.cnv.dom.conversionsql.InsertSentence;
import nts.uk.cnv.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.dom.conversionsql.TableFullName;
import nts.uk.cnv.dom.conversionsql.WhereSentence;

/**
 * コンバート表
 * @author ai_muto
 */
@Getter
@AllArgsConstructor
public class ConversionTable {
	private DatabaseSpec spec;
	private TableFullName targetTableName;

	private Optional<String> dateColumnName;
	private Optional<String> startDateColumnName;
	private Optional<String> endDateColumnName;

	private List<WhereSentence> whereList;
	private List<OneColumnConversion> conversionMap;

	public ConversionSQL createConversionSql() {
		val newWhereList = new ArrayList<WhereSentence>(whereList);
//		val spec = new SqlServerSpec();		// コンバートコードはSqlServerでのみ動作させる仕様のため直指定
		addPeriodCondition(spec, newWhereList);

		ConversionSQL result = new ConversionSQL(
					new InsertSentence(targetTableName, new ArrayList<>()),
					new ArrayList<>(),
					new FromSentence(Optional.empty(), new ArrayList<>()),
					newWhereList
				);

		for(OneColumnConversion oneColumnConversion : conversionMap) {
			result = oneColumnConversion.apply(result);
		}

		return result;
	}

	private void addPeriodCondition(DatabaseSpec spec, List<WhereSentence> newWhereList) {
		if(dateColumnName.isPresent()) {
			newWhereList.add(
				new WhereSentence(
					new ColumnName("", dateColumnName.get()),
					RelationalOperator.GreagerThanOrEqual,
					Optional.of(new ColumnExpression(Optional.empty(), spec.param(Constants.StartDateParamName)))
				)
			);
			newWhereList.add(
				new WhereSentence(
					new ColumnName("", dateColumnName.get()),
					RelationalOperator.LessThanOrEqual,
					Optional.of(new ColumnExpression(Optional.empty(), spec.param(Constants.EndDateParamName)))
				)
			);
		}

		if(startDateColumnName.isPresent()) {
			newWhereList.add(
				new WhereSentence(
					new ColumnName("", startDateColumnName.get()),
					RelationalOperator.LessThanOrEqual,
					Optional.of(new ColumnExpression(Optional.empty(), spec.param(Constants.EndDateParamName)))
				)
			);
		}

		if(endDateColumnName.isPresent()) {
			newWhereList.add(
				new WhereSentence(
					new ColumnName("", endDateColumnName.get()),
					RelationalOperator.GreagerThanOrEqual,
					Optional.of(new ColumnExpression(Optional.empty(), spec.param(Constants.StartDateParamName)))
				)
			);
		}
	}

}
