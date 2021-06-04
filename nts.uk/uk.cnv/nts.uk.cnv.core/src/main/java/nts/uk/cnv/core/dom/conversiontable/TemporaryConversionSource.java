package nts.uk.cnv.core.dom.conversiontable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.cnv.core.dom.conversionsql.ColumnName;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.OnSentence;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;

public class TemporaryConversionSource extends ConversionSource {
	private String tableNameSuffix;

	public TemporaryConversionSource(String sourceId, String category, String sourceTableName, String condition,
			String memo, Optional<String> dateColumnName, Optional<String> startDateColumnName,
			Optional<String> endDateColumnName, Optional<String> dateType, List<String> pkColumns, String tableNameSuffix) {
		super(sourceId, category, sourceTableName, condition, memo, dateColumnName, startDateColumnName, endDateColumnName,
				dateType, pkColumns);
		this.tableNameSuffix = tableNameSuffix;
	}


	@Override
	public Join getMainJoin() {
		return Join.createMain(
			TableFullName.createMainTableName(this.joinTableName()));
	}
	
	@Override
	public Join getInnerJoin() {
		List<OnSentence> onSentences = pkColumns.stream()
			.map(pkCol -> new OnSentence(
					new ColumnName("dest", pkCol),
					new ColumnName("source", pkCol),
					Optional.empty()))
			.collect(Collectors.toList());
		return new Join(
				new TableFullName("", "", this.joinTableName(), "source"),
				JoinAtr.InnerJoin,
				onSentences
			);
	}
	
	private String joinTableName() {
		return String.join("_", this.sourceTableName, this.tableNameSuffix);
	}
}
