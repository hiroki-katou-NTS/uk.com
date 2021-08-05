package nts.uk.cnv.core.dom.conversiontable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;
import nts.uk.cnv.core.dom.constants.Constants;
import nts.uk.cnv.core.dom.conversionsql.ColumnExpression;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.ConversionInsertSQL;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversionsql.ConversionUpdateSQL;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.RelationalOperator;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversionsql.WhereSentence;

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

	private boolean removeDuplicate;

	public ConversionSQL createConversionSql() {
		val newWhereList = new ArrayList<WhereSentence>(whereList);
		addPeriodCondition(spec, newWhereList);

		ConversionSQL result = new ConversionInsertSQL(targetTableName, newWhereList);

		for(OneColumnConversion oneColumnConversion : conversionMap) {
			result = oneColumnConversion.apply(targetTableName.getAlias(), result, removeDuplicate);
		}

		return result;
	}

	public ConversionSQL createUpdateConversionSql() {
		val newWhereList = new ArrayList<WhereSentence>(whereList);
		addPeriodCondition(spec, newWhereList);

		ConversionSQL result = new ConversionUpdateSQL(targetTableName, newWhereList);
		result.addJoin(Join.createMain(targetTableName));

		for(OneColumnConversion oneColumnConversion : conversionMap) {
			result = oneColumnConversion.apply(targetTableName.getAlias(), result, removeDuplicate);
		}

		return result;
	}

	private void addPeriodCondition(DatabaseSpec spec, List<WhereSentence> newWhereList) {
		if(dateColumnName.isPresent()) {
			newWhereList.add(
				new WhereSentence(
					new ColumnName("", dateColumnName.get()),
					RelationalOperator.GreagerThanOrEqual,
					Optional.of(new ColumnExpression(spec.param(Constants.StartDateParamName)))
				)
			);
			newWhereList.add(
				new WhereSentence(
					new ColumnName("", dateColumnName.get()),
					RelationalOperator.LessThanOrEqual,
					Optional.of(new ColumnExpression(spec.param(Constants.EndDateParamName)))
				)
			);
		}

		if(startDateColumnName.isPresent()) {
			newWhereList.add(
				new WhereSentence(
					new ColumnName("", startDateColumnName.get()),
					RelationalOperator.LessThanOrEqual,
					Optional.of(new ColumnExpression(spec.param(Constants.EndDateParamName)))
				)
			);
		}

		if(endDateColumnName.isPresent()) {
			newWhereList.add(
				new WhereSentence(
					new ColumnName("", endDateColumnName.get()),
					RelationalOperator.GreagerThanOrEqual,
					Optional.of(new ColumnExpression(spec.param(Constants.StartDateParamName)))
				)
			);
		}
	}

}
