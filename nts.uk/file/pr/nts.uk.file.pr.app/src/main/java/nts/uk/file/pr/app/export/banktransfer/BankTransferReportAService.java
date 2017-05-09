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
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferAReport;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferARpData;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferARpHeader;
import nts.uk.file.pr.app.export.banktransfer.query.BankTransferReportQuery;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BankTransferReportAService extends ExportService<BankTransferReportQuery> {

	@Inject
	private BankTransferRpAGenerator generator;

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
			List<BankTransferARpData> rpDataList = new ArrayList<BankTransferARpData>();
			for (String fromBranchId : query.getFromBranchId()) {
				BankTransferParam bankTransferParam = new BankTransferParam(companyCode, null, fromBranchId, 0, null,
						null, 0, null, query.getProcessingNo(), query.getProcessingYm(), PayBonusAtr.SALARY.value, 0,
						query.getPayDate());
				List<BankTransfer> bankTransfer = bankTransferRepo.findBySEL1_1(bankTransferParam);

				for (BankTransfer bankTrans : bankTransfer) {
					BankTransferARpData rpData = new BankTransferARpData();
					// A_DBD_002
					rpData.setBankName(bankTrans.getFromBank().getBankNameKana());
					// A_DBD_004
					rpData.setBranchName(bankTrans.getFromBank().getBranchNameKana());
					// A_DBD_006
					if (bankTrans.getToBank().getAccountAtr() == 0) {
						rpData.setToAccountAtr("普通");
					} else {
						rpData.setToAccountAtr("当座");
					}
					// A_DBD_007
					rpData.setToAccountNo(bankTrans.getToBank().getAccountNo());
					// A_DBD_010
					rpData.setPaymentMyn(bankTrans.getPaymentMoney().v());
					rpData.setUnit("円");
					// A_DBD_003
					rpData.setBankCode("0001");
					// A_DBD_005
					rpData.setBranchCode("101");

					// A_DBD_009
					rpData.setName("ワン");
					// A_DBD_008
					rpData.setScd("0000000002");

					if (query.getSelectedId_J_SEL_001() == 1) {
						// PERSON_BASE SEL_1
					}
					rpDataList.add(rpData);
				}
			}
			BankTransferARpData rpDataSum = new BankTransferARpData();
			rpDataSum.setBankCode("総合計");
			// A_CTR_002 (totalPaymentMny)
			BigDecimal sum = BigDecimal.valueOf(0);
			for (BankTransferARpData bankTransferARpData : rpDataList) {
				sum = sum.add(bankTransferARpData.getPaymentMyn());
			}
			rpDataSum.setPaymentMyn(sum);
			rpDataSum.setUnit("円");
			// A_CTR_001 (totalPerson)
			rpDataSum.setName(String.valueOf(rpDataList.size()) + "人");

			rpDataList.add(rpDataSum);

			BankTransferARpHeader header = new BankTransferARpHeader();
			header.setCompanyName("【日通システム株式会社】");
			header.setCode("0001 - 100 	給与分  	給与予備月 】");
			header.setDate("平成 29 年 5 月12日  】");
			// A_DBD_001
			header.setPerson("PERSON");

			if (query.getSparePayAtr().equals("3")) {
				if (query.getCurrentCode_J_SEL_004() == 2) {
					// A_CTR_003
					header.setState("【給与分】");
					// A_CTR_004
					// header.setState("【給与予備月】");
				} else {
					header.setState("");
				}
			} else {
				header.setState("");
			}

			BankTransferAReport reportA = new BankTransferAReport();
			reportA.setData(rpDataList);
			reportA.setHeader(header);

			this.generator.generator(context.getGeneratorContext(), reportA);
		}
	}

	private void process(BankTransferReportQuery query, String companyCode,
			ExportServiceContext<BankTransferReportQuery> context, int sparePayAtr) {
		List<BankTransferARpData> rpDataList = new ArrayList<BankTransferARpData>();
		for (String fromBranchId : query.getFromBranchId()) {
			BankTransferParam bankTransferParam = new BankTransferParam(companyCode, null, fromBranchId, 0, null, null,
					0, null, query.getProcessingNo(), query.getProcessingYm(), PayBonusAtr.SALARY.value, sparePayAtr,
					query.getPayDate());
			List<BankTransfer> bankTransfer = bankTransferRepo.findBySEL1(bankTransferParam);

			for (BankTransfer bankTrans : bankTransfer) {
				BankTransferARpData rpData = new BankTransferARpData();
				// A_DBD_002
				rpData.setBankName(bankTrans.getFromBank().getBankNameKana());
				// A_DBD_004
				rpData.setBranchName(bankTrans.getFromBank().getBranchNameKana());
				// A_DBD_006
				if (bankTrans.getToBank().getAccountAtr() == 0) {
					rpData.setToAccountAtr("普通");
				} else {
					rpData.setToAccountAtr("当座");
				}
				// A_DBD_007
				rpData.setToAccountNo(bankTrans.getToBank().getAccountNo());
				// A_DBD_010
				rpData.setPaymentMyn(bankTrans.getPaymentMoney().v());
				rpData.setUnit("円");
				// A_DBD_003
				rpData.setBankCode("0001");
				// A_DBD_005
				rpData.setBranchCode("101");

				// A_DBD_009
				rpData.setName("山");
				// A_DBD_008
				rpData.setScd("0000000001");

				if (query.getSelectedId_J_SEL_001() == 1) {
					// PERSON_BASE SEL_1
				}
				rpDataList.add(rpData);
			}
		}
		BankTransferARpData rpDataSum = new BankTransferARpData();
		rpDataSum.setBankCode("総合計");
		// A_CTR_002 (totalPaymentMny)
		BigDecimal sum = BigDecimal.valueOf(0);
		for (BankTransferARpData bankTransferARpData : rpDataList) {
			sum = sum.add(bankTransferARpData.getPaymentMyn());
		}
		rpDataSum.setPaymentMyn(sum);
		rpDataSum.setUnit("円");
		// A_CTR_001 (totalPerson)
		rpDataSum.setName(String.valueOf(rpDataList.size()) + "人");

		rpDataList.add(rpDataSum);

		BankTransferARpHeader header = new BankTransferARpHeader();
		header.setCompanyName("【日通システム株式会社】");
		header.setCode("0001 - 100 	給与分  	給与予備月 】");
		header.setDate("平成 29 年 5 月12日  】");
		// A_DBD_001
		header.setPerson("PERSON");

		if (query.getSparePayAtr().equals("3")) {
			if (query.getCurrentCode_J_SEL_004() == 2) {
				// A_CTR_003
				header.setState("【給与分】");
				// A_CTR_004
				// header.setState("【給与予備月】");
			} else {
				header.setState("");
			}
		} else {
			header.setState("");
		}

		BankTransferAReport reportA = new BankTransferAReport();
		reportA.setData(rpDataList);
		reportA.setHeader(header);

		this.generator.generator(context.getGeneratorContext(), reportA);
	}
}
