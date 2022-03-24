package nts.uk.ctx.exio.ws.exo.getinfotosmile;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TargetEmployeeParam {
	private List<String> employmentCd;
	private Date startDate;
	private Date endDate;
}
