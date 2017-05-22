package nts.uk.file.pr.app.export.banktransfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.dom.enums.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.enums.SparePayAtr;
import nts.uk.file.pr.app.export.banktransfer.data.BankDto;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferCReport;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferCRpData;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferCRpHeader;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferParamRpDto;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferRpDto;
import nts.uk.file.pr.app.export.banktransfer.data.BranchDto;
import nts.uk.file.pr.app.export.banktransfer.data.PersonBankAccountDto;
import nts.uk.file.pr.app.export.banktransfer.query.BankTransferReportQuery;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BankTransferReportCService extends ExportService<BankTransferReportQuery> {

	@Inject
	private BankTransferRpCGenerator generator;

	@Inject
	private BankTransferReportRepository bankTransferReportRepo;

	@Override
	protected void handle(ExportServiceContext<BankTransferReportQuery> context) {
		String companyCode = AppContexts.user().companyCode();
		BankTransferReportQuery query = context.getQuery();
		if (query.getSparePayAtr() == 1) {
			process(query, companyCode, context, SparePayAtr.NORMAL.value);
		} else if (query.getSparePayAtr()== 2) {
			process(query, companyCode, context, SparePayAtr.PRELIMINARY.value);
		} else {
			process(query, companyCode, context, null);
		}
	}

	private void process(BankTransferReportQuery query, String companyCode,
			ExportServiceContext<BankTransferReportQuery> context, Integer sparePayAtr) {

		List<BankTransferCRpData> rpDataList = new ArrayList<BankTransferCRpData>();
		for (String fromBranchId : query.getFromBranchId()) {
			BankTransferParamRpDto bankTransferParamRp = new BankTransferParamRpDto(companyCode, fromBranchId,
					PayBonusAtr.SALARY.value, query.getProcessingNo(), query.getProcessingYm(), query.getPayDate(),
					sparePayAtr);
			// BANK_TRANSFER SEL_1
			List<BankTransferRpDto> bankTransfer = new ArrayList<BankTransferRpDto>();
			if (query.getSparePayAtr() == 3) {
				bankTransfer = bankTransferReportRepo.findBySEL1_1(bankTransferParamRp);
			} else {
				bankTransfer = bankTransferReportRepo.findBySEL1(bankTransferParamRp);
			}

			for (BankTransferRpDto bankTrans : bankTransfer) {
				BankTransferCRpData rpData = new BankTransferCRpData();
				// PERSON_BASE SEL_1
				// to-do : sau khi select trong personBase can check dieu kien
				// ton tai hay khong(throw ER010)

				Optional<PersonBankAccountDto> personBankAccountDto = bankTransferReportRepo
						.findPerBankAccBySEL3(companyCode, bankTrans.getPersonId(), query.getProcessingYm());
				if (!personBankAccountDto.isPresent()) {
					throw new BusinessException(new RawErrorMessage("対象データがありません。"));// ER010
				}
				Optional<BranchDto> branchDto = bankTransferReportRepo.findAllBranch(companyCode,
						bankTrans.getToBranchId());
				if (!branchDto.isPresent()) {
					throw new BusinessException(new RawErrorMessage("対象データがありません。"));// ER010
				}
				Optional<BankDto> bankDto = bankTransferReportRepo.findAllBank(companyCode,
						branchDto.get().getBankCode());
				if (!bankDto.isPresent()) {
					throw new BusinessException(new RawErrorMessage("対象データがありません。"));// ER010
				}
				// C_DBD_001
				rpData.setBankName(bankDto.get().getBankName());
				// C_DBD_002
				rpData.setBranchName(branchDto.get().getBranchName());
				// C_DBD_003
				if (bankTrans.getFromAccountAtr() == 0) {
					rpData.setFromAccountAtr("普通");
				} else {
					rpData.setFromAccountAtr("当座");
				}
				// C_DBD_004
				rpData.setFromAccountNo(bankTrans.getFromAccountNo());
				// C_DBD_005
				if (query.getProcessingNo() == 1) {
					rpData.setAccHolderName(personBankAccountDto.get().getAccountHolderName1());
				} else if (query.getProcessingNo() == 2) {
					rpData.setAccHolderName(personBankAccountDto.get().getAccountHolderName2());
				} else if (query.getProcessingNo() == 3) {
					rpData.setAccHolderName(personBankAccountDto.get().getAccountHolderName3());
				} else if (query.getProcessingNo() == 4) {
					rpData.setAccHolderName(personBankAccountDto.get().getAccountHolderName4());
				} else {
					rpData.setAccHolderName(personBankAccountDto.get().getAccountHolderName5());
				}

				// C_DBD_006
				rpData.setPaymentMny(bankTrans.getPaymentMoney());

				rpDataList.add(rpData);
			}
		}
		// BankTransferCRpData rpDataSum = new BankTransferCRpData();
		// BigDecimal totalMny = BigDecimal.ZERO;
		// // BigDecimal mnyPerPage = BigDecimal.ZERO;
		// rpDataSum.setTotalObjPerPage(rpDataList.size());
		// rpDataSum.setTotalObj(rpDataList.size());
		// for (int i = 0; i < rpDataList.size(); i++) {
		// totalMny.add(rpDataList.get(i).getPaymentMny());
		// }
		// rpDataSum.setTotalMnyPerPage(totalMny);
		// rpDataList.add(rpDataSum);
		// for (int i = 0; i < rpDataList.size(); i += 15) {
		// mnyPerPage.add(rpDataList.get(i).getPaymentMny());
		// }

		BankTransferCRpHeader header = new BankTransferCRpHeader();
		// C_DBD_007
		if (!bankTransferReportRepo.findRegalDocCnameSjis(companyCode).get().isEmpty()) {
			header.setCNameSJIS(bankTransferReportRepo.findRegalDocCnameSjis(companyCode).get());
		}
		header.setTotalObjPerPage(rpDataList.size());
		header.setTotalObj(rpDataList.size());
		BigDecimal totalMny = BigDecimal.ZERO;
		for (int i = 0; i < rpDataList.size(); i++) {
			totalMny = totalMny.add(rpDataList.get(i).getPaymentMny());
		}
		header.setTotalMnyPerPage(totalMny);
		header.setTotalMny(totalMny);
		BankTransferCReport reportC = new BankTransferCReport();
		reportC.setData(rpDataList);
		reportC.setHeader(header);

		this.generator.generate(context.getGeneratorContext(), reportC);
	}
}
