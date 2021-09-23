package nts.uk.cnv.app.dto;

import lombok.Value;

@Value
public class GetUkColumnsParamDto {
	String category;
	String tableName;
	int recordNo;
}
