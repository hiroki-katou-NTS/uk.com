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
import nts.uk.ctx.basic.infra.entity.report.PcpmtPersonCom;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItem;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;
import nts.uk.file.pr.app.export.detailpaymentsalary.PaySalaryReportRepository;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.DepartmentDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeKey;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.query.PaymentSalaryQuery;

/**
 * The Class JpaPaymentSalaryReportRepository.
 */
@Stateless
public class JpaPaySalaryReportRepository extends JpaRepository
	implements PaySalaryReportRepository {

	public static final int PAY_BONUS_ATR = 0;
	public static final int SPARE_PAY_ATR = 0;
	public static final int LENGTH_LEVEL = 3;

	public static final String QUERY_EMPLOYEE_SORT = "SELECT header,dep,com "
		+ "FROM QstdtPaymentHeader header, CmnmtDep dep, PcpmtPersonCom com "
		+ "WHERE header.qstdtPaymentHeaderPK.companyCode = :companyCode "
		+ "AND header.qstdtPaymentHeaderPK.personId IN :personIds "
		+ "AND header.qstdtPaymentHeaderPK.companyCode = dep.cmnmtDepPK.companyCode "
		+ "AND header.departmentCode = dep.cmnmtDepPK.departmentCode "
		+ "AND com.pcpmtPersonComPK.ccd = header.qstdtPaymentHeaderPK.companyCode "
		+ "AND com.pcpmtPersonComPK.pid = header.qstdtPaymentHeaderPK.personId ";

	public static final String QUERY_PAYMENT_DETAIL = "SELECT h FROM QstdtPaymentDetail h "
		+ "WHERE h.qstdtPaymentDetailPK.companyCode = :companyCode "
		+ "AND h.qstdtPaymentDetailPK.payBonusAttribute = 0 "
		+ "AND h.qstdtPaymentDetailPK.processingYM >= :startDate "
		+ "AND h.qstdtPaymentDetailPK.processingYM <= :endDate ";

	public static final String QUERY_PAYMENT_DATA_DETAIL = "SELECT detail, com, item, detailheader "
		+ "FROM QstdtPaymentDetail detail, PcpmtPersonCom com, QcamtItem item , "
		+ "QlsptPaylstFormDetail detailheader "
		+ "WHERE detail.qstdtPaymentDetailPK.companyCode = :companyCode "
		+ "AND detail.qstdtPaymentDetailPK.payBonusAttribute = " + PAY_BONUS_ATR + " "
		+ "AND detail.qstdtPaymentDetailPK.sparePayAttribute = " + SPARE_PAY_ATR + " "
		+ "AND detail.qstdtPaymentDetailPK.personId IN :personIds "
		+ "AND com.pcpmtPersonComPK.ccd = detail.qstdtPaymentDetailPK.companyCode "
		+ "AND com.pcpmtPersonComPK.pid = detail.qstdtPaymentDetailPK.personId "
		+ "AND detail.qstdtPaymentDetailPK.categoryATR = item.qcamtItemPK.ctgAtr "
		+ "AND detail.qstdtPaymentDetailPK.itemCode = item.qcamtItemPK.itemCd "
		+ "AND item.qcamtItemPK.ccd = detail.qstdtPaymentDetailPK.companyCode "
		+ "AND detailheader.qlsptPaylstFormDetailPK.ccd = detail.qstdtPaymentDetailPK.companyCode "
		+ "AND detailheader.qlsptPaylstFormDetailPK.formCd = :formCd "
		+ "AND detailheader.qlsptPaylstFormDetailPK.ctgAtr = detail.qstdtPaymentDetailPK.categoryATR "
		+ "AND detailheader.qlsptPaylstFormDetailPK.itemAgreCd = detail.qstdtPaymentDetailPK.itemCode ";

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
	public PaymentSalaryReportData exportPDFPaymentSalary(String companyCode,
		PaymentSalaryQuery query) {
		String personIds[] = { "99900000-0000-0000-0000-000000000001",
			"99900000-0000-0000-0000-000000000002", "99900000-0000-0000-0000-000000000003",
			"99900000-0000-0000-0000-000000000004", "99900000-0000-0000-0000-000000000005",
			"99900000-0000-0000-0000-000000000006", "99900000-0000-0000-0000-000000000007",
			"99900000-0000-0000-0000-000000000008", "99900000-0000-0000-0000-000000000009",
			"99900000-0000-0000-0000-0000000000010" };
		query.setPersonIds(Arrays.asList(personIds));
		PaymentSalaryReportData data = new PaymentSalaryReportData();

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
			key.setYearMonth(detail.qstdtPaymentDetailPK.processingYM);
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
		TypedQuery<Object[]> typedQuery = em.createQuery(QUERY_EMPLOYEE_SORT, Object[].class);
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
			departmentDto.setDepLevel(dep.getHierarchyId().length() / LENGTH_LEVEL);
			departmentDto.setDepPath(dep.getHierarchyId());
			departmentDto.setYearMonth(dep.getStartDate().yearMonth().v());
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
		departmentDto.setDepLevel(department.getHierarchyCode().v().length() / LENGTH_LEVEL);
		departmentDto.setDepPath(department.getHierarchyCode().v());
		return departmentDto;
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

		TypedQuery<Object[]> typedQuery = em.createQuery(QUERY_PAYMENT_DATA_DETAIL, Object[].class);

		// set eq companyCode
		typedQuery.setParameter("companyCode", companyCode);

		// set in persionId
		typedQuery.setParameter("personIds", query.getPersonIds());
		typedQuery.setParameter("formCd", query.getOutputSettingCode());

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
	private List<QstdtPaymentDetail> findPaymentDetail(String companyCode,
		PaymentSalaryQuery query) {
		EntityManager em = this.getEntityManager();
		TypedQuery<QstdtPaymentDetail> typedQuery = em.createQuery(QUERY_PAYMENT_DETAIL,
			QstdtPaymentDetail.class);
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
