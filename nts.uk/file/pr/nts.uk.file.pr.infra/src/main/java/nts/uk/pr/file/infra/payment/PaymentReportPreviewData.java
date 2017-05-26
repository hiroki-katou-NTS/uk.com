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
	
	/**
	 * Gets the post card preview data.
	 *
	 * @return the post card preview data
	 */
	public static List<PaymentReportDto> getPostCardPreviewData() {
		PaymentReportDto dto = getCommonData();
		
		dto.setAttendanceItems(getPreviewTimeData(30));
		dto.setPaymentItems(getPreviewData(30));
		dto.setDeductionItems(getPreviewData(30));
		dto.setArticleItems(getPreviewData(7));
		dto.setOtherItems(getPreviewData(11));

		List<PaymentReportDto> list = createList(dto, 10);
		return list;
	}

	/**
	 * Gets the horizontal two preview data.
	 *
	 * @return the horizontal two preview data
	 */
	public static List<PaymentReportDto> getHorizontalTwoPreviewData() {
		PaymentReportDto dto = getCommonData();

		dto.setPaymentItems(getPreviewData(18));
		dto.setDeductionItems(getPreviewData(18));
		dto.setAttendanceItems(getPreviewTimeData(18));
		dto.setArticleItems(getPreviewData(9));
		dto.setOtherItems(getPreviewData(9));

		List<PaymentReportDto> list = createList(dto, 10);
		return list;
	}

	/**
	 * Gets the vertical two preview data.
	 *
	 * @return the vertical two preview data
	 */
	public static List<PaymentReportDto> getVerticalTwoPreviewData() {
		PaymentReportDto dto = getCommonData();

		dto.setPaymentItems(getPreviewData(45));
		dto.setDeductionItems(getPreviewData(45));
		dto.setAttendanceItems(getPreviewTimeData(45));
		dto.setArticleItems(getPreviewData(9));
		dto.setOtherItems(getPreviewData(9));

		List<PaymentReportDto> list = createList(dto, 10);
		return list;
	}

	/**
	 * Gets the vertical three preview data.
	 *
	 * @return the vertical three preview data
	 */
	public static List<PaymentReportDto> getVerticalThreePreviewData() {
		PaymentReportDto dto = getCommonData();

		dto.setPaymentItems(getPreviewData(18));
		dto.setDeductionItems(getPreviewData(18));
		dto.setAttendanceItems(getPreviewTimeData(18));
		dto.setArticleItems(getPreviewData(9));
		dto.setOtherItems(getPreviewData(9));

		List<PaymentReportDto> list = createList(dto, 10);
		return list;
	}

	/**
	 * Gets the vertical one preview data.
	 *
	 * @return the vertical one preview data
	 */
	public static List<PaymentReportDto> getVerticalOnePreviewData() {
		PaymentReportDto dto = getCommonData();
		dto.setPaymentItems(getPreviewData(81));
		dto.setDeductionItems(getPreviewData(81));
		dto.setAttendanceItems(getPreviewTimeData(81));
		dto.setArticleItems(getPreviewData(18));
		dto.setOtherItems(getPreviewData(9));

		List<PaymentReportDto> list = createList(dto, 10);
		return list;
	}

	/**
	 * Gets the z folded preview data.
	 *
	 * @return the z folded preview data
	 */
	public static List<PaymentReportDto> getZFoldedPreviewData() {
		PaymentReportDto dto = getCommonData();

		dto.setPaymentItems(getPreviewData(45));
		dto.setDeductionItems(getPreviewData(45));
		dto.setAttendanceItems(getPreviewTimeData(45));
		dto.setArticleItems(getPreviewData(18));
		dto.setOtherItems(getPreviewData(9));

		List<PaymentReportDto> list = createList(dto, 10);
		return list;
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
		dto.setRemark("ＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮＮ");
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
