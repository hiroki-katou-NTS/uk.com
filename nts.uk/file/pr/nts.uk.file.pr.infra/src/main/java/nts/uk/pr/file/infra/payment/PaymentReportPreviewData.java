/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.ArrayList;
import java.util.List;

import nts.uk.file.pr.app.export.payment.data.dto.PaymentCompanyDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentDepartmentDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentEmployeeDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentSalaryItemDto;

/**
 * The Class PaymentReportPreviewData.
 */
public class PaymentReportPreviewData {

	/** The Constant _30_ITEM. */
	public static final int _30_ITEM = 30;
	
	/** The Constant _7_ITEM. */
	public static final int _7_ITEM = 7;
	
	/** The Constant _11_ITEM. */
	public static final int _11_ITEM = 11;
	
	/** The Constant _45_ITEM. */
	public static final int _45_ITEM = 45;
	
	/** The Constant _18_ITEM. */
	public static final int _18_ITEM = 18;
	
	/** The Constant _9_ITEM. */
	public static final int _9_ITEM = 9;
	
	/** The Constant _81_ITEM. */
	public static final int _81_ITEM = 81;
	
	/** The Constant _1_PERSON. */
	public static final int _1_PERSON = 1;
	
	/** The Constant _2_PERSON. */
	public static final int _2_PERSON = 2;
	
	/** The Constant _3_PERSON. */
	public static final int _3_PERSON = 3;
	
	/** The Constant REMARK. */
	public static final String REMARK = "ＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮ";

	/**
	 * Gets the post card preview data.
	 *
	 * @return the post card preview data
	 */
	public static List<PaymentReportDto> getPostCardPreviewData() {
		PaymentReportDto dto = getCommonData();

		dto.setAttendanceItems(getPreviewTimeData(_30_ITEM));
		dto.setPaymentItems(getPreviewData(_30_ITEM));
		dto.setDeductionItems(getPreviewData(_30_ITEM));
		dto.setArticleItems(getPreviewData(_7_ITEM));
		dto.setOtherItems(getPreviewData(_11_ITEM));

		return createList(dto, _1_PERSON);
	}

	/**
	 * Gets the horizontal two preview data.
	 *
	 * @return the horizontal two preview data
	 */
	public static List<PaymentReportDto> getHorizontalTwoPreviewData() {
		PaymentReportDto dto = getCommonData();

		dto.setPaymentItems(getPreviewData(_18_ITEM));
		dto.setDeductionItems(getPreviewData(_18_ITEM));
		dto.setAttendanceItems(getPreviewTimeData(_18_ITEM));
		dto.setArticleItems(getPreviewData(_9_ITEM));
		dto.setOtherItems(getPreviewData(_9_ITEM));

		return createList(dto, _2_PERSON);
	}

	/**
	 * Gets the vertical two preview data.
	 *
	 * @return the vertical two preview data
	 */
	public static List<PaymentReportDto> getVerticalTwoPreviewData() {
		PaymentReportDto dto = getCommonData();

		dto.setPaymentItems(getPreviewData(_45_ITEM));
		dto.setDeductionItems(getPreviewData(_45_ITEM));
		dto.setAttendanceItems(getPreviewTimeData(_45_ITEM));
		dto.setArticleItems(getPreviewData(_9_ITEM));
		dto.setOtherItems(getPreviewData(_9_ITEM));

		return createList(dto, _2_PERSON);
	}

	/**
	 * Gets the vertical three preview data.
	 *
	 * @return the vertical three preview data
	 */
	public static List<PaymentReportDto> getVerticalThreePreviewData() {
		PaymentReportDto dto = getCommonData();

		dto.setPaymentItems(getPreviewData(_18_ITEM));
		dto.setDeductionItems(getPreviewData(_18_ITEM));
		dto.setAttendanceItems(getPreviewTimeData(_18_ITEM));
		dto.setArticleItems(getPreviewData(_9_ITEM));
		dto.setOtherItems(getPreviewData(_9_ITEM));

		return createList(dto, _3_PERSON);
	}

	/**
	 * Gets the vertical one preview data.
	 *
	 * @return the vertical one preview data
	 */
	public static List<PaymentReportDto> getVerticalOnePreviewData() {
		PaymentReportDto dto = getCommonData();
		dto.setPaymentItems(getPreviewData(_81_ITEM));
		dto.setDeductionItems(getPreviewData(_81_ITEM));
		dto.setAttendanceItems(getPreviewTimeData(_81_ITEM));
		dto.setArticleItems(getPreviewData(_9_ITEM));
		dto.setOtherItems(getPreviewData(_9_ITEM));

		return createList(dto, _1_PERSON);
	}

	/**
	 * Gets the z folded preview data.
	 *
	 * @return the z folded preview data
	 */
	public static List<PaymentReportDto> getZFoldedPreviewData() {
		PaymentReportDto dto = getCommonData();

		dto.setPaymentItems(getPreviewData(_45_ITEM));
		dto.setDeductionItems(getPreviewData(_45_ITEM));
		dto.setAttendanceItems(getPreviewTimeData(_45_ITEM));
		dto.setArticleItems(getPreviewData(_18_ITEM));
		dto.setOtherItems(getPreviewData(_9_ITEM));

		return createList(dto, _1_PERSON);
	}

	/**
	 * Gets the common data.
	 *
	 * @return the common data
	 */
	private static PaymentReportDto getCommonData() {
		PaymentReportDto dto = new PaymentReportDto();
		dto.setDepartmentInfo(PaymentDepartmentDto.getPreviewData());
		dto.setCompanyInfo(PaymentCompanyDto.getPreviewData());
		dto.setEmployeeInfo(PaymentEmployeeDto.getPreviewData());
		dto.setRemark(REMARK);
		return dto;
	}

	/**
	 * Gets the preview time data.
	 *
	 * @param numberOfItem the number of item
	 * @return the preview time data
	 */
	private static List<PaymentSalaryItemDto> getPreviewTimeData(int numberOfItem) {
		List<PaymentSalaryItemDto> items = new ArrayList<PaymentSalaryItemDto>();
		for (int i = 0; i < numberOfItem; i++) {
			PaymentSalaryItemDto item = PaymentSalaryItemDto.getPreviewTimeData();
			items.add(item);
		}
		return items;
	}

	/**
	 * Gets the preview data.
	 *
	 * @param numberOfItem the number of item
	 * @return the preview data
	 */
	private static List<PaymentSalaryItemDto> getPreviewData(int numberOfItem) {
		List<PaymentSalaryItemDto> items = new ArrayList<PaymentSalaryItemDto>();
		for (int i = 0; i < numberOfItem; i++) {
			PaymentSalaryItemDto item = PaymentSalaryItemDto.getPreviewData();
			items.add(item);
		}
		return items;
	}

	/**
	 * Creates the list.
	 *
	 * @param employee the employee
	 * @param numberOfEmployee the number of employee
	 * @return the list
	 */
	private static List<PaymentReportDto> createList(PaymentReportDto employee, int numberOfEmployee) {
		List<PaymentReportDto> dtos = new ArrayList<PaymentReportDto>();
		for (int i = 0; i < numberOfEmployee; i++) {
			dtos.add(employee);
		}
		return dtos;
	}
}
