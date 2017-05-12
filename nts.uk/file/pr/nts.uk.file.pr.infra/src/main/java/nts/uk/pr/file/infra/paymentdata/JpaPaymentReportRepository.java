/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.paymentdata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItem;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.file.pr.app.export.payment.PaymentReportQuery;
import nts.uk.file.pr.app.export.payment.PaymentReportRepository;
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.file.pr.app.export.payment.data.dto.DepartmentDto;
import nts.uk.file.pr.app.export.payment.data.dto.EmployeeDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.SalaryItemDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class JpaPaymentReportRepository.
 */
@Stateless
public class JpaPaymentReportRepository extends JpaRepository implements PaymentReportRepository {

	/** The Constant TYPE_PRINT_SPECIFICATION. */
	public static final int TYPE_PRINT_SPECIFICATION = 1;

	/** The Constant TYPE_PRINT_LAYOUT. */
	public static final int TYPE_PRINT_LAYOUT = 2;
	
	
	/** The Constant CATEGORY_PAYMENT. */
	public static final int CATEGORY_PAYMENT = 0;
	
	/** The Constant CATEGORY_DEDUCTION. */
	public static final int CATEGORY_DEDUCTION = 1;
	
	/** The Constant CATEGORY_ATTENDANCE. */
	public static final int CATEGORY_ATTENDANCE = 2;
	
	/** The Constant CATEGORY_ARTICLE. */
	public static final int CATEGORY_ARTICLE = 3;

	/** The Constant FIND_DEPARTMENT_BYCODE. */
	public static final String FIND_DEPARTMENT_BYCODE = "SELECT dep FROM CmnmtDep dep "
		+ "WHERE dep.cmnmtDepPK.companyCode = :companyCode "
		+ " AND dep.cmnmtDepPK.departmentCode = :departmentCode";

	/** The Constant FIND_PAYMENT_HEADER_LAYOUT. */
	public static final String FIND_PAYMENT_HEADER_LAYOUT = "SELECT header FROM QstdtPaymentHeader "
		+ "WHERE header.qstdtPaymentHeaderPK.companyCode = :companyCode "
		+ "AND header.qstdtPaymentHeaderPK.processingNo = :processingNo "
		+ "AND header.qstdtPaymentHeaderPK.payBonusAtr = 0 "
		+ "AND header.qstdtPaymentHeaderPK.processingYM = :processingYM "
		+ "AND header.qstdtPaymentHeaderPK.sparePayAtr = 0 ";

	/** The Constant FIND_PAYMENT_HEADER_SPECIFICATION. */
	public static final String FIND_PAYMENT_HEADER_SPECIFICATION = "SELECT header "
		+ "FROM QstdtPaymentHeader header "
		+ "WHERE header.qstdtPaymentHeaderPK.companyCode = :companyCode "
		+ "AND header.qstdtPaymentHeaderPK.processingNo = :processingNo "
		+ "AND header.qstdtPaymentHeaderPK.payBonusAtr = 0 "
		+ "AND header.qstdtPaymentHeaderPK.processingYM = :processingYM "
		+ "AND header.qstdtPaymentHeaderPK.sparePayAtr = 0 "
		+ "AND header.specificationCode IN :specificationCodes ";

	public static final String DETAL_PAYMENT_HEADER_CATEGORY = "SELECT detail,item "
		+ "FROM QstdtPaymentDetail detail, QcamtItem item "
		+ "WHERE detail.qstdtPaymentDetailPK.companyCode = :companyCode "
		+ "AND detail.qstdtPaymentDetailPK.personId = :personId "
		+ "AND detail.qstdtPaymentDetailPK.processingNo = :processingNo "
		+ "AND detail.qstdtPaymentDetailPK.payBonusAttribute = 0 "
		+ "AND detail.qstdtPaymentDetailPK.processingYM = :processingYM "
		+ "AND detail.qstdtPaymentDetailPK.sparePayAttribute = 0 "
		+ "AND detail.qstdtPaymentDetailPK.categoryATR = :categoryItem "
		+ "AND detail.qstdtPaymentDetailPK.itemCode = item.qcamtItemPK.itemCd "
		+ "AND detail.qstdtPaymentDetailPK.categoryATR = item.qcamtItemPK.ctgAtr "
		+ "AND detail.qstdtPaymentDetailPK.companyCode = item.qcamtItemPK.ccd ";

	/**
	 * Check payment header layout.
	 *
	 * @param companyCode the company code
	 * @param query the query
	 * @return the list
	 */
	private List<QstdtPaymentHeader> checkPaymentHeaderLayout(String companyCode,
		PaymentReportQuery query) {

		return this.queryProxy().query(FIND_PAYMENT_HEADER_LAYOUT, QstdtPaymentHeader.class)
			.setParameter("companyCode", companyCode)
			.setParameter("processingNo", query.getProcessingNo())
			.setParameter("processingYM", query.getProcessingYM()).getList();
	}

