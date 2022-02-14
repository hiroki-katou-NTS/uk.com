package nts.uk.screen.at.app.query.kdp.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTimeDifference;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTimeDifferenceRepository;

/**
 * SC: 打刻入力で勤務場所と地域時差を取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP_打刻共通.打刻入力地域.打刻入力で勤務場所と地域時差を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class GetWorkLocationAndRegionalTimeDifference {

	@Inject
	private WorkLocationRepository workLocationRepository;

	@Inject
	private RegionalTimeDifferenceRepository regionalTimeDifferenceRepository;

	public GetWorkLocationAndRegionalTimeDifferenceDto getWorkLocationAndRegionalTimeDifference(GetWorkLocationAndRegionalTimeDifferenceInput param) {
		
		GetWorkLocationAndRegionalTimeDifferenceDto result = new GetWorkLocationAndRegionalTimeDifferenceDto();

		Optional<WorkLocation> workLocation = Optional.empty();

		// Step 1
		if (param.getWorkLocationCode() != null) {
			workLocation = this.workLocationRepository.findByCode(param.getContractCode(), param.getWorkLocationCode());
		}

		// Step2
		if (param.getContractCode() == null && param.getIpv4Address() != null) {
			workLocation = this.workLocationRepository.identifyWorkLocationByAddress(param.getContractCode(),
					param.getIpv4Address());
		}

		// Step3
		if (workLocation.isPresent()) {
			WorkLocationRequireImpl require = new WorkLocationRequireImpl(regionalTimeDifferenceRepository);
			
			result.setRegionalTime(workLocation.get().findTimeDifference(require, param.getContractCode()));
			// Step4
			result.setWorkPlaceId(workLocation.get().getWorkplace().map(m -> m.getWorkpalceId()).orElse(""));
		}
		
		return result;
	}

	@AllArgsConstructor
	private class WorkLocationRequireImpl implements WorkLocation.Require {

		@Inject
		private RegionalTimeDifferenceRepository regionalTimeDifferenceRepository;

		@Override
		public Optional<RegionalTimeDifference> get(String contractCode, int code) {
			return regionalTimeDifferenceRepository.get(contractCode, code);
		}

	}

}
