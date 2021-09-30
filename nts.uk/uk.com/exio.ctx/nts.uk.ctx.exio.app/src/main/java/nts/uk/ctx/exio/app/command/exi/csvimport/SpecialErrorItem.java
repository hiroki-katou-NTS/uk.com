package nts.uk.ctx.exio.app.command.exi.csvimport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialErrorItem {
	private String tableName;
	private String columnName;
	private Object value;
}
