package nts.uk.screen.at.app.query.kdp.kdp001.a;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 打刻入力の場所を取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP001_打刻入力(ポータル).A:打刻入力(ポータル).メニュー別OCD.打刻入力の場所を取得する.打刻入力の場所を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class LocationStampInput {

	@Inject
	private WorkLocationRepository locationRepository;

	public LocationStampInputDto get(LocationStampInputParam param) {

		String cid = AppContexts.user().companyId();

		LocationStampInputDto dto = new LocationStampInputDto();

		Optional<WorkLocation> optWorkLocation = locationRepository.findByCode(param.contractCode,
				param.workLocationCode);

		if (!optWorkLocation.isPresent()) {
			return null;
		}

		WorkLocation workLocation = optWorkLocation.get();

		dto.setWorkLocationName(workLocation.getWorkLocationName().toString());

		List<String> workplaces = workLocation.getListWorkplace().stream().filter(x -> x.getCompanyId().equals(cid))
				.map(m -> m.getWorkpalceId())
				.collect(Collectors.toList());
		dto.setWorkpalceId(workplaces);

		return dto;
	}
}
