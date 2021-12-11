package nts.uk.cnv.screen.app.query.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class RecordDto {
	int recordNo;
	String tableName;
	String explanation;
	String whereCondition;
	String sourceId;
	boolean isRemoveDuplicate;
}
