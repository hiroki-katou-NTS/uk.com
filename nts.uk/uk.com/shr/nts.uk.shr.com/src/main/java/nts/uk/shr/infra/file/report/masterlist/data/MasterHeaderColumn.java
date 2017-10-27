package nts.uk.shr.infra.file.report.masterlist.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterHeaderColumn {

	private String columnId;
	
	private String columnText;
	
	private ColumnTextAlign textAlign;
	
	private String columnFormat;
	
	private boolean display;
}
