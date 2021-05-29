package nts.uk.cnv.core.dom.conversiontable;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.core.dom.conversionsql.Join;

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

	public Join getJoin() {
		return Join.createMain(this.sourceTableName);
	}
}
