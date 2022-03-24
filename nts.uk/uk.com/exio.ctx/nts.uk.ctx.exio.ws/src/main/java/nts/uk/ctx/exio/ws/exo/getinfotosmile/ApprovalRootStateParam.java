package nts.uk.ctx.exio.ws.exo.getinfotosmile;

import java.util.Date;

import lombok.Data;

@Data
public class ApprovalRootStateParam {
	private String employeeCd;
	private Date startDate;
	private Date endDate;
	private Integer yearMonth;
	private Integer closureID;
	private int closureDay;
	private Boolean lastDayOfMonth;
	private Date baseDate;
}
