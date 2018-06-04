package nts.uk.ctx.at.request.ac.bs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
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
		return lstEmployeeId == null ? new ArrayList<String>() : lstEmployeeId;
	}
	
	@Override
	public List<EmployeeInfoImport> getByListSID(List<String> sIds) {
		return syEmployeePub.getByListSid(sIds).stream().map(x -> {
			return new EmployeeInfoImport(x.getSid(), x.getScd(), x.getBussinessName());
		}).collect(Collectors.toList());
	}

}
