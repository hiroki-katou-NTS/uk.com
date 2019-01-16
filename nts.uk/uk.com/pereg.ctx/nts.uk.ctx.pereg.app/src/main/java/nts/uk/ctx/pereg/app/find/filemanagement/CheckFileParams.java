package nts.uk.ctx.pereg.app.find.filemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpHead;
@Getter
@AllArgsConstructor
public class CheckFileParams {
	private String fileId;
	private String fileName;
	private String categoryId;
	private List<String> columnChange;
	private List<GridEmpHead> headDatas;
}
