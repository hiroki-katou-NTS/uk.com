package nts.uk.ctx.at.record.app.find.stamp.card.stampcard;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class PeregStampCardFinder implements PeregFinder<PeregStampCardDto> {


	@Inject
	private StampCardRepository stampCardRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00069";
	}

	@Override
	public Class<PeregStampCardDto> dtoClass() {
		return PeregStampCardDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		Optional<StampCard> stampCardOpt = stampCardRepo.getByStampCardId(query.getInfoId());
		if (!stampCardOpt.isPresent()) {
			return null;
		}
		return PeregStampCardDto.createFromDomain(stampCardOpt.get());
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		List<StampCard> stampCardList = stampCardRepo.getListStampCard(query.getEmployeeId());
		return stampCardList.stream().map(stampCard -> PeregStampCardDto.createFromDomain(stampCard))
				.collect(Collectors.toList());
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		List<StampCard> stampCardList = stampCardRepo.getListStampCard(query.getEmployeeId());
		return stampCardList.stream()
				.map(stampCard -> new ComboBoxObject(stampCard.getStampCardId(), stampCard.getStampNumber().v()))
				.collect(Collectors.toList());
	}

}
