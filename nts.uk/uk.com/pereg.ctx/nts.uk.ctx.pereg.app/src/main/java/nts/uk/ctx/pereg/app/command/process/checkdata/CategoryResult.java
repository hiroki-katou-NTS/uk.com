package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

@Data
@AllArgsConstructor
public class CategoryResult {
	
	private List<CategoryInfo> listCtg;

	private int peopleCount;
	
	private GeneralDateTime startDateTime;
	
	private GeneralDateTime endDateTime;

}
