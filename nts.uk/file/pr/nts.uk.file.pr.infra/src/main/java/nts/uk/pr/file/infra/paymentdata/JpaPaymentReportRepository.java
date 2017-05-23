/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.paymentdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.infra.entity.organization.employment.CmnmtEmp;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItem;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingRepository;
import nts.uk.file.pr.app.export.payment.PaymentReportQuery;
import nts.uk.file.pr.app.export.payment.PaymentReportRepository;
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentCompanyDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentDepartmentDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentEmployeeDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentSalaryItemDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseErasProvider;

/**
 * The Class JpaPaymentReportRepository.
 */
@Stateless
public class JpaPaymentReportRepository extends JpaRepository implements PaymentReportRepository {

	/** The japanese provider. */
	@Inject
	private JapaneseErasProvider japaneseProvider;

	/** The repository. */
	@Inject
	private ContactItemsSettingRepository repository;

	/** The Constant TOTAL_COLUMN_INLINE. */
	private static final int TOTAL_COLUMN_INLINE = 9;

	/** The Constant FIRST_COLUMN_INLINE. */
	private static final int FIRST_COLUMN_INLINE = 1;

	/** The Constant TYPE_PRINT_SPECIFICATION. */
	public static final int TYPE_PRINT_SPECIFICATION = 2;

	/** The Constant TYPE_PRINT_LAYOUT. */
	public static final int TYPE_PRINT_LAYOUT = 1;

	/** The Constant CATEGORY_PAYMENT. */
	public static final int CATEGORY_PAYMENT = 0;

	/** The Constant CATEGORY_DEDUCTION. */
	public static final int CATEGORY_DEDUCTION = 1;

	/** The Constant CATEGORY_ATTENDANCE. */
	public static final int CATEGORY_ATTENDANCE = 2;

	/** The Constant CATEGORY_ARTICLE. */
	public static final int CATEGORY_ARTICLE = 3;
	
	/** The Constant CATEGORY_OTHER. */
	public static final int CATEGORY_OTHER = 9;

	/** The print line position. */
	int printLinePosition = 0;

	/** The column position. */
	int columnPosition = 0;

	/** The Constant FIND_EMPLOYEE_BYCODE. */
	public static final String FIND_EMPLOYEE_BYCODE = "SELECT emp FROM CmnmtEmp emp "
		+ "WHERE emp.cmnmtEmpPk.companyCode = :companyCode "
		+ " AND emp.cmnmtEmpPk.employmentCode = :employmentCode";

	/** The Constant FIND_PAYMENT_HEADER_LAYOUT. */
	public static final String FIND_PAYMENT_HEADER_LAYOUT = "SELECT header "
		+ "FROM QstdtPaymentHeader header "
		+ "WHERE header.qstdtPaymentHeaderPK.companyCode = :companyCode "
		+ "AND header.qstdtPaymentHeaderPK.processingNo = :processingNo "
		+ "AND header.qstdtPaymentHeaderPK.payBonusAtr = 0 "
		+ "AND header.qstdtPaymentHeaderPK.processingYM = :processingYM "
		+ "AND header.qstdtPaymentHeaderPK.sparePayAtr = 0 " + "AND header.layoutAtr = :layoutItem";

	/** The Constant FIND_PAYMENT_HEADER_SPECIFICATION. */
	public static final String FIND_PAYMENT_HEADER_SPECIFICATION = "SELECT header "
		+ "FROM QstdtPaymentHeader header "
		+ "WHERE header.qstdtPaymentHeaderPK.companyCode = :companyCode "
		+ "AND header.qstdtPaymentHeaderPK.processingNo = :processingNo "
		+ "AND header.qstdtPaymentHeaderPK.payBonusAtr = 0 "
		+ "AND header.qstdtPaymentHeaderPK.processingYM = :processingYM "
		+ "AND header.qstdtPaymentHeaderPK.sparePayAtr = 0 "
		+ "AND header.specificationCode IN :specificationCodes ";

