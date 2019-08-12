package nts.uk.ctx.hr.develop.dom.guidance;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class IGuidanceImpl implements IGuidance{

	@Inject
	private GuidanceRepository guidanceRepo;

	@Override
	public IGuidanceExportDto getGuidance(String companyId, String programId, String screenId) {
		Optional<Guidance> guidance = guidanceRepo.getGuidance(companyId);
		if(guidance.isPresent()) {
			if(guidance.get().isUsageFlgCommon()) {
				Optional<GuideMsg> guideMsgs = guidance.get().getGuideMsg().stream().filter(c -> {return c.getProgramId().equals(programId) && c.getScreenId().equals(screenId);}).findFirst();
				if(guideMsgs.isPresent() && guideMsgs.get().isUsageFlgByScreen()) {
					return new IGuidanceExportDto(true, guidance.get().getGuideMsgAreaRow().v(), guideMsgs.get().getGuideMsg());
				}
			}
		}
		return new IGuidanceExportDto(false, null, null);
	}

}
