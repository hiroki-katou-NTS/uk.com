/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.accumulatedpayment;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.file.pr.app.export.accumulatedpayment.data.AccPaymentDataSource;
import nts.uk.file.pr.app.export.accumulatedpayment.data.AccPaymentHeaderData;
import nts.uk.file.pr.app.export.accumulatedpayment.data.AccPaymentItemData;
import nts.uk.file.pr.app.export.accumulatedpayment.query.AccPaymentReportQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseErasProvider;

/**
 * The Class AccPaymentReportService.
 */
@Stateless
public class AccPaymentReportService extends ExportService<AccPaymentReportQuery>{

	/** The generator. */
	@Inject
	private AccPaymentReportGenerator generator;

	/** The repository. */
	@Inject
	private AccPaymentRepository repository;
	
	/** The era provider. */
	@Inject
	private JapaneseErasProvider eraProvider;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<AccPaymentReportQuery> context) {

		// Get Query
		AccPaymentReportQuery query = context.getQuery();
		List<String> personIds = Arrays.asList("99900000-0000-0000-0000-000000000001",
				"99900000-0000-0000-0000-000000000002", "99900000-0000-0000-0000-000000000003",
				"99900000-0000-0000-0000-000000000004", "99900000-0000-0000-0000-000000000005",
				"99900000-0000-0000-0000-000000000006", "99900000-0000-0000-0000-000000000007",
				"99900000-0000-0000-0000-000000000008", "99900000-0000-0000-0000-000000000009",
				"99900000-0000-0000-0000-000000000010");
		query.setPIdList(personIds);
		
		// Validate.
		this.validateData(query);
		
		// Query data.
		List<AccPaymentItemData> items = this.repository.getItems(AppContexts.user().companyCode(), query);
		
		//  CONVERT YEARMONTH JAPANESE
		StringBuilder japanYear = new StringBuilder("【期間： ");
		japanYear.append(this.convertYearMonthJP(query.getTargetYear()));

		// Create header object.
		AccPaymentHeaderData headerData = AccPaymentHeaderData.builder()
				.departmentInfo("【部門： 役員　販売促進1課　役員～製造部　製造課　製造　（31部門）】")
				.empTypeInfo("【分類： 正社員～アルバイト（5分類）】")
				.positionInfo("【職位： 参事～主任（10職位）】")
				.yearMonthInfo(japanYear.toString())
				.build();

		// Create data source.
		val dataSource = AccPaymentDataSource
				.builder()
				.accPaymentItemData(items)
				.headerData(headerData)
				.build();

		// Call generator.
		this.generator.generate(context.getGeneratorContext(),dataSource, query);
	}
	
	/**
	 * Validate data.
	 *
	 * @param query the query
	 */
	private void validateData(AccPaymentReportQuery query) {
		if (query.getTargetYear() == null) {
			throw new RuntimeException("Target Year is Empty");
		}
		if (query.getTargetYear() < 1900 || query.getTargetYear() > 9999) {
			throw new RuntimeException("Target Year is not in range");
		}
		if (query.getIsLowerLimit() && query.getLowerLimitValue() == null) {
			throw new RuntimeException("金額範囲下限額 が入力されていません。");
		}
		if (query.getIsUpperLimit() && query.getUpperLimitValue() == null) {
			throw new RuntimeException("金額範囲上限額 が入力されていません。");
		}
		if (query.getIsLowerLimit() && query.getIsUpperLimit()
				&& (query.getLowerLimitValue().intValue() > query.getUpperLimitValue().intValue())) {
			throw new RuntimeException("金額の範囲が正しく指定されていません。");
		}
		if ((query.getIsLowerLimit() && query.getLowerLimitValue().intValue() < 0) 
				|| (query.getIsUpperLimit() && query.getUpperLimitValue().intValue() < 0)) {
			throw new RuntimeException("The Limit Values is not in range");
		}
	}

	/**
	 * Convert year month JP.
	 *
	 * @param targetYear the target year
	 * @return the string
	 */
	private String convertYearMonthJP(Integer targetYear) {
		String firstDay = "0101";
		String tmpDate = targetYear.toString().concat(firstDay);
		String dateFormat = "yyyyMMdd";
		GeneralDate generalDate = GeneralDate.fromString(tmpDate, dateFormat);
		JapaneseDate japaneseDate = this.eraProvider.toJapaneseDate(generalDate);
		return japaneseDate.era() + japaneseDate.year() + "年 " + "01月～12月迄】";
	}

}
