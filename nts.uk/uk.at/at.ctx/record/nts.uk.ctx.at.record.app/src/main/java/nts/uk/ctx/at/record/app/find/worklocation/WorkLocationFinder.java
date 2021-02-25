package nts.uk.ctx.at.record.app.find.worklocation;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
/**
 * 
 * @author hieult
 *
 */
public class WorkLocationFinder {

	@Inject
	private WorkLocationRepository workPlaceRepository;
	
	/**
	 * Find All WorkLocation
	 * @param companyID
	 * @return List
	 */
	public List<WorkLocationDto> getAllWorkPlace(){
		String companyID = AppContexts.user().companyId();
		return this.workPlaceRepository.findAll(companyID).stream()
				.map(workPlace -> WorkLocationDto.fromDomain(workPlace))
				.collect(Collectors.toList());
	}
	
	public Optional<WorkLocationDto> getWorkPlace(String workLocationCD){
		String companyID = AppContexts.user().companyId();
		return this.workPlaceRepository.findByCode(companyID, workLocationCD)
				.map(workPlace -> WorkLocationDto.fromDomain(workPlace));
	}
	
	/**
	 * Find All WorkLocation KCP012
	 * 
	 * @return Kcp012WorkplaceListDto
	 */
	public List<Kcp012WorkplaceListDto> findWorkplaceList() {
		//	アルゴリズム「勤務場所リスト：リスト表示処理」を実行する
		String companyID = AppContexts.user().companyId();
		//	ドメインモデル「勤務場所」を取得する
		List<WorkLocationDto> wLocationDto = this.workPlaceRepository.findAll(companyID).stream()
				.map(workPlace -> WorkLocationDto.fromDomain(workPlace))
				//	取得した勤務場所情報をリストに表示する
				.sorted(Comparator.comparing(WorkLocationDto::getWorkLocationCD)).collect(Collectors.toList());
		return wLocationDto.stream().map(wp -> {
			Kcp012WorkplaceListDto dto = new Kcp012WorkplaceListDto();
			dto.setCode(wp.getWorkLocationCD());
			dto.setName(wp.getWorkLocationName());
			return dto;
		}).collect(Collectors.toList());
	}
}
