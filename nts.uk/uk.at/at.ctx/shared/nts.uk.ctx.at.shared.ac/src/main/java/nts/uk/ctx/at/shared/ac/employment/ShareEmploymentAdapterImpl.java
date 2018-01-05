/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ac.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmpCdNameImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.bs.employee.pub.employment.EmpCdNameExport;
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
	 * @see nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter#findByEmpCodes(java.lang.String, java.util.List)
	 */
	@Override
	public List<BsEmploymentImport> findByEmpCodes(String companyId, List<String> empCodes) {
		return new ArrayList<>();
//		List<ShEmploymentExport> empExport = this.employment.findByEmpCodes(companyId, empCodes);
//		return empExport.stream().map(item -> {
//			return new BsEmploymentImport(item.getCompanyId(), item.getEmploymentCode(), item.getEmploymentName(),
//					item.getEmpExternalCode(), item.getMemo());
//		}).collect(Collectors.toList());
	}
}
