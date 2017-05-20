package nts.uk.file.pr.app.export.banktransfer;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
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
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferBReport;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferBRpData;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferBRpHeader;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferParamRpDto;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferRpDto;
import nts.uk.file.pr.app.export.banktransfer.data.BranchDto;
import nts.uk.file.pr.app.export.banktransfer.query.BankTransferReportQuery;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BankTransferReportBService extends ExportService<BankTransferReportQuery> {

	@Inject
	private BankTransferRpBGenerator generator;

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
		// get branch
		List<BranchDto> branchList = bankTransferReportRepo.findAllBranch(companyCode, query.getFromBranchId());
		Map<String, BranchDto> branchMap = branchList.stream().collect(Collectors.toMap(BranchDto::getBranchId, x -> x));
				
		List<BankTransferBRpData> rpDataList = new ArrayList<BankTransferBRpData>();
		
		BankTransferBRpHeader header = new BankTransferBRpHeader();
		
		int index = 0;
		for (String fromBranchId : query.getFromBranchId()) {
			if (index == 0) {
				BranchDto branch = branchMap.get(fromBranchId);
				String title = MessageFormat.format("{0} - {1} {2} {3}", branch.getBankCode(), branch.getBranchCode(), branch.getBankName(), branch.getBranchName());
				header.setStartCode(title);
			}
			
			if (index == (query.getFromBranchId().size() - 1)) {
				BranchDto branch = branchMap.get(fromBranchId);
				String title = MessageFormat.format("{0} - {1} {2} {3}　】", branch.getBankCode(), branch.getBranchCode(), branch.getBankName(), branch.getBranchName());
				header.setEndCode(title);
			}
			
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
				BankTransferBRpData rpData = new BankTransferBRpData();
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
					// to- do
				}
				// B_DBD_002
				rpData.setBankCode(bankDto.get().getBankCode());
				// B_DBD_003
				rpData.setBankName(bankDto.get().getBankName());
				// B_DBD_004
				rpData.setBranchCode(branchDto.get().getBranchCode());
				// B_DBD_005
				rpData.setBranchName(branchDto.get().getBranchName());

				// B_DBD_006
				if (bankTrans.getFromAccountAtr() == 0) {
					rpData.setFromAccountAtr("普通");
				} else {
					rpData.setFromAccountAtr("当座");
				}
				// B_DBD_007
				rpData.setFromAccountNo(bankTrans.getFromAccountNo());
				// B_DBD_008
				List<BankTransferRpDto> bankTransferObj = bankTransfer.stream()
						.filter(tmp -> tmp.getFromBranchId().equals(bankTrans.getToBranchId())
								&& tmp.getFromAccountAtr() == bankTrans.getFromAccountAtr()
								&& tmp.getFromAccountNo().equals(bankTrans.getFromAccountNo()))
						.collect(Collectors.toList());
				rpData.setNumPerSameType(bankTransferObj.size());
				// A_DBD_009
				BigDecimal pMny = BigDecimal.ZERO;
				for(BankTransferRpDto obj : bankTransferObj){
					pMny = pMny.add(obj.getPaymentMoney());
				}
				rpData.setPaymentMyn(pMny);
				
				rpData.setUnit("円");
				rpData.setUnitPerson("人");

				if (rpDataList.stream()
						.filter(x -> x.getBankCode().equals(rpData.getBankCode())
								&& x.getBranchCode().equals(rpData.getBranchCode())
								&& x.getFromAccountNo().equals(rpData.getFromAccountNo())
								&& x.getFromAccountAtr().equals(rpData.getFromAccountAtr()))
						.count() < 1) {
					rpDataList.add(rpData);
				}
			}
			
			index ++;
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

		header.setCompanyName(bankTransferReportRepo.findCompany(companyCode).getCompanyName());
		header.setDate(query.getTransferDate() + "】");
		// B_DBD_001
		header.setPerson(bankTransferReportRepo.findAllCalled(companyCode).get());

		BankTransferBReport reportB = new BankTransferBReport();
		reportB.setData(rpDataList);
		reportB.setHeader(header);

		this.generator.generator(context.getGeneratorContext(), reportB);
	}
}