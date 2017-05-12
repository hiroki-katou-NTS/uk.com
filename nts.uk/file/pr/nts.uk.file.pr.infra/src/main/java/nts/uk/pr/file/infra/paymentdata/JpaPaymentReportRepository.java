/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.paymentdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDep;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.file.pr.app.export.payment.PaymentReportQuery;
import nts.uk.file.pr.app.export.payment.PaymentReportRepository;
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.file.pr.app.export.payment.data.dto.DepartmentDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class JpaPaymentReportRepository.
 */
@Stateless
public class JpaPaymentReportRepository extends JpaRepository implements PaymentReportRepository {

	/** The Constant FIND_DEPARTMENT_BYCODE. */
	public static final String FIND_DEPARTMENT_BYCODE = "SELECT dep FROM CmnmtDep dep "
		+ "WHERE dep.cmnmtDepPK.companyCode = :companyCode "
		+ " AND dep.cmnmtDepPK.departmentCode = :departmentCode";

	/**
	 * Find department code.
	 *
	 * @param companyCode
	 *            the company code
	 * @param departmentCode
	 *            the department code
	 * @return the optional
	 */
	private Optional<CmnmtDep> findDepartmentCode(String companyCode, String departmentCode) {
		return this.queryProxy().query(FIND_DEPARTMENT_BYCODE, CmnmtDep.class)
			.setParameter("companyCode", companyCode).setParameter("departmentCode", departmentCode)
			.getSingle().map(dep -> {
				return Optional.of(dep);
			}).orElse(Optional.empty());
	}
	
	private List<QstdtPaymentHeader> checkPaymentHeaderLayout(PaymentReportQuery query){
		return new ArrayList<QstdtPaymentHeader>();
	}

	@Override
	public void checkExportData(PaymentReportQuery query) {

	}

	/**
	 * To department dto.
	 *
	 * @param dep
	 *            the dep
	 * @return the department dto
	 */
	private DepartmentDto toDepartmentDto(CmnmtDep dep) {
		DepartmentDto dto = new DepartmentDto();
		dto.setDepartmentCode(dep.getCmnmtDepPK().getDepartmentCode());
		dto.setDepartmentName(dep.getDepName());
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.file.pr.app.export.payment.PaymentReportRepository#findData(nts.uk
	 * .file.pr.app.export.payment.PaymentReportQuery)
	 */
	@Override
	public PaymentReportData findData(PaymentReportQuery query) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code

		String companyCode = loginUserContext.companyCode();
		PaymentReportData reportData = new PaymentReportData();

		// find department
		Optional<CmnmtDep> department = this.findDepartmentCode(companyCode, "00001");
		if (department.isPresent()) {
			reportData.setDepartmentInfo(this.toDepartmentDto(department.get()));
		}
		return reportData;
	}

}
