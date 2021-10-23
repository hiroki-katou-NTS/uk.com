package nts.uk.cnv.core.dom.conversiontable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.core.dom.constants.Constants;
import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.OnSentence;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;

@Getter
@AllArgsConstructor
public class ConversionSource {

	String sourceId;
	String category;
	String sourceTableName;
	String condition;
	String memo;

	Optional<String> dateColumnName;
	Optional<String> startDateColumnName;
	Optional<String> endDateColumnName;
	Optional<String> dateType;

	List<String> pkColumns;

	public Join getMainJoin(String databaseName, String schema) {
		return Join.createMain(
			new TableFullName(databaseName, schema, this.sourceTableName, Constants.BaseTableAlias));
	}

	public List<OnSentence> getOnSentence(String otherTableAlias, String sourceTableAlias, List<String> pkColumnNames){
		return pkColumnNames.stream()
			.map(col -> new OnSentence(
					new ColumnName(otherTableAlias, col),
					new ColumnName(sourceTableAlias, col),
					Optional.empty()))
			.collect(Collectors.toList());
	}

	public Join getInnerJoin(String databaseName, String schema) {
		List<OnSentence> onSentences = pkColumns.stream()
			.map(pkCol -> new OnSentence(
					new ColumnName("dest", pkCol),
					new ColumnName("source", pkCol),
					Optional.empty()))
			.collect(Collectors.toList());
		return new Join(
				new TableFullName(databaseName, schema, sourceTableName, "source"),
				JoinAtr.InnerJoin,
				onSentences
			);
	}
}