	/**
	 * Check payment header specification.
	 *
	 * @param companyCode the company code
	 * @param query the query
	 * @return the list
	 */
	private List<QstdtPaymentHeader> checkPaymentHeaderSpecification(String companyCode,
		PaymentReportQuery query) {

		return this.queryProxy().query(FIND_PAYMENT_HEADER_SPECIFICATION, QstdtPaymentHeader.class)
			.setParameter("companyCode", companyCode)
			.setParameter("processingNo", query.getProcessingNo())
			.setParameter("specificationCodes", query.getSpecificationCodes())
			.setParameter("processingYM", query.getProcessingYM()).getList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.file.pr.app.export.payment.PaymentReportRepository#checkExportData
	 * (nts.uk.file.pr.app.export.payment.PaymentReportQuery)
	 */
	@Override
	public void checkExportData(PaymentReportQuery query) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code

		String companyCode = loginUserContext.companyCode();

		if (query.getSelectPrintTypes() == TYPE_PRINT_LAYOUT
			&& CollectionUtil.isEmpty(this.checkPaymentHeaderLayout(companyCode, query))) {
			throw new BusinessException("ER010");
		}

		if (query.getSelectPrintTypes() == TYPE_PRINT_SPECIFICATION
			&& CollectionUtil.isEmpty(this.checkPaymentHeaderSpecification(companyCode, query))) {
			throw new BusinessException("ER010");
		}
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

		List<QstdtPaymentHeader> paymentHeaders = new ArrayList<>();

		// type print eq LAYOUT
		if (query.getSelectPrintTypes() == TYPE_PRINT_LAYOUT) {
			paymentHeaders = this.checkPaymentHeaderLayout(companyCode, query);
		}

		// type print eq SPECIFICATION
		if (query.getSelectPrintTypes() == TYPE_PRINT_SPECIFICATION) {
			paymentHeaders = this.checkPaymentHeaderSpecification(companyCode, query);
		}

		List<PaymentReportDto> reportDatas = paymentHeaders.stream()
			.map(header -> toHeaderData(header)).collect(Collectors.toList());

		if (CollectionUtil.isEmpty(reportDatas)) {
			throw new BusinessException("ER010");
		}
		
		PaymentReportData reportData = new PaymentReportData();
		reportData.setReportData(reportDatas);
		return reportData;
	}

	/**
	 * Detail header.
	 *
	 * @param header the header
	 * @param category the category
	 * @return the list
	 */
	private List<SalaryItemDto> detailHeader(QstdtPaymentHeader header, int category) {

		// detail header data and category
		List<Object[]> dataDetailHeader = this.queryProxy()
			.query(DETAL_PAYMENT_HEADER_CATEGORY, Object[].class)
			.setParameter("companyCode", header.qstdtPaymentHeaderPK.companyCode)
			.setParameter("processingNo", header.qstdtPaymentHeaderPK.processingNo)
			.setParameter("personId", header.qstdtPaymentHeaderPK.personId)
			.setParameter("processingYM", header.qstdtPaymentHeaderPK.processingYM)
			.setParameter("categoryItem", category).getList();

		// to Dto data
		List<SalaryItemDto> salaryItems = dataDetailHeader.stream().map(detail -> {
			SalaryItemDto dto = new SalaryItemDto();
			if (detail[1] instanceof QcamtItem) {
				QcamtItem item = (QcamtItem) detail[1];
				dto.setItemName(item.itemName);
			}
			if (detail[0] instanceof QstdtPaymentDetail) {
				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) detail[0];
				dto.setItemVal(paymentDetail.value);
			}
			dto.setView(true);
			return dto;
		}).collect(Collectors.toList());
		
		return salaryItems;
	}

	/**
	 * To header data.
	 *
	 * @param header the header
	 * @return the payment report data
	 */
	private PaymentReportDto toHeaderData(QstdtPaymentHeader header) {
		PaymentReportDto reportData = new PaymentReportDto();

		// set department
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDepartmentCode(header.departmentCode);
		departmentDto.setDepartmentName(header.departmentName);
		reportData.setDepartmentInfo(departmentDto);

		// set employee
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmployeeCode(header.employeeCode);
		employeeDto.setEmployeeName(header.employeeName);
		reportData.setEmployeeInfo(employeeDto);

		// 支給項目  (categoryItem = payment)
		reportData.setPaymentItems(this.detailHeader(header, CATEGORY_PAYMENT));
		
		//控除項目 (categoryItem = reduction)
		reportData.setDeductionItems(this.detailHeader(header, CATEGORY_DEDUCTION));
		
		//勤怠項目 (categoryItem = attendance)
		reportData.setAttendanceItems(this.detailHeader(header, CATEGORY_ATTENDANCE));
		
		//記事項目 (categoryItem = article)
		reportData.setArticleItems(this.detailHeader(header, CATEGORY_ARTICLE));
		
		reportData.setProcessingYm(header.qstdtPaymentHeaderPK.processingYM);
		return reportData;
	}

}
