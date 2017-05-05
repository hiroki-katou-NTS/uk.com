package nts.uk.file.pr.app.export.comparingsalarybonus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormDetail;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusHeaderReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DeparmentInf;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.EmployeeInf;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.HeaderTable;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.PaymentDetail;
import nts.uk.file.pr.app.export.comparingsalarybonus.query.ComparingSalaryBonusQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.japanese.JapaneseErasProvider;

@Stateless
public class ComparingSalaryBonusReportService extends ExportService<ComparingSalaryBonusQuery> {

	@Inject
	private ComparingSalaryBonusGenerator compareGenertor;

	@Inject
	private ComparingSalaryBonusQueryRepository compareSalaryBonusQueryRepo;

	@Override
	protected void handle(ExportServiceContext<ComparingSalaryBonusQuery> context) {
		ComparingSalaryBonusQuery comparingQuery = context.getQuery();
		LoginUserContext loginUserContext = AppContexts.user();
		String companyCode = loginUserContext.companyCode();
//
//		// error1
//		if (comparingQuery.getMonth1() == 0 || comparingQuery.getMonth2() == 0) {
//			throw new BusinessException(new RawErrorMessage("比較年月1、比較年月2 が入力されていません。"));
//		}
//		// error 31
//		if (comparingQuery.getMonth1() == comparingQuery.getMonth2()) {
//			throw new BusinessException(new RawErrorMessage("設定が正しくありません。"));
//		}
//
//		List<ComparingFormDetail> lstComparingFormDetail = this.compareSalaryBonusQueryRepo
//				.getPayComDetailByFormCode(companyCode, comparingQuery.getFormCode());
//
//		/****************** EA2 ******************/
//
//		List<DetailDifferential> lstDetailDifferentialEarlyYM = new ArrayList<DetailDifferential>();
//		List<DetailDifferential> lstDetailDifferentialLaterYM = new ArrayList<DetailDifferential>();
//		lstComparingFormDetail.stream().forEach(c -> {
//
//			lstDetailDifferentialEarlyYM.addAll(this.compareSalaryBonusQueryRepo.getDetailDifferentialWithEarlyYM(
//					companyCode, comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(),
//					c.getCategoryAtr().value, comparingQuery.getPayBonusAttr(), c.getItemCode().toString()));
//			lstDetailDifferentialLaterYM.addAll(this.compareSalaryBonusQueryRepo.getDetailDifferentialWithLaterYM(
//					companyCode, comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(),
//					c.getCategoryAtr().value, comparingQuery.getPayBonusAttr(), c.getItemCode().toString()));
//		});
//		// error 10
//		if (lstDetailDifferentialEarlyYM.isEmpty() || lstDetailDifferentialLaterYM.isEmpty()) {
//			throw new BusinessException(new RawErrorMessage("対象データがありません。"));
//		}
//
//		/**************************** EA3 *****************************/
//		List<PaymentDetail> lstPaymentDetailEarlyYM = this.compareSalaryBonusQueryRepo.getPaymentDetail(companyCode,
//				comparingQuery.getEmployeeCodeList(), comparingQuery.getPayBonusAttr(), comparingQuery.getMonth1());
//
//		List<PaymentDetail> lstPaymentDetailLaterYM = this.compareSalaryBonusQueryRepo.getPaymentDetail(companyCode,
//				comparingQuery.getEmployeeCodeList(), comparingQuery.getPayBonusAttr(), comparingQuery.getMonth2());
//		int comparingMonth = comparingQuery.getMonth2() - comparingQuery.getMonth1();
//		if (comparingMonth > 0) {
//			System.out.println("not same line");
//		} else {
//
//			System.out.println("same line");
//		}

		ComparingSalaryBonusReportData reportData = fakeData(companyCode, comparingQuery);
		this.compareGenertor.generate(context.getGeneratorContext(), reportData);

	}

	private ComparingSalaryBonusReportData fakeData(String companyCode, ComparingSalaryBonusQuery comparingQuery) {
		ComparingSalaryBonusReportData reportData = new ComparingSalaryBonusReportData();
		List<ComparingSalaryBonusHeaderReportData> lstheaderData;
		lstheaderData = this.compareSalaryBonusQueryRepo.getReportHeader(companyCode);
		if (lstheaderData.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("not data"));
		}
		ComparingSalaryBonusHeaderReportData headerData = new ComparingSalaryBonusHeaderReportData();
		int max = lstheaderData.size();
		headerData.setTitleReport("明細金額比較表");
		headerData.setNameCompany("【 " + lstheaderData.get(0).getNameCompany() + " 】");
		headerData.setNameDeparment("【部門：" + lstheaderData.get(0).getNameDeparment() + "~"
				+ lstheaderData.get(max - 1).getNameDeparment() + "】");
		headerData.setTypeDeparment("【分類： " + lstheaderData.get(0).getTypeDeparment() + " ~ "
				+ lstheaderData.get(max - 1).getTypeDeparment() + " 】");
		headerData.setPostion(
				"【職位：  " + lstheaderData.get(0).getPostion() + " ~ " + lstheaderData.get(max - 1).getPostion() + " 】");
		headerData.setTargetYearMonth("【処理年月：  " + lstheaderData.get(0).getTargetYearMonth() + "】");
		reportData.setHeaderData(headerData);
		
		HeaderTable headerTable = new HeaderTable();
		headerTable.setItemName("項目名称");
		headerTable.setMonth1("平成  29 年  3月");
		headerTable.setMonth2("平成  29 年  4月");
		headerTable.setDifferentSalary("差額");
		headerTable.setRegistrationStatus1("登録状況1 (平成  29 年  3月)");
		headerTable.setRegistrationStatus2("登録状況2 (平成  29 年  4月)");
		headerTable.setReason(" 差異理由");
		headerTable.setConfirmed("確認済");
		reportData.setHeaderTable(headerTable);

		DeparmentInf deparmentInf = new DeparmentInf();
		deparmentInf.setDepcode("000001");
		deparmentInf.setDepname("部門1");
		reportData.setDeparmentInf(deparmentInf);

		EmployeeInf employeeInf = new EmployeeInf();
		employeeInf.setEmpcode("99900000-0000-0000-0000-000000000004");
		employeeInf.setEmpname("A");
		reportData.setEmployeeInf(employeeInf);
		return reportData;
	}

}
