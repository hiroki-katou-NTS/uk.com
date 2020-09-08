package nts.uk.cnv.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetUkTablesDto {
	String tableId;
	String tableName;
}
