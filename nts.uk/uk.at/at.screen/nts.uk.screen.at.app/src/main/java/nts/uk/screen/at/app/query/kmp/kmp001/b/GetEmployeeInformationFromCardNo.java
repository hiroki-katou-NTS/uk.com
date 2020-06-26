package nts.uk.screen.at.app.query.kmp.kmp001.b;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;


import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.B：カードNO指定による個人の登録.メニュー別OCD.カードNOから抽出した社員情報を取得する
 * 
 * @author chungnt
 *
 */
@Stateless
public class GetEmployeeInformationFromCardNo {

	@Inject
	private StampCardRepository stampCardRepo;
	
	@Inject
	private EmployeeInformationAdapter employeeInformationAdapter;

	public GetEmployeeInformationFromCardNoDto find(String cardNumber) {

		List<StampCard> stampCards = new ArrayList<>();
		String contractCd = AppContexts.user().contractCode();
		GeneralDate generalDate = GeneralDate.today();
		
		if (cardNumber.isEmpty()) {
			Optional<StampCard> stampCard = stampCardRepo.getByCardNoAndContractCode(cardNumber, contractCd);
			
			if (stampCard.isPresent()) {
				stampCards.add(stampCard.get());
			}else {
				throw new RuntimeException("Not found");
			}
			
		} else {
			stampCards = stampCardRepo.getLstStampCardByContractCode(contractCd) == null ? new ArrayList<>()
					: stampCardRepo.getLstStampCardByContractCode(contractCd);
		}
		
		List<String> employeeIds = new ArrayList<>();
		
		if (!stampCards.isEmpty()) {
			stampCards.stream().forEach(m -> {
				employeeIds.add(m.getEmployeeId());
			});
		}
		
		List<EmployeeInformationImport> empInfoList = employeeInformationAdapter
				.getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds, generalDate, false, false, false,
						false, false, false));
		
		if (empInfoList.isEmpty()) {
			throw new RuntimeException("Not found");
		}
		
		return new GetEmployeeInformationFromCardNoDto(stampCards, empInfoList);
	}
}
