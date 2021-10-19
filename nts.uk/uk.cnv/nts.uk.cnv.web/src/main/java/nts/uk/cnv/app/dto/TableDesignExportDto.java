package nts.uk.cnv.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TableDesignExportDto {
	String tableName;
	String type;
	boolean withComment;
}
