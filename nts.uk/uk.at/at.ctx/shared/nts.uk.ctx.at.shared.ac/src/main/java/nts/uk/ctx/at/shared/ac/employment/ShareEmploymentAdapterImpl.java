/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ac.employment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmpCdNameImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.bs.employee.pub.employment.EmpCdNameExport;
import nts.uk.ctx.bs.employee.pub.employment.SEmpHistExport;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

/**
 * The Class ShareEmploymentAdapterImpl.
 */
@Stateless
public class ShareEmploymentAdapterImpl implements ShareEmploymentAdapter{
	
	/** The employment. */
	@Inject
	public SyEmploymentPub employment;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter#findAll(java.lang.String)
	 */
	@Override
	public List<EmpCdNameImport> findAll(String companyId) {
		List<EmpCdNameExport> data = employment.findAll(companyId);
		return data.stream().map(x -> {
			return new EmpCdNameImport(x.getCode(), x.getName());
		}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter
	 * #findByEmpCodes(java.lang.String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<BsEmploymentHistoryImport> findEmploymentHistory(String companyId, String employeeId, GeneralDate baseDate) {
		Optional<SEmpHistExport> empHistOpt = employment.findSEmpHistBySid(companyId, employeeId, baseDate);
		if (empHistOpt.isPresent()) {
			SEmpHistExport empHist = empHistOpt.get();
			return Optional.of(new BsEmploymentHistoryImport(empHist.getEmployeeId(), empHist.getEmploymentCode(),
					empHist.getEmploymentName(), empHist.getPeriod()));
		}
		return Optional.empty();
	}
	
}
