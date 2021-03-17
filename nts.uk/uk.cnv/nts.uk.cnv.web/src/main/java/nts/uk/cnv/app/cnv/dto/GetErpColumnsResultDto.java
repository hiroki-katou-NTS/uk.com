package nts.uk.cnv.app.cnv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetErpColumnsResultDto {
	String id;
	String columnName;
	String dataType;
}
