/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.detailpaymentsalary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.department.DepartmentRepository;
import nts.uk.ctx.basic.infra.entity.report.PbsmtPersonBase;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItem;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreDetail;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreHead;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHead;
import nts.uk.file.pr.app.export.detailpaymentsalary.PaymentSalaryReportRepository;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.DepartmentDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.query.PaymentSalaryQuery;

/**
 * The Class JpaPaymentSalaryReportRepository.
 */
@Stateless
public class JpaPaymentSalaryReportRepository extends JpaRepository implements PaymentSalaryReportRepository {

	/** The repository. */
	@Inject
	private DepartmentRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.file.pr.app.export.detailpaymentsalary.
	 * PaymentSalaryReportRepository#exportPDFPaymentSalary(nts.uk.file.pr.app.
	 * export.detailpaymentsalary.query.PaymentSalaryQuery)
	 */
	public PaymentSalaryReportData exportPDFPaymentSalary(String companyCode, PaymentSalaryQuery query) {
		PaymentSalaryReportData data = new PaymentSalaryReportData();
		QlsptPaylstFormHead headerForm = this.findHeaderFrom(companyCode, query.getOutputSettingCode());
		QcamtItem item = this.findItem(companyCode);
		QlsptPaylstAggreHead payHead = this.findAggreHeader(companyCode);
		if (CollectionUtil.isEmpty(payHead.getQlsptPaylstAggreDetailList())) {
			throw new BusinessException("ER010");
		}
		List<PbsmtPersonBase> persons = this.findAllPerson(query.getEmployeeCodes());
		if (CollectionUtil.isEmpty(persons)) {
			throw new BusinessException("ER010");
		}
		

		return data;
	}

	/**
	 * Sort employees.
	 *
	 * @param companyCode
	 *            the company code
	 * @param employeeCodes
	 *            the employee codes
	 * @return the list
	 */
	public List<EmployeeDto> sortEmployees(String companyCode, List<String> employeeCodes) {

		EntityManager em = this.getEntityManager();
		String query = "SELECT h FROM QstdtPaymentHeader h ";
		// WHERE h.employmentCode IN :employeeCodes

		TypedQuery<QstdtPaymentHeader> typed = em.createQuery(query, QstdtPaymentHeader.class);
		// typed.setParameter("employeeCodes", employeeCodes);
		List<QstdtPaymentHeader> headers = typed.getResultList();

		return headers.stream().map(header -> {
			EmployeeDto dto = new EmployeeDto();
			dto.setCode(header.employeeCode);
			dto.setName(header.employeeName);

			DepartmentDto departmentDto = new DepartmentDto();
			departmentDto.setCode(header.departmentCode);
			departmentDto.setName(header.departmentName);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Export department history.
	 *
	 * @param companyCode
	 *            the company code
	 * @param historyId
	 *            the history id
	 * @return the list
	 */
	public List<DepartmentDto> exportDepartmentHistory(String companyCode, String historyId) {
		// find all department
		List<Department> departments = this.repository.findAllByHistory(companyCode, historyId);
		return departments.stream().map(department -> this.convertDepartmentDto(department))
			.collect(Collectors.toList());
	}

	/**
	 * Convert department dto.
	 *
	 * @param department
	 *            the department
	 * @return the department dto
	 */
	private DepartmentDto convertDepartmentDto(Department department) {
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setCode(department.getDepartmentCode().v());
		departmentDto.setName(department.getDepartmentName().v());
		departmentDto.setDepLevel(department.getHierarchyCode().v().length() / 3);
		departmentDto.setDepPath(department.getHierarchyCode().v());
		return departmentDto;
	}

	/**
	 * Find header from.
	 *
	 * @param companyCode
	 *            the company code
	 * @param fromId
	 *            the from id
	 * @return the qlspt paylst form head
	 */
	private QlsptPaylstFormHead findHeaderFrom(String companyCode, String fromId) {
		EntityManager em = this.getEntityManager();
		String queryString = "SELECT h FROM QlsptPaylstFormHead h "
			+ "WHERE h.qlsptPaylstFormHeadPK.ccd = :companyCode "
			+ "AND h.qlsptPaylstFormHeadPK.formCd = :formCd ";

		TypedQuery<QlsptPaylstFormHead> typedQuery = em.createQuery(queryString, QlsptPaylstFormHead.class);
		typedQuery.setParameter("companyCode", companyCode);
		typedQuery.setParameter("formCd", fromId);
		return typedQuery.getSingleResult();
	}

	/**
	 * Find item.
	 *
	 * @param companyCode
	 *            the company code
	 * @param query
	 *            the query
	 * @return the qcamt item
	 */
	private QcamtItem findItem(String companyCode) {
		EntityManager em = this.getEntityManager();
		String queryString = "SELECT h FROM QcamtItem h " + "WHERE h.qcamtItemPK.ccd = :companyCode "
			+ "AND h.qcamtItemPK.ctgAtr = 0 AND h.qcamtItemPK.itemCd = 0 ";

		TypedQuery<QcamtItem> typedQuery = em.createQuery(queryString, QcamtItem.class);
		typedQuery.setParameter("companyCode", companyCode);
		return typedQuery.getSingleResult();
	}

	/**
	 * Find aggre header.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the qlspt paylst aggre head
	 */
	private QlsptPaylstAggreHead findAggreHeader(String companyCode) {
		EntityManager em = this.getEntityManager();
		String queryString = "SELECT h FROM QlsptPaylstAggreHead h "
			+ "WHERE h.qlsptPaylstAggreHeadPK.ccd = :companyCode "
			+ "AND h.qlsptPaylstAggreHeadPK.aggregateCd = 1 AND h.qlsptPaylstAggreHeadPK.ctgAtr = 1 ";

		TypedQuery<QlsptPaylstAggreHead> typedQuery = em.createQuery(queryString, QlsptPaylstAggreHead.class);
		typedQuery.setParameter("companyCode", companyCode);
		return typedQuery.getSingleResult();
	}

	/**
	 * Find all person.
	 *
	 * @param personIds
	 *            the person ids
	 * @return the list
	 */
	private List<PbsmtPersonBase> findAllPerson(List<String> personIds) {
		EntityManager em = this.getEntityManager();
		String queryString = "SELECT h FROM PbsmtPersonBase h " + "WHERE h.pid IN = :personIds ";

		TypedQuery<PbsmtPersonBase> typedQuery = em.createQuery(queryString, PbsmtPersonBase.class);
		typedQuery.setParameter("personIds", personIds);
		return typedQuery.getResultList();
	}

	/**
	 * Find payment header.
	 *
	 * @param companyCode
	 *            the company code
	 * @param query
	 *            the query
	 * @return the list
	 */
	public List<QstdtPaymentHeader> findPaymentHeader(String companyCode, PaymentSalaryQuery query) {
		EntityManager em = this.getEntityManager();
		String queryString = "SELECT h FROM QstdtPaymentHeader h "
			+ "WHERE h.qstdtPaymentHeaderPK.companyCode = :companyCode "
			+ "AND h.qstdtPaymentHeaderPK.payBonusAtr = 0 "
			+ "AND h.qstdtPaymentHeaderPK.processingYM >= :startDate "
			+ "AND h.qstdtPaymentHeaderPK.processingYM <= :endDate ";

		TypedQuery<QstdtPaymentHeader> typedQuery = em.createQuery(queryString, QstdtPaymentHeader.class);
		typedQuery.setParameter("companyCode", companyCode);
		typedQuery.setParameter("startDate", query.getStartDate());
		typedQuery.setParameter("endDate", query.getEndDate());
		return typedQuery.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.file.pr.app.export.detailpaymentsalary.
	 * PaymentSalaryReportRepository#checkExport(java.lang.String,
	 * nts.uk.file.pr.app.export.detailpaymentsalary.query.PaymentSalaryQuery)
	 */
	@Override
	public boolean checkExport(String companyCode, PaymentSalaryQuery query) {
		if (CollectionUtil.isEmpty(this.findPaymentHeader(companyCode, query))) {
			return true;
		}
		return false;
	}

}
