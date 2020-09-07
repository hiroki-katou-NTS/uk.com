package nts.uk.cnv.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ImportFromFileResult {
	int successCount;
	List<String> erroMessages;
	public void increment() {
		this.successCount++;
	}
}
