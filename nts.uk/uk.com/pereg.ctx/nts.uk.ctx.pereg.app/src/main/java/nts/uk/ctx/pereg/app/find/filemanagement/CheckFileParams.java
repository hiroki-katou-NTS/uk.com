package nts.uk.ctx.pereg.app.find.filemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class CheckFileParams {
	private String fileId;
	private String fileName;
	private List<String> columnChange;
}
