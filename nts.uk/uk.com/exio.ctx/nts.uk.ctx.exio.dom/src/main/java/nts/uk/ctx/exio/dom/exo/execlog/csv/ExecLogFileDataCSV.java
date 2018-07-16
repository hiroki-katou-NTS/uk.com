package nts.uk.ctx.exio.dom.exo.execlog.csv;

import java.util.List;

import lombok.Data;

@Data
public class ExecLogFileDataCSV {

	private String fileName;
	private List<String> resultLog;
	private List<String> dataSource;

	public ExecLogFileDataCSV(String fileName, List<String> resultLog, List<String> dataSource) {
		this.fileName = fileName;
		this.resultLog = resultLog;
		this.dataSource = dataSource;
	}

}
