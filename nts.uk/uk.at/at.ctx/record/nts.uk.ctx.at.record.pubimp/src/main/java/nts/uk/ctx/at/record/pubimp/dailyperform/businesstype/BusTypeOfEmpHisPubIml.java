package nts.uk.ctx.at.record.pubimp.dailyperform.businesstype;

import java.util.List;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyperform.businesstype.BusTypeOfEmpHimPub;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpService;

public class BusTypeOfEmpHisPubIml implements BusTypeOfEmpHimPub {

	@Inject
	private BusinessTypeEmpService service;

	@Override
	public List<BusinessTypeOfEmployee> getData(String sId, GeneralDate baseDate) {
		return this.service.getData(sId, baseDate);
	}

}
