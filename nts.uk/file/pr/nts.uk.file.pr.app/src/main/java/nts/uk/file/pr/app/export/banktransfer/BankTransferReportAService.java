package nts.uk.file.pr.app.export.banktransfer;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.dom.enums.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.enums.SparePayAtr;
import nts.uk.file.pr.app.export.banktransfer.data.BankDto;
import nts.uk.file.pr.app.export.banktransfer.data.BankReportData;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferARpData;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferARpHeader;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferParamRpDto;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferRpDto;
import nts.uk.file.pr.app.export.banktransfer.data.BranchDto;
import nts.uk.file.pr.app.export.banktransfer.query.BankTransferReportQuery;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BankTransferReportAService extends ExportService<BankTransferReportQuery> {

	@Inject
	private BankTransferRpAGenerator generator;

	@Inject
	private BankTransferReportRepository bankTransferReportRepo;

	@Override
	protected void handle(ExportServiceContext<BankTransferReportQuery> context) {
		String companyCode = AppContexts.user().companyCode();
		BankTransferReportQuery query = context.getQuery();
		if (query.getSparePayAtr() == 1) {
			process(query, companyCode, context, SparePayAtr.NORMAL.value);
		} else if (query.getSparePayAtr() == 2) {
			process(query, companyCode, context, SparePayAtr.PRELIMINARY.value);
		} else {
			process(query, companyCode, context, 0);
		}
	}

	private void process(BankTransferReportQuery query, String companyCode,
			ExportServiceContext<BankTransferReportQuery> context, int sparePayAtr) {
		Map<String, String> persons = fixPerson();
		
		List<BankTransferARpData> rpBankDataList = new ArrayList<>();
		
		// get branch
		List<BranchDto> branchList = bankTransferReportRepo.findAllBranch(companyCode, query.getFromBranchId());
		Map<String, BranchDto> branchMap = branchList.stream().collect(Collectors.toMap(BranchDto::getBranchId, x -> x));
		
		for (String fromBranchId : query.getFromBranchId()) {
			BankTransferARpData rpBankData = new BankTransferARpData();
			
			// header
			BranchDto branch = branchMap.get(fromBranchId);
			String title = MessageFormat.format("{0} - {1} {2} {3}　】", branch.getBankCode(), branch.getBranchCode(), branch.getBankName(), branch.getBranchName());
			BankTransferARpHeader header = getHeader(query, companyCode, title);
			rpBankData.setHeader(header);
			
			BankTransferParamRpDto bankTransferParamRp = new BankTransferParamRpDto(companyCode, fromBranchId,
					PayBonusAtr.SALARY.value, query.getProcessingNo(), query.getProcessingYm(), query.getPayDate(),
					sparePayAtr);
			
			// BANK_TRANSFER SEL_1
			List<BankTransferRpDto> bankTransfer = new ArrayList<BankTransferRpDto>();
			if (query.getSparePayAtr()== 3) {
				bankTransfer = bankTransferReportRepo.findBySEL1_1(bankTransferParamRp);
			} else {
				bankTransfer = bankTransferReportRepo.findBySEL1(bankTransferParamRp);
			}
			
			List<BankReportData> salaryDataList = new ArrayList<>();
			for (BankTransferRpDto bankTrans : bankTransfer) {
				BankReportData rpData = new BankReportData();
				// A_DBD_008 (PERSON_COM)
				rpData.setScd(persons.get(bankTrans.getPersonId()));
				// A_DBD_009 (PERSON_BASE)
				rpData.setName("個人 郎");
				if (query.getSelectedId_J_SEL_001() == 1) {
					// PERSON_BASE SEL_1
				}

				Optional<BranchDto> branchDto = bankTransferReportRepo.findAllBranch(companyCode,
						bankTrans.getToBranchId());
				if (!branchDto.isPresent()) {
					throw new BusinessException(new RawErrorMessage("対象データがありません。"));//ER010
				}
				Optional<BankDto> bankDto = bankTransferReportRepo.findAllBank(companyCode,
						branchDto.get().getBankCode());
				if (!bankDto.isPresent()) {
					throw new BusinessException(new RawErrorMessage("対象データがありません。"));//ER010
				}
				if (query.getCurrentCode_J_SEL_004() == 0) {
					// to-do
				}
				// A_DBD_002
				rpData.setBankCode(bankDto.get().getBankCode());
				// A_DBD_003
				rpData.setBankName(bankDto.get().getBankName());
				// A_DBD_004
				rpData.setBranchCode(branchDto.get().getBranchCode());
				// A_DBD_005
				rpData.setBranchName(branchDto.get().getBranchName());

				// A_DBD_006
				if (bankTrans.getToAccountAtr() == 0) {
					rpData.setToAccountAtr("普通");
				} else {
					rpData.setToAccountAtr("当座");
				}
				
				// A_DBD_007
				rpData.setToAccountNo(bankTrans.getToAccountNo());
				// A_DBD_010
				rpData.setPaymentMyn(bankTrans.getPaymentMoney());
				rpData.setUnit("円");

				rpData.setSparePayAtr(bankTrans.getSparePayAtr());
				
				rpData.setRowTotal(false);
				
				salaryDataList.add(rpData);	
			}
			
			// row total
			this.totalSalary(salaryDataList);
			rpBankData.setDataSalaryList(salaryDataList);
			
			rpBankDataList.add(rpBankData);
		}		

		this.generator.generator(context.getGeneratorContext(), rpBankDataList);
	}

	private BankTransferARpHeader getHeader(BankTransferReportQuery query, String companyCode, String fromBank) {
		// set header
		BankTransferARpHeader header = new BankTransferARpHeader();
		header.setCompanyName(bankTransferReportRepo.findCompany(companyCode).getCompanyName());
		// A_CTR_005
		header.setCode(fromBank);
		// A_CTR_006
		header.setDate(query.getTransferDate() + "　】");
		// A_DBD_001
		header.setPerson(bankTransferReportRepo.findAllCalled(companyCode).get());
		return header;
	}
	
	/**
	 * Total salary preliminary month
	 */
	private void totalSalary(List<BankReportData> salaryPreliminaryMonthList) {
		if (salaryPreliminaryMonthList == null) {
			return;
		}
		
		BankReportData rpDataSum = new BankReportData();
		rpDataSum.setBankCode("総合計");
		// A_CTR_002 (totalPaymentMny)
		BigDecimal sum = BigDecimal.valueOf(0);
		for (BankReportData bankTransferARpData : salaryPreliminaryMonthList) {
			sum = sum.add(bankTransferARpData.getPaymentMyn());
		}
		rpDataSum.setPaymentMyn(sum);
		rpDataSum.setUnit("円");
		// A_CTR_001 (totalPerson)
		rpDataSum.setName(String.valueOf(salaryPreliminaryMonthList.size()) + "人");
		rpDataSum.setRowTotal(true);
		
		salaryPreliminaryMonthList.add(rpDataSum);
	}
	
	/**
	 * Fix data
	 * @return
	 */
	private Map<String, String> fixPerson() {
		Map<String, String> result = new HashMap<>();
		for(int i=0; i< 10; i++) {
			result.put("99900000-0000-0000-0000-00000000000" + (i+1), "000000000" + (i +1));
		}
		
		return result;
	}
}
