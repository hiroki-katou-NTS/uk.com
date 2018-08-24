package nts.uk.ctx.exio.dom.exo.execlog.csv;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ExecLogFileDataCSV {

	private String fileName;
	private List<List<String>> resultLogs;
	private List<String> headers;
	private List<Map<String, Object>> dataSource;
	
	public ExecLogFileDataCSV(String fileName, List<List<String>> resultLogs, List<String> headers,
			List<Map<String, Object>> dataSource) {
		this.fileName = fileName;
		this.resultLogs = resultLogs;
		this.headers = headers;
		this.dataSource = dataSource;
	}
}
