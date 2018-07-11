package nts.uk.ctx.exio.dom.exo.execlog.csv;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecLogCSVFileData {
	
	private String fileName;
	private List<String> resultLog;
//	private List<String> errorLog;
	private List<String> header;
	private List<Map<String, Object>> dataSource;
	
}
