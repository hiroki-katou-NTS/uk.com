package nts.uk.ctx.sys.assist.dom.deletedata;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Result {
	private ResultState state;
	private File file;
	private String fileId;
}
