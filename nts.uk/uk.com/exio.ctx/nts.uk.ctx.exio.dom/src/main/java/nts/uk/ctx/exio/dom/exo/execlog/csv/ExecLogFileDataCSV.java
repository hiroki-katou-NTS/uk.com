package nts.uk.ctx.exio.dom.exo.execlog.csv;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ExecLogFileDataCSV {

	private String fileName;
	private List<String> resultLog;
	private List<String> headers;
	private List<Map<String, Object>> dataSource;
	
	public ExecLogFileDataCSV(String fileName, List<String> resultLog, List<String> headers,
			List<Map<String, Object>> dataSource) {
		this.fileName = fileName;
		this.resultLog = resultLog;
		this.headers = headers;
		this.dataSource = dataSource;
	}
}
