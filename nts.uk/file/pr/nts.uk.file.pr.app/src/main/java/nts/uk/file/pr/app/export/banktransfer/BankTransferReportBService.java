package nts.uk.file.pr.app.export.banktransfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.dom.enums.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.enums.SparePayAtr;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransfer;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransferParam;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransferRepository;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferBReport;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferBRpData;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferBRpHeader;
import nts.uk.file.pr.app.export.banktransfer.query.BankTransferReportQuery;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BankTransferReportBService extends ExportService<BankTransferReportQuery> {

	@Inject
	private BankTransferRpBGenerator generator;

	@Inject
	private BankTransferRepository bankTransferRepo;

	@Override
	protected void handle(ExportServiceContext<BankTransferReportQuery> context) {
		String companyCode = AppContexts.user().companyCode();
		BankTransferReportQuery query = context.getQuery();
		if (query.getSparePayAtr().equals("1")) {
			process(query, companyCode, context, SparePayAtr.NORMAL.value);
		} else if (query.getSparePayAtr().equals("2")) {
			process(query, companyCode, context, SparePayAtr.PRELIMINARY.value);
		} else {
			List<BankTransferBRpData> rpDataList = new ArrayList<BankTransferBRpData>();
			for (String fromBranchId : query.getFromBranchId()) {
				BankTransferParam bankTransferParam = new BankTransferParam(companyCode, null, fromBranchId, 0, null,
						null, 0, null, query.getProcessingNo(), query.getProcessingYm(), PayBonusAtr.SALARY.value, 0,
						query.getPayDate());
				List<BankTransfer> bankTransfer = bankTransferRepo.findBySEL1_1(bankTransferParam);

				for (BankTransfer bankTrans : bankTransfer) {
					BankTransferBRpData rpData = new BankTransferBRpData();
					// B_DBD_002
					rpData.setBankCode("001");
					// B_DBD_003
					rpData.setBankName("bankName");
					// B_DBD_004
					rpData.setBranchCode("101");
					// B_DBD_005
					rpData.setBranchName("branchName");
					// B_DBD_006
					if (bankTrans.getFromBank().getAccountAtr() == 0) {
						rpData.setFromAccountAtr("普通");
					} else {
						rpData.setFromAccountAtr("当座");
					}
					// B_DBD_007
					rpData.setFromAccountNo(bankTrans.getFromBank().getAccountNo());
					// B_DBD_008
					rpData.setNumPerSameType(15);
					rpData.setUnitPerson("人");
					// B_DBD_009
					rpData.setPaymentMyn(bankTrans.getPaymentMoney().v());
					rpData.setUnit("円");

					// PERSON_BASE SEL_1
					rpDataList.add(rpData);
				}
			}
			BankTransferBRpData rpDataSum = new BankTransferBRpData();
			rpDataSum.setBankCode("総合計");
			// B_CTR_002 (totalPaymentMny)
			BigDecimal sum = BigDecimal.valueOf(0);
			int totalPerson = 0;
			for (BankTransferBRpData bankTransferBRpData : rpDataList) {
				sum = sum.add(bankTransferBRpData.getPaymentMyn());
				totalPerson += bankTransferBRpData.getNumPerSameType();
			}
			rpDataSum.setPaymentMyn(sum);
			rpDataSum.setUnit("円");
			// B_CTR_001 (totalPerson)
			rpDataSum.setNumPerSameType(totalPerson);
			rpDataSum.setUnitPerson("人");

			rpDataList.add(rpDataSum);

			BankTransferBRpHeader header = new BankTransferBRpHeader();
			header.setCompanyName("【日通システム株式会社】");
			header.setStartCode("0001 - 100 	給与分  	給与予備月 ");
			header.setEndCode("9999 - 999	総合計	給与予備月 】");
			header.setDate("平成 29 年 5 月12日  】");
			// B_DBD_001
			header.setPerson("PERSON");

			BankTransferBReport reportB = new BankTransferBReport();
			reportB.setData(rpDataList);
			reportB.setHeader(header);

			this.generator.generator(context.getGeneratorContext(), reportB);
		}
	}

	private void process(BankTransferReportQuery query, String companyCode,
			ExportServiceContext<BankTransferReportQuery> context, int sparePayAtr) {

		List<BankTransferBRpData> rpDataList = new ArrayList<BankTransferBRpData>();
		for (String fromBranchId : query.getFromBranchId()) {
			BankTransferParam bankTransferParam = new BankTransferParam(companyCode, null, fromBranchId, 0, null, null,
					0, null, query.getProcessingNo(), query.getProcessingYm(), PayBonusAtr.SALARY.value, sparePayAtr,
					query.getPayDate());
			List<BankTransfer> bankTransfer = bankTransferRepo.findBySEL1(bankTransferParam);

			for (BankTransfer bankTrans : bankTransfer) {
				BankTransferBRpData rpData = new BankTransferBRpData();
				// B_DBD_002
				rpData.setBankCode("001");
				// B_DBD_003
				rpData.setBankName("bankName");
				// B_DBD_004
				rpData.setBranchCode("101");
				// B_DBD_005
				rpData.setBranchName("branchName");
				// B_DBD_006
				if (bankTrans.getFromBank().getAccountAtr() == 0) {
					rpData.setFromAccountAtr("普通");
				} else {
					rpData.setFromAccountAtr("当座");
				}
				// B_DBD_007
				rpData.setFromAccountNo(bankTrans.getFromBank().getAccountNo());
				// B_DBD_008
				rpData.setNumPerSameType(15);
				// A_DBD_009
				rpData.setPaymentMyn(bankTrans.getPaymentMoney().v());
				rpData.setUnit("円");
				rpData.setUnitPerson("人");
				// PERSON_BASE SEL_1
				rpDataList.add(rpData);
			}
		}
		BankTransferBRpData rpDataSum = new BankTransferBRpData();
		rpDataSum.setBankCode("総合計");
		// B_CTR_002 (totalPaymentMny)
		BigDecimal sum = BigDecimal.valueOf(0);
		int totalPerson = 0;
		for (BankTransferBRpData bankTransferBRpData : rpDataList) {
			sum = sum.add(bankTransferBRpData.getPaymentMyn());
			totalPerson += bankTransferBRpData.getNumPerSameType();
		}
		rpDataSum.setPaymentMyn(sum);
		rpDataSum.setUnit("円");
		// B_CTR_001 (totalPerson)
		rpDataSum.setNumPerSameType(totalPerson);
		rpDataSum.setUnitPerson("人");

		rpDataList.add(rpDataSum);

		BankTransferBRpHeader header = new BankTransferBRpHeader();
		header.setCompanyName("【日通システム株式会社】");
		header.setStartCode("0001 - 100 	給与分  	給与予備月 ");
		header.setEndCode("9999 - 999	総合計	給与予備月 】");
		header.setDate("平成 29 年 5 月12日  】");
		// B_DBD_001
		header.setPerson("PERSON");

		BankTransferBReport reportB = new BankTransferBReport();
		reportB.setData(rpDataList);
		reportB.setHeader(header);

		this.generator.generator(context.getGeneratorContext(), reportB);
	}
 
}