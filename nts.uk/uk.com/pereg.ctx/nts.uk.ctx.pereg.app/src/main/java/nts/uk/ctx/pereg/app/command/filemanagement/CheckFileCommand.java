package nts.uk.ctx.pereg.app.command.filemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class CheckFileCommand {
	private String fileId;
	private String fileName;
	private List<String> columnChange;
}
