package nts.uk.ctx.exio.app.find.exo.exechist;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ExecHistSearchParam {
	private GeneralDate startDate;
	private GeneralDate endDate;
	private String condSetCd;
	private List<Integer> exOutCtgIdList;
}
