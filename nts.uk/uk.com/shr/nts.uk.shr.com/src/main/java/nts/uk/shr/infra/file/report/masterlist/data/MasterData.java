package nts.uk.shr.infra.file.report.masterlist.data;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MasterData {

	private Map<String, Object> datas;
	
	private List<MasterData> childGroup;
	
	private String group;
}
