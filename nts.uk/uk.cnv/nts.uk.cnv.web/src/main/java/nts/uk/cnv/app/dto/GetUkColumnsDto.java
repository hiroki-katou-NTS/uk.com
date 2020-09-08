package nts.uk.cnv.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetUkColumnsDto {
	int id;
	String columnName;
	String dataType;
}
