package nts.uk.cnv.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetUkTablesResultDto {
	String tableId;
	String tableName;
}
