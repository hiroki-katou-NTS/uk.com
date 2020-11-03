package nts.uk.screen.at.ws.kcp015;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kcp015.JudgmentVacationCls;
import nts.uk.screen.at.app.kcp015.KCP015Dto;

/**
 * 
 * @author laitv
 *
 */
@Path("screen/at/kcp015")
@Produces("application/json")
public class KCP015WebService extends WebService{

	@Inject
	private JudgmentVacationCls judgmentVacationCls;
	
	@POST
	@Path("get")
	public KCP015Dto getDataSetting(){
		KCP015Dto data = judgmentVacationCls.getData();
		return data;
	}
}
