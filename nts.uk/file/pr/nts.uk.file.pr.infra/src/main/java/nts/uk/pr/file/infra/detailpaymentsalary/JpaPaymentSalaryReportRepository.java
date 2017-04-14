/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.detailpaymentsalary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.department.DepartmentRepository;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDep;
import nts.uk.ctx.basic.infra.entity.report.PbsmtPersonBase;
import nts.uk.ctx.basic.infra.entity.report.PcpmtPersonCom;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItem;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreHead;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHead;
import nts.uk.file.pr.app.export.detailpaymentsalary.PaymentSalaryReportRepository;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.DepartmentDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeKey;
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
		String personIds[] = { "999000000000000000000000000000000001", "999000000000000000000000000000000002",
			"999000000000000000000000000000000003", "999000000000000000000000000000000004",
			"999000000000000000000000000000000005", "999000000000000000000000000000000006",
			"999000000000000000000000000000000007", "999000000000000000000000000000000008",
			"999000000000000000000000000000000009", "999000000000000000000000000000000010" };
		query.setPersonIds(Arrays.asList(personIds));
		PaymentSalaryReportData data = new PaymentSalaryReportData();
		/*
		 * QlsptPaylstFormHead headerForm = this.findHeaderFrom(companyCode,
		 * query.getOutputSettingCode()); //QcamtItem item =
		 * this.findItem(companyCode); QlsptPaylstAggreHead payHead =
		 * this.findAggreHeader(companyCode); // hea // if
		 * (CollectionUtil.isEmpty(payHead.getQlsptPaylstAggreDetailList())) {
		 * throw new BusinessException("ER010"); } List<PbsmtPersonBase> persons
		 * = this.findAllPerson(query.getPersonIds()); List<QstdtPaymentDetail>
		 * paymentDetails = this.findAllDetail(companyCode, query); if
		 * (CollectionUtil.isEmpty(paymentDetails)) { throw new
		 * BusinessException("ER010"); }
		 */
		List<Object[]> paydetails = this.findAllDetail(companyCode, query);

		List<EmployeeDto> employees = this.sortEmployees(companyCode, query.getPersonIds());

		Map<EmployeeKey, Double> mapEmployeeAmount = new HashMap<>();
		paydetails.stream().forEach(pay -> {
			QstdtPaymentDetail detail = (QstdtPaymentDetail) pay[0];
			PcpmtPersonCom com = (PcpmtPersonCom) pay[1];
			QcamtItem item = (QcamtItem) pay[2];
			EmployeeKey key = new EmployeeKey();
			key.setEmployeeCode(com.getScd());
			key.setItemName(item.itemName);
			key.setSalaryCategory(SalaryCategory.valueOf(detail.qstdtPaymentDetailPK.categoryATR));
			key.setYearMonth(String.valueOf(detail.qstdtPaymentDetailPK.processingYM));
			if (!mapEmployeeAmount.containsKey(key)) {
				mapEmployeeAmount.put(key, detail.value.doubleValue());
			}
		});
		data.setMapEmployeeAmount(mapEmployeeAmount);
		data.setEmployees(employees);

		return data;
	}

	/**
	 * Sort employees.
	 *
	 * @param companyCode
	 *            the company code
	 * @param personIds
	 *            the person ids
	 * @return the list
	 */
	public List<EmployeeDto> sortEmployees(String companyCode, List<String> personIds) {

		EntityManager em = this.getEntityManager();

		// PbsmtPersonBase
		// PcpmtPersonCom
		// PogmtPersonDepRgl
		// CmnmtDep

		String query = "SELECT header,dep,com FROM QstdtPaymentHeader header, CmnmtDep dep ,"
			+ " PcpmtPersonCom com WHERE " + "header.qstdtPaymentHeaderPK.companyCode = :companyCode "
			+ "AND header.qstdtPaymentHeaderPK.personId IN :personIds "
			+ "AND header.qstdtPaymentHeaderPK.companyCode = dep.cmnmtDepPK.companyCode "
			+ "AND header.departmentCode = dep.cmnmtDepPK.departmentCode "
			+ "AND com.pcpmtPersonComPK.ccd = header.qstdtPaymentHeaderPK.companyCode "
			+ "AND com.pcpmtPersonComPK.pid = header.qstdtPaymentHeaderPK.personId ";

		TypedQuery<Object[]> typedQuery = em.createQuery(query, Object[].class);
		typedQuery.setParameter("personIds", personIds);
		typedQuery.setParameter("companyCode", companyCode);
		List<Object[]> data = typedQuery.getResultList();

		return data.stream().map(object -> {
			PcpmtPersonCom com = (PcpmtPersonCom) object[2];
			CmnmtDep dep = (CmnmtDep) object[1];
			QstdtPaymentHeader empl = (QstdtPaymentHeader) object[0];
			EmployeeDto dto = new EmployeeDto();
			dto.setCode(com.getScd());
			dto.setName(empl.employeeName);

			DepartmentDto departmentDto = new DepartmentDto();
			departmentDto.setCode(dep.getCmnmtDepPK().getDepartmentCode());
			departmentDto.setName(dep.getDepName());
			departmentDto.setDepLevel(dep.getHierarchyId().length() / 3);
			departmentDto.setDepPath(dep.getHierarchyId());
			departmentDto.setYearMonth(dep.getStartDate().yearMonth().v().toString());
			dto.setDepartment(departmentDto);
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
	private List<QcamtItem> findItem(String companyCode) {
		EntityManager em = this.getEntityManager();
		String queryString = "SELECT h FROM QcamtItem h " + "WHERE h.qcamtItemPK.ccd = :companyCode "
			+ "AND h.qcamtItemPK.ctgAtr = 0 AND h.qcamtItemPK.itemCd = '0' ";

		TypedQuery<QcamtItem> typedQuery = em.createQuery(queryString, QcamtItem.class);
		typedQuery.setParameter("companyCode", companyCode);
		return typedQuery.getResultList();
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
	 * Find all detail.
	 *
	 * @param companyCode
	 *            the company code
	 * @param query
	 *            the query
	 * @return the list
	 */
	private List<Object[]> findAllDetail(String companyCode, PaymentSalaryQuery query) {
		EntityManager em = this.getEntityManager();
		String queryString = "SELECT detail,com,item FROM QstdtPaymentDetail detail, PcpmtPersonCom com, "
			+ "QcamtItem item " + "WHERE detail.qstdtPaymentDetailPK.companyCode = :companyCode "
			+ "AND detail.qstdtPaymentDetailPK.personId IN :personIds "
			+ "AND com.pcpmtPersonComPK.ccd = detail.qstdtPaymentDetailPK.companyCode "
			+ "AND com.pcpmtPersonComPK.pid = detail.qstdtPaymentDetailPK.personId "
			+ "AND detail.qstdtPaymentDetailPK.categoryATR = item.qcamtItemPK.ctgAtr "
			+ "AND detail.qstdtPaymentDetailPK.itemCode = item.qcamtItemPK.itemCd "
			+ "AND item.qcamtItemPK.ccd = detail.qstdtPaymentDetailPK.companyCode ";
		/*
		 * + "AND h.qstdtPaymentDetailPK.payBonusAttribute = 0 " +
		 * "AND h.qstdtPaymentDetailPK.processingYM >= :startDate " +
		 * "AND h.qstdtPaymentDetailPK.processingYM <= :endDate " +
		 * "AND h.qstdtPaymentDetailPK.sparePayAttribute = 0 " +
		 * "AND h.qstdtPaymentDetailPK.categoryATR = 0 AND h.qstdtPaymentDetailPK.itemCode = 0"
		 * ;
		 * 
		 * // set start date typedQuery.setParameter("startDate",
		 * query.getStartDate());
		 * 
		 * // set end date typedQuery.setParameter("endDate",
		 * query.getEndDate());
		 */
		TypedQuery<Object[]> typedQuery = em.createQuery(queryString, Object[].class);

		// set eq companyCode
		typedQuery.setParameter("companyCode", companyCode);

		// set in persionId
		typedQuery.setParameter("personIds", query.getPersonIds());

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
	private List<QstdtPaymentHeader> findPaymentHeader(String companyCode, PaymentSalaryQuery query) {
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

	/**
	 * Find paymend detail.
	 *
	 * @param companyCode
	 *            the company code
	 * @param query
	 *            the query
	 * @return the list
	 */
	private List<QstdtPaymentDetail> findPaymentDetail(String companyCode, PaymentSalaryQuery query) {
		EntityManager em = this.getEntityManager();
		String queryString = "SELECT h FROM QstdtPaymentDetail h "
			+ "WHERE h.qstdtPaymentDetailPK.companyCode = :companyCode "
			+ "AND h.qstdtPaymentDetailPK.payBonusAttribute = 0 "
			+ "AND h.qstdtPaymentDetailPK.processingYM >= :startDate "
			+ "AND h.qstdtPaymentDetailPK.processingYM <= :endDate ";

		TypedQuery<QstdtPaymentDetail> typedQuery = em.createQuery(queryString, QstdtPaymentDetail.class);
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
		// 2 find header
		if (CollectionUtil.isEmpty(this.findPaymentDetail(companyCode, query))) {
			return true;
		}
		return false;
	}

}
