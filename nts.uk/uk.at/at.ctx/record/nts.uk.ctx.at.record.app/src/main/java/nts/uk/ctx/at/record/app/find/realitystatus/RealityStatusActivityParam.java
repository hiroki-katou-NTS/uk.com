package nts.uk.ctx.at.record.app.find.realitystatus;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author dat.lh
 *
 */
@Value
public class RealityStatusActivityParam {
	private String startDate;
	private String endDate;
	private boolean isConfirmData;
	private List<String> listWorkplaceId;
	private List<String> listEmpCd;
}
