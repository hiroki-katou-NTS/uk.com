package nts.uk.screen.at.app.query.kdp.common;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTimeDifference;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTimeDifferenceRepository;
import nts.uk.ctx.sys.portal.dom.notice.adapter.MessageNoticeAdapter;
import nts.uk.ctx.sys.portal.dom.notice.adapter.WorkplaceInfoImport;
import nts.uk.shr.com.context.AppContexts;

/**
 * SQ: 打刻入力で職場の勤務場所と地域時差を取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP_打刻共通.打刻入力地域.打刻入力で職場の勤務場所と地域時差を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class GetWorkPlaceRegionalTime {

	@Inject
	private MessageNoticeAdapter msgNoticeAdapter;

	@Inject
	private WorkLocationRepository workLocationRepository;
	
	@Inject
	private RegionalTimeDifferenceRepository regionalTimeDifferenceRepository;

	public GetWorkPlaceRegionalTimeDto getWorkPlaceRegionalTime(GetWorkPlaceRegionalTimeInput param) {

		GetWorkPlaceRegionalTimeDto result = new GetWorkPlaceRegionalTimeDto();

		// 1. call [RQ30]社員所属職場履歴を取得
		Optional<WorkplaceInfoImport> optWkpInfp = this.msgNoticeAdapter.getWorkplaceInfo(param.getSid(),
				GeneralDate.today());

		// 2.
		List<WorkLocation> workLocations = this.workLocationRepository.findAll(param.getContractCode(),
				AppContexts.user().companyId());

		// 3.
		Optional<WorkLocation> workLocation = Optional.empty();
		
		String wkpId = optWkpInfp.map(x -> x.getWorkplaceId()).orElse(param.getWorkPlaceId());

		workLocation = workLocations.stream().filter(f -> f.getWorkplace().map(m -> m.getWorkpalceId()).orElse("").equals(wkpId)).findFirst();
		
		
		WorkLocationRequireImpl require = new WorkLocationRequireImpl(regionalTimeDifferenceRepository);
		
		if (workLocation.isPresent()) {
			result.setWorkPlaceId(workLocation.get().getWorkplace().map(m -> m.getWorkpalceId()).orElse(""));
			result.setRegional(workLocation.get().findTimeDifference(require));
			result.setWorkLocationName(workLocation.get().getWorkLocationName().v());
			result.setWorkLocationCD(workLocation.get().getWorkLocationCD().v());
		}

		return result;
	}
	
	@AllArgsConstructor
	private class WorkLocationRequireImpl implements WorkLocation.Require {

		@Inject
		private RegionalTimeDifferenceRepository regionalTimeDifferenceRepository;

		@Override
		public Optional<RegionalTimeDifference> get(int code) {
			return regionalTimeDifferenceRepository.get(code);
		}

	}

}
