package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         NRWeb照会のパラメーターと会社IDと社員ID
 */
@AllArgsConstructor
@Getter
public class NRWebQuerySidDateParameter {

	//会社ID 
	private String cid;
	
	//	社員ID
	private String sid;
	
	//	NRWeb照会パラメータークエリ
	private NRWebQueryParameter nrWebQuery;
}
