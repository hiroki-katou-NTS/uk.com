package nts.uk.file.pr.app.export.comparingsalarybonus;

import java.util.ArrayList;
import java.util.List;

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
import nts.uk.file.pr.app.export.comparingsalarybonus.data.PaymentDetail;
import nts.uk.file.pr.app.export.comparingsalarybonus.query.ComparingSalaryBonusQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

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

		// error1
		if (comparingQuery.getMonth1()== 0 || comparingQuery.getMonth2() == 0 ){
			throw new BusinessException(new RawErrorMessage("比較年月1、比較年月2 が入力されていません。"));
		} 
		// error 31
//		 if (comparingQuery.getMonth1() == comparingQuery.getMonth2()) {
//		 throw new BusinessException(new RawErrorMessage("設定が正しくありません。"));
//		 }

		List<ComparingFormDetail> lstComparingFormDetail = this.compareSalaryBonusQueryRepo
				.getPayComDetailByFormCode(companyCode, comparingQuery.getFormCode());
		/****************** EA2 ******************/
		lstComparingFormDetail.stream().forEach(c -> {
			List<DetailDifferential> lstDetailDifferentialEarlyYM = this.compareSalaryBonusQueryRepo
					.getDetailDifferentialWithEarlyYM(companyCode, comparingQuery.fakeDataList(),
							comparingQuery.getMonth1(), c.getCategoryAtr().value, comparingQuery.getPayBonusAttr(),
							c.getItemCode().toString());
			
			List<DetailDifferential> lstDetailDifferentialLaterYM = this.compareSalaryBonusQueryRepo
					.getDetailDifferentialWithLaterYM(companyCode, comparingQuery.fakeDataList(),
							comparingQuery.getMonth1(), c.getCategoryAtr().value, comparingQuery.getPayBonusAttr(),
							c.getItemCode().toString());
			//error 10
//			if(lstDetailDifferentialEarlyYM.isEmpty() || lstDetailDifferentialLaterYM.isEmpty()){
//				throw new BusinessException(new RawErrorMessage("対象データがありません。"));
//			}
			
			lstDetailDifferentialEarlyYM.stream().forEach(s -> {
				System.out.println(s.getCategoryAtr());
				
			});
			
			lstDetailDifferentialLaterYM.stream().forEach(s->{
				int i =0;
				System.out.println(i);
				i++;
			});
		});
		
		/**************************** EA3 *****************************/
		List<PaymentDetail> lstPaymentDetailEarlyYM = this.compareSalaryBonusQueryRepo.getPaymentDetail(companyCode, comparingQuery.fakeDataList()
				, comparingQuery.getPayBonusAttr(), comparingQuery.getMonth1());
		
		List<PaymentDetail> lstPaymentDetailLaterYM = this.compareSalaryBonusQueryRepo.getPaymentDetail(companyCode, comparingQuery.fakeDataList()
				, comparingQuery.getPayBonusAttr(), comparingQuery.getMonth2());
		
		ComparingSalaryBonusReportData reportData = fakeData();
		this.compareGenertor.generate(context.getGeneratorContext(), reportData);

	}

	private ComparingSalaryBonusReportData fakeData() {
		ComparingSalaryBonusReportData reportData = new ComparingSalaryBonusReportData();
		ComparingSalaryBonusHeaderReportData headerData = new ComparingSalaryBonusHeaderReportData();
		headerData.setTitleReport("明細金額比較表");
		headerData.setNameCompany("【日通 システム株式会社】");
		headerData.setNameDeparment("【部門： 】");
		headerData.setTypeDeparment("【分類： 】");
		headerData.setPostion("【職位： 】");
		headerData.setTargetYearMonth("【処理年月： 】");
		reportData.setHeaderData(headerData);

		return reportData;
	}

}
