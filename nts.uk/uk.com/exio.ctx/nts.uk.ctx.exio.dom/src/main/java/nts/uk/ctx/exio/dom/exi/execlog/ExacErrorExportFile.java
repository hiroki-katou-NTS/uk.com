package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExacErrorExportFile {
	private List<ExacErrorLog> exacErrorLog;
	private List<ExacExeResultLog> exacExeResultLog;
}
