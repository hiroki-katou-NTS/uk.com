/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.paymentdata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.infra.entity.company.CmnmtCompany;
import nts.uk.ctx.basic.infra.entity.organization.employment.CmnmtEmp;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItem;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.report.app.payment.refundsetting.refundpadding.find.RefundPaddingFinder;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsCode;
import nts.uk.ctx.pr.report.dom.payment.contact.ContactItemsSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.payment.report.PadmtPersonResiadd;
import nts.uk.ctx.pr.report.infra.entity.payment.report.PadmtPersonResiaddPK_;
import nts.uk.ctx.pr.report.infra.entity.payment.report.PadmtPersonResiadd_;
import nts.uk.file.pr.app.export.payment.PaymentReportQuery;
import nts.uk.file.pr.app.export.payment.PaymentReportRepository;
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentCompanyDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentDepartmentDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentEmployeeDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentRefundPaddingDto;
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

	/** The refund padding finder. */
	@Inject
	private RefundPaddingFinder refundPaddingFinder;
	
	/** The Constant ITEM_CODE_TAX_TOTAL. */
	// 課税合計 ITEM_NAME OF QCAMT_ITEM
	private static final String ITEM_CODE_TAX_TOTAL = "F001";
	
	/** The Constant ITEM_CODE_TAX_EXEMPTION_TOTAL. */
	// 非課税合計 ITEM_NAME OF QCAMT_ITEM
	private static final String ITEM_CODE_TAX_EXEMPTION_TOTAL = "F002";
	
	/** The Constant ITEM_CODE_PAYMENT_TOTAL. */
	//支給合計 ITEM_NAME OF QCAMT_ITEM
	private static final String ITEM_CODE_PAYMENT_TOTAL = "F003";
	
	/** The Constant ITEM_CODE_SOCIAL_INSURANCE_TOTAL. */
	// 社会保険合計 ITEM_NAME OF QCAMT_ITEM
	private static final String ITEM_CODE_SOCIAL_INSURANCE_TOTAL = "F105";
	
	/** The Constant ITEM_CODE_TAXABLE_AMOUNT. */
	// 課税対象額 ITEM_NAME OF QCAMT_ITEM
	private static final String ITEM_CODE_TAXABLE_AMOUNT = "F106";
	
	/** The Constant ITEM_CODE_DEDUCTION_TOTAL. */
	// 控除合計 ITEM_NAME OF QCAMT_ITEM
	private static final String ITEM_CODE_DEDUCTION_TOTAL = "F114";
	
	/** The Constant ITEM_CODE_SUBSCRIPTION_AMOUNT. */
	// 差引支給額  ITEM_NAME OF QCAMT_ITEM
	private static final String ITEM_CODE_SUBSCRIPTION_AMOUNT = "F309";
	
	/** The Constant ITEM_CODE_TAXABLE_TOTAL. */
	// 課税対象累計 ITEM_NAME OF QCAMT_ITEM
	private static final String ITEM_CODE_TAXABLE_TOTAL ="F301";

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

	/** The Constant FIND_COMPANY_BYCODE. */
	public static final String FIND_COMPANY_BYCODE ="SELECT com FROM CmnmtCompany com "
		+"WHERE com.cmnmtCompanyPk.companyCd = :companyCode";
	
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

	/** The Constant TOTAL_PAYMENT_DETAIL_BY_ITEM_CODE. */
	public static final String TOTAL_PAYMENT_DETAIL_BY_ITEM_CODE ="SELECT SUM(detail.value) "
		+ "FROM QstdtPaymentDetail detail "
		+ "WHERE detail.qstdtPaymentDetailPK.companyCode = :companyCode "
		+ "AND detail.qstdtPaymentDetailPK.personId = :personId "
		+ "AND detail.qstdtPaymentDetailPK.processingNo = :processingNo "
		+ "AND detail.qstdtPaymentDetailPK.payBonusAttribute = 0 "
		+ "AND detail.qstdtPaymentDetailPK.processingYM = :processingYM "
		+ "AND detail.qstdtPaymentDetailPK.sparePayAttribute = 0 "
		+ "AND detail.qstdtPaymentDetailPK.itemCode = :itemCode ";
	
	
	
	/**
	 * Find company code.
	 *
	 * @param companyCode the company code
	 * @return the optional
	 */
	private Optional<CmnmtCompany> findCompanyCode(String companyCode) {
		return this.queryProxy().query(FIND_COMPANY_BYCODE, CmnmtCompany.class)
			.setParameter("companyCode", companyCode).getSingle();
	}
	
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
			.setParameter("processingNo", query.getProcessingNo()).setParameter("layoutItem", 0)
			// TODO FAKE LAYOUT ITEM
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
			.setParameter("companyCode", companyCode).setParameter("employmentCode", employeeCode)
			.getSingle();
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

		// set config data query
		PaymentRefundPaddingDto config = new PaymentRefundPaddingDto();
		int printType = 0;
		switch (query.getLayoutItems()) {
		case 0:
			printType = 1;
			break;
		case 4:
			printType = 1;
			break;
		case 5:
			printType = 1;
			break;
		case 1:
			printType = 2;
			break;
		case 3:
			printType = 2;
			break;
		case 2:
			printType = 3;
			break;
		default:
			break;
		}

		// set config
		config.setPrintType(printType);
		switch (printType) {
		case 1:
			config.setRefundPaddingOnceDto(this.refundPaddingFinder.findPrintTypeOne());
			break;
		case 2:
			config.setRefundPaddingTwoDto(this.refundPaddingFinder.findPrintTypeTwo());
			break;
		case 3:
			config.setRefundPaddingThreeDto(this.refundPaddingFinder.findPrintTypeThree());
			break;

		default:
			break;
		}
		reportData.setConfig(config);
		
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

	private List<PaymentSalaryItemDto> defaultDataBeginEnd(int startLine, int endLine,
		int startColum, int endColum) {
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
		} else {
			employeeDto.setEmployeeCode(header.employeeCode);
			employeeDto.setEmployeeName(header.employeeName);
		}


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
		companyDto.setJapaneseYearMonth(
			this.convertYearMonthJP(header.qstdtPaymentHeaderPK.processingYM));
		companyDto.setName(header.companyName);
		Optional<CmnmtCompany> company = this
			.findCompanyCode(header.qstdtPaymentHeaderPK.companyCode);
		if(company.isPresent()){
			companyDto.setPostalCode(company.get().postal);
			companyDto.setAddressOne(company.get().address1);
			companyDto.setAddressTwo(company.get().address2);
		}
		reportData.setCompanyInfo(companyDto);
		
		Optional<PadmtPersonResiadd> persionInfo = this
			.findPersionInfo(header.qstdtPaymentHeaderPK.personId);
		
		//set info employee
		if(persionInfo.isPresent()){
			employeeDto.setPostalCode(persionInfo.get().getPostal());
			employeeDto.setAddressOne(persionInfo.get().getAddress1());
			employeeDto.setAddressTwo(persionInfo.get().getAddress2());			
		}
		
		Optional<BigDecimal> taxTotal = this.totalPaymentDetailByItemCode(header,
			ITEM_CODE_TAX_TOTAL);
		employeeDto.setTaxTotal(BigDecimal.ZERO);
		if(taxTotal.isPresent()){
			employeeDto.setTaxTotal(taxTotal.get());
		}
		
		Optional<BigDecimal> taxExemptionTotal = this.totalPaymentDetailByItemCode(header,
			ITEM_CODE_TAX_EXEMPTION_TOTAL);
		employeeDto.setTaxExemptionTotal(BigDecimal.ZERO);
		if(taxExemptionTotal.isPresent()){
			employeeDto.setTaxExemptionTotal(taxExemptionTotal.get());
		}
		
		Optional<BigDecimal> paymentTotal = this.totalPaymentDetailByItemCode(header,
			ITEM_CODE_PAYMENT_TOTAL);
		employeeDto.setPaymentTotal(BigDecimal.ZERO);
		if(paymentTotal.isPresent()){
			employeeDto.setPaymentTotal(paymentTotal.get());
		}
		
		Optional<BigDecimal> socialInsuranceTotal = this.totalPaymentDetailByItemCode(header,
			ITEM_CODE_SOCIAL_INSURANCE_TOTAL);
		employeeDto.setSocialInsuranceTotal(BigDecimal.ZERO);
		if(socialInsuranceTotal.isPresent()){
			employeeDto.setSocialInsuranceTotal(socialInsuranceTotal.get());
		}
		
		Optional<BigDecimal> taxableAmount = this.totalPaymentDetailByItemCode(header,
			ITEM_CODE_TAXABLE_AMOUNT);
		employeeDto.setTaxableAmount(BigDecimal.ZERO);
		if(taxableAmount.isPresent()){
			employeeDto.setTaxableAmount(taxableAmount.get());
		}
		
		Optional<BigDecimal> deductionTotal = this.totalPaymentDetailByItemCode(header,
			ITEM_CODE_DEDUCTION_TOTAL);
		employeeDto.setDeductionTotal(BigDecimal.ZERO);
		if(deductionTotal.isPresent()){
			employeeDto.setDeductionTotal(deductionTotal.get());
		}
		
		Optional<BigDecimal> subscriptionAmount = this.totalPaymentDetailByItemCode(header,
			ITEM_CODE_SUBSCRIPTION_AMOUNT);
		employeeDto.setSubscriptionAmount(BigDecimal.ZERO);
		if(subscriptionAmount.isPresent()){
			employeeDto.setSubscriptionAmount(subscriptionAmount.get());
		}
		
		Optional<BigDecimal> taxableTotal = this.totalPaymentDetailByItemCode(header,
			ITEM_CODE_TAXABLE_TOTAL);
		employeeDto.setTaxableTotal(BigDecimal.ZERO);
		if(taxableTotal.isPresent()){
			employeeDto.setTaxableTotal(taxableTotal.get());
		}
		reportData.setEmployeeInfo(employeeDto);
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
	
	/**
	 * Find persion info.
	 *
	 * @param persionId the persion id
	 * @return the optional
	 */
	private Optional<PadmtPersonResiadd> findPersionInfo(String persionId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call QISMT_LABOR_INSU_OFFICE (QismtLaborInsuOffice SQL)
		CriteriaQuery<PadmtPersonResiadd> cq = criteriaBuilder
			.createQuery(PadmtPersonResiadd.class);

		// root data
		Root<PadmtPersonResiadd> root = cq.from(PadmtPersonResiadd.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal persionId
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(PadmtPersonResiadd_.padmtPersonResiaddPK).get(PadmtPersonResiaddPK_.pid),
			persionId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<PadmtPersonResiadd> query = em.createQuery(cq);

		// exclude select
		List<PadmtPersonResiadd> lstPersion = query.getResultList();
		if (CollectionUtil.isEmpty(lstPersion)) {
			return Optional.empty();
		}
		return Optional.ofNullable(lstPersion.get(0));

	}
	
	/**
	 * Total payment detail by item code.
	 *
	 * @param header the header
	 * @param itemCode the item code
	 * @return the optional
	 */
	private Optional<BigDecimal> totalPaymentDetailByItemCode(QstdtPaymentHeader header,
		String itemCode) {
		return this.queryProxy().query(TOTAL_PAYMENT_DETAIL_BY_ITEM_CODE, BigDecimal.class)
			.setParameter("companyCode", header.qstdtPaymentHeaderPK.companyCode)
			.setParameter("processingNo", header.qstdtPaymentHeaderPK.processingNo)
			.setParameter("personId", header.qstdtPaymentHeaderPK.personId)
			.setParameter("processingYM", header.qstdtPaymentHeaderPK.processingYM)
			.setParameter("itemCode", itemCode).getSingle();
	}
	

}
