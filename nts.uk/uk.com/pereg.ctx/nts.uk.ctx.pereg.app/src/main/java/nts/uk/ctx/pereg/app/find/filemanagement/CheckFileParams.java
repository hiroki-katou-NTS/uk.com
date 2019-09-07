package nts.uk.ctx.pereg.app.find.filemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpHead;
@Getter
@Setter
@AllArgsConstructor
public class CheckFileParams {
	private String fileId;
	private String fileName;
	private String categoryId;
	private int modeUpdate;
	private List<GridEmpHead> columnChange;
	private List<String> sids;
	private GeneralDate baseDate;
}
