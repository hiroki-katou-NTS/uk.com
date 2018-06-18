package nts.uk.ctx.at.record.app.find.stamp.stampcard;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.StampCardtemRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StampCardFinder {

	@Inject
	private StampCardtemRepository stampCarItemdRepo;
	
	@Inject
	private StampCardRepository stampCardRepo;
	
	/**
	 * 
	 * get List Card by List PersonID
	 * 
	 * */
	public List<String> findByLstSID(List<String> lstPersonId) {
		List<String> lstCardNumber = stampCarItemdRepo.findByListEmployeeID(lstPersonId)
				.stream()
				.map(item -> item.getCardNumber().v())
				.collect(Collectors.toList());
		return lstCardNumber;
	}
	
	
	/**
	 * 
	 * get List Card by PersonID
	 * 
	 * */
	public List<StampCardDto> findByPersonID(String employeeId) {
		List<StampCardDto> lstCardNumber = stampCarItemdRepo.findByEmployeeID(employeeId)
				.stream()
				.map(item -> StampCardDto.fromDomain(item))
				.collect(Collectors.toList());;
		return lstCardNumber;
	}
	
	
	public List<String> getListCardNoByContactCode() {
		return stampCardRepo.getListStampCardByContractCode(AppContexts.user().contractCode());
	}
	
	

	
}