	/** The Constant DETAL_PAYMENT_HEADER_CATEGORY. */
	public static final String DETAL_PAYMENT_HEADER_CATEGORY = "SELECT detail,item "
		+ "FROM QstdtPaymentDetail detail, QcamtItem item "
		+ "WHERE detail.qstdtPaymentDetailPK.companyCode = :companyCode "
		+ "AND detail.qstdtPaymentDetailPK.personId = :personId "
		+ "AND detail.qstdtPaymentDetailPK.processingNo = :processingNo "
		+ "AND detail.qstdtPaymentDetailPK.payBonusAttribute = 0 "
		+ "AND detail.qstdtPaymentDetailPK.processingYM = :processingYM "
		+ "AND detail.qstdtPaymentDetailPK.sparePayAttribute = 0 "
		+ "AND detail.printLinePosition != -1 "
		+ "AND detail.qstdtPaymentDetailPK.categoryATR = :categoryItem "
		+ "AND detail.qstdtPaymentDetailPK.itemCode = item.qcamtItemPK.itemCd "
		+ "AND detail.qstdtPaymentDetailPK.categoryATR = item.qcamtItemPK.ctgAtr "
		+ "AND detail.qstdtPaymentDetailPK.companyCode = item.qcamtItemPK.ccd "
		+ "ORDER BY detail.printLinePosition ASC , detail.columnPosition ASC ";

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
			.setParameter("layoutItem", 0)
			//TODO FAKE LAYOUT ITEM
			.setParameter("processingYM", query.getProcessingYM()).getList();
	}

	/**
	 * Find employee code.
	 *
	 * @param companyCode the company code
	 * @param employeeCode the employee code
	 * @return the optional
	 */
	Optional<CmnmtEmp> findEmployeeCode(String companyCode, String employeeCode) {
		return this.queryProxy().query(FIND_EMPLOYEE_BYCODE, CmnmtEmp.class)
			.setParameter("companyCode", companyCode).setParameter("employmentCode", employeeCode).getSingle();
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

		// print type layout ?
		if (query.getSelectPrintTypes() == TYPE_PRINT_LAYOUT
			&& CollectionUtil.isEmpty(this.checkPaymentHeaderLayout(companyCode, query))) {
			throw new BusinessException("ER010");
		}

		// print type specification ?
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

		// type print equals LAYOUT
		if (query.getSelectPrintTypes() == TYPE_PRINT_LAYOUT) {
			paymentHeaders = this.checkPaymentHeaderLayout(companyCode, query);
		}

		// type print equals SPECIFICATION
		if (query.getSelectPrintTypes() == TYPE_PRINT_SPECIFICATION) {
			paymentHeaders = this.checkPaymentHeaderSpecification(companyCode, query);
		}

		// to list data
		List<PaymentReportDto> reportDatas = paymentHeaders.stream()
			.map(header -> toHeaderData(header)).collect(Collectors.toList());

		// check is empty ?
		if (CollectionUtil.isEmpty(reportDatas)) {
			throw new BusinessException("ER010");
		}

		// return data
		PaymentReportData reportData = new PaymentReportData();
		reportData.setReportData(reportDatas);
		reportData.setLayoutItem(query.getLayoutItems());
		return reportData;
	}

	/**
	 * Detail header.
	 *
	 * @param header the header
	 * @param category the category
	 * @return the list
	 */
	private List<PaymentSalaryItemDto> detailHeader(QstdtPaymentHeader header, int category) {

		// detail header data and category
		List<Object[]> dataDetailHeader = this.queryProxy()
			.query(DETAL_PAYMENT_HEADER_CATEGORY, Object[].class)
			.setParameter("companyCode", header.qstdtPaymentHeaderPK.companyCode)
			.setParameter("processingNo", header.qstdtPaymentHeaderPK.processingNo)
			.setParameter("personId", header.qstdtPaymentHeaderPK.personId)
			.setParameter("processingYM", header.qstdtPaymentHeaderPK.processingYM)
			.setParameter("categoryItem", category).getList();

		// to DTO data
		List<PaymentSalaryItemDto> salaryItems = new ArrayList<>();
		printLinePosition = 1;
		columnPosition = 0;
		dataDetailHeader.forEach(detail -> {

			QstdtPaymentDetail paymentDetail = new QstdtPaymentDetail();
			QcamtItem item = new QcamtItem();
			if (detail[0] instanceof QstdtPaymentDetail) {
				paymentDetail = (QstdtPaymentDetail) detail[0];
			}

			// add all data begin => end
			salaryItems.addAll(defaultDataBeginEnd(printLinePosition,
				paymentDetail.printLinePosition, columnPosition, paymentDetail.columnPosition));

			// set up data print
			printLinePosition = paymentDetail.printLinePosition;
			columnPosition = paymentDetail.columnPosition;

			// add to data
			PaymentSalaryItemDto dto = new PaymentSalaryItemDto();
			if (detail[1] instanceof QcamtItem) {
				item = (QcamtItem) detail[1];
			}
			dto.setItemName(item.itemName);
			dto.setItemVal(paymentDetail.value);
			dto.setView(true);
			salaryItems.add(dto);
		});

		// add all data begin => end
		salaryItems.addAll(defaultDataColumn(columnPosition + 1, TOTAL_COLUMN_INLINE));

		return salaryItems;
	}

	/**
	 * Default data begin end.
	 *
	 * @param startLine the start line
	 * @param endLine the end line
	 * @param startColum the start colum
	 * @param endColum the end colum
	 * @return the list
	 */

	private List<PaymentSalaryItemDto> defaultDataBeginEnd(int startLine, int endLine, int startColum,
		int endColum) {
		if (startLine == endLine) {
			return defaultDataColumn(startColum + 1, endColum - 1);
		}
		List<PaymentSalaryItemDto> salaryItems = new ArrayList<>();
		salaryItems.addAll(defaultDataColumn(startColum + 1, TOTAL_COLUMN_INLINE));
		salaryItems.addAll(defaultDataLine(startLine + 1, endLine - 1));
		salaryItems.addAll(defaultDataColumn(1, endColum - 1));
		return salaryItems;
	}

	/**
	 * Default data line.
	 *
	 * @param startLine the start line
	 * @param endLine the end line
	 * @return the list
	 */
	private List<PaymentSalaryItemDto> defaultDataLine(int startLine, int endLine) {
		List<PaymentSalaryItemDto> salaryItems = new ArrayList<>();
		for (int index = startLine; index <= endLine; index++) {
			salaryItems.addAll(defaultDataColumn(FIRST_COLUMN_INLINE, TOTAL_COLUMN_INLINE));
		}
		return salaryItems;
	}

	/**
	 * Default data column.
	 *
	 * @param startColum the start colum
	 * @param endColum the end colum
	 * @return the list
	 */
	private List<PaymentSalaryItemDto> defaultDataColumn(int startColum, int endColum) {
		List<PaymentSalaryItemDto> salaryItems = new ArrayList<>();
		for (int index = startColum; index <= endColum; index++) {
			salaryItems.add(PaymentSalaryItemDto.defaultData());
		}
		return salaryItems;
	}

	/**
	 * To header data.
	 *
	 * @param header the header
	 * @return the payment report dto
	 */
	private PaymentReportDto toHeaderData(QstdtPaymentHeader header) {
		PaymentReportDto reportData = new PaymentReportDto();

		// set department
		PaymentDepartmentDto departmentDto = new PaymentDepartmentDto();
		departmentDto.setDepartmentCode(header.departmentCode);
		departmentDto.setDepartmentName(header.departmentName);
		departmentDto.setDepartmentOutput("xxxyyyzzz");
		reportData.setDepartmentInfo(departmentDto);

		// set employee
		PaymentEmployeeDto employeeDto = new PaymentEmployeeDto();
		Optional<CmnmtEmp> employee = this.findEmployeeCode(header.qstdtPaymentHeaderPK.companyCode,
			header.employeeCode);
		
		if (employee.isPresent()) {
			employeeDto.setEmployeeCode(employee.get().cmnmtEmpPk.employmentCode);
			employeeDto.setEmployeeName(employee.get().employmentName);
		}else {
			employeeDto.setEmployeeCode(header.employeeCode);
			employeeDto.setEmployeeName(header.employeeName);
		}
		reportData.setEmployeeInfo(employeeDto);

		// 支給項目 (categoryItem = payment)
		reportData.setPaymentItems(this.detailHeader(header, CATEGORY_PAYMENT));

		// 控除項目 (categoryItem = reduction)
		reportData.setDeductionItems(this.detailHeader(header, CATEGORY_DEDUCTION));

		// 勤怠項目 (categoryItem = attendance)
		reportData.setAttendanceItems(this.detailHeader(header, CATEGORY_ATTENDANCE));

		// 記事項目 (categoryItem = article)
		reportData.setArticleItems(this.detailHeader(header, CATEGORY_ARTICLE));
		
		// 他項目 (categoryItem = other)
		reportData.setOtherItems(this.detailHeader(header, CATEGORY_OTHER));

		// set remark
		reportData.setRemark(
			repository.getRemark(new ContactItemsCode(new JpaPaymentContactCodeGetMemento(header)),
				header.employeeCode));

		// set year month
		
		PaymentCompanyDto companyDto = new PaymentCompanyDto();
		companyDto.setJapaneseYearMonth(this.convertYearMonthJP(header.qstdtPaymentHeaderPK.processingYM));
		reportData.setCompanyInfo(companyDto);

		return reportData;
	}

	/**
	 * Convert year month JP.
	 *
	 * @param yearMonth the year month
	 * @return the string
	 */
	private String convertYearMonthJP(Integer yearMonth) {
		String firstDay = "01";
		String tmpDate = yearMonth.toString().concat(firstDay);
		String dateFormat = "yyyyMMdd";
		GeneralDate generalDate = GeneralDate.fromString(tmpDate, dateFormat);
		JapaneseDate japaneseDate = this.japaneseProvider.toJapaneseDate(generalDate);
		return japaneseDate.era() + japaneseDate.year() + "年 " + japaneseDate.month() + "月度";
	}
}
