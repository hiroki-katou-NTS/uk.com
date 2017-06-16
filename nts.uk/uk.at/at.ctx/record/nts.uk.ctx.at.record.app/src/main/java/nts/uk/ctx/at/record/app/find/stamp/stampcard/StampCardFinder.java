package nts.uk.ctx.at.record.app.find.stamp.stampcard;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.StampCardRepository;

@Stateless
public class StampCardFinder {
	@Inject
	private StampCardRepository stampCardRepo;
	
	/**
	 * 
	 * get List Card by PersonID
	 * 
	 * */
	public List<StampCardDto> findByPersonID(String personId) {
		List<StampCardDto> lstCardNumber = stampCardRepo.findByPersonID(personId)
				.stream()
				.map(item -> StampCardDto.fromDomain(item))
				.collect(Collectors.toList());;
		return lstCardNumber;
	}
	
	/**
	 * 
	 * get List Card by List PersonID
	 * 
	 * */
	public List<StampCardDto> findByListPersonID(List<String> lstPersonId) {
		List<StampCardDto> lstCardNumber = stampCardRepo.findByListPersonID(lstPersonId)
				.stream()
				.map(item -> StampCardDto.fromDomain(item))
				.collect(Collectors.toList());;
		return lstCardNumber;
	}
}
