package nts.uk.cnv.screen.app.query.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class SourceDto {
	String sourceId;
	String category;
	String erpTableName;
	String whereCondition;
	String memo;
	String dateColumnName;
	String startDateColumnName;
	String endDateColumnName;
	String dateType;
}
