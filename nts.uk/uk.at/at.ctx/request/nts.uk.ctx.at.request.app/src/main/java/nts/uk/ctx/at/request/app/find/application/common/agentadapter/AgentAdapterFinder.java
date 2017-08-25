package nts.uk.ctx.at.request.app.find.application.common.agentadapter;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.agentadapter.AgentAdapterDto;
/**
 * ドメインモデル「代行承認」を取得する
 * @author dudt
 *
 */
@Stateless
public class AgentAdapterFinder {
	public List<AgentAdapterDto> getAgenInfor(String requestId, GeneralDate startDate, GeneralDate endDate){
		List<AgentAdapterDto> lstAgen =  new ArrayList<>();
		return lstAgen;
	}
}
