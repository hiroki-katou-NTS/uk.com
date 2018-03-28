package nts.uk.ctx.exio.dom.exi.execlog.csv;

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
	private List<String> condImport;
	private List<String> dateTime;
	private List<String> totalCount;
	private List<String> normalCount;
	private List<String> errorCount;
	private List<String> headers;
	private List<Map<String, Object>> datas;
	
}
