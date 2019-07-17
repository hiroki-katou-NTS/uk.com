package nts.uk.ctx.at.record.app.find.stamp.card.stampcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
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

	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		List<String> sids = query.getEmpInfos().stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());
		List<StampCard> domains = stampCardRepo.getLstStampCardByLstSid(sids);
		List<GridPeregDomainDto> result = new ArrayList<>();
		List<GridPeregDomainDto> resultDataExist = domains.stream().map(d -> {
			String pid = query.getEmpInfos().stream()
					.filter(f -> f.getEmployeeId().equals(d.getEmployeeId()))
					.findFirst()
					.map(m -> m.getPersonId())
					.orElse("");
			
			return new  GridPeregDomainDto(d.getEmployeeId(), pid, PeregStampCardDto.createFromDomain(d));
		}).collect(Collectors.toList());
		
		if(!CollectionUtil.isEmpty(resultDataExist)) {
			result.addAll(resultDataExist);
		}
		
		List<GridPeregDomainDto> resultDistinct = resultDataExist.stream().filter(distinctByKey(GridPeregDomainDto::getEmployeeId)).collect(Collectors.toList());
		
		query.getEmpInfos().stream().forEach(c -> {
			Optional<GridPeregDomainDto> gridDto = resultDistinct.stream().filter(r -> r.getEmployeeId().equals(c.getEmployeeId())).findFirst();
			
			if(!gridDto.isPresent()) {
				GridPeregDomainDto dto = new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null);
				
				result.add(dto);
			}
		});
		
		return result;
	}
	
	@Override
	public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp query) {
		List<GridPeregDomainBySidDto> result = new ArrayList<>();
		List<GridPeregDomainBySidDto> resultDataExist = new ArrayList<>();
		List<String> sids = query.getEmpInfos().stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());
		Map<String, List<StampCard>> domains = stampCardRepo.getLstStampCardByLstSid(sids).stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		
		domains.entrySet().forEach(d ->{
			String pid = query.getEmpInfos().stream()
					.filter(f -> f.getEmployeeId().equals(d.getKey()))
					.findFirst()
					.map(m -> m.getPersonId())
					.orElse("");
			List<PeregDomainDto>  peregDto = new ArrayList<>();
			
			d.getValue().forEach(s ->{
				peregDto.add(PeregStampCardDto.createFromDomain(s));
			});
			
			resultDataExist.add(new GridPeregDomainBySidDto(d.getKey(), pid, peregDto));
		});
		
		if(!CollectionUtil.isEmpty(resultDataExist)) {
			result.addAll(resultDataExist);
		}
		
		List<GridPeregDomainBySidDto> resultDistinct = resultDataExist.stream()
				.filter(distinctByKey(GridPeregDomainBySidDto::getEmployeeId)).collect(Collectors.toList());
		
		query.getEmpInfos().stream().forEach(c -> {
			Optional<GridPeregDomainBySidDto> gridDto = resultDistinct.stream().filter(r -> r.getEmployeeId().equals(c.getEmployeeId())).findFirst();
			
			if(!gridDto.isPresent()) {
				GridPeregDomainBySidDto dto = new GridPeregDomainBySidDto(c.getEmployeeId(), c.getPersonId(), new ArrayList<>());
				result.add(dto);
			}
		});
		
		return result;
	}
	
	/**
	 * 
	 * @param keyExtractor
	 * @return
	 */
	private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
