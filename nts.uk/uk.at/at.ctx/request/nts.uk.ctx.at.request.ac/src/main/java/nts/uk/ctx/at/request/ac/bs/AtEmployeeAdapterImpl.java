package nts.uk.ctx.at.request.ac.bs;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class AtEmployeeAdapterImpl implements AtEmployeeAdapter{

	@Inject
	private SyEmployeePub syEmployeePub;
	@Override
	public List<String> getListSid(String sId, GeneralDate baseDate) {
		List<String> lstEmployeeId = syEmployeePub.GetListSid(sId, baseDate);
		return lstEmployeeId;
	}

}
