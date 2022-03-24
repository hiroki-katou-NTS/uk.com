package nts.uk.ctx.link.smile.app.smilelink;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversion;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversionRepository;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SmileOutLinkSetFinder {
	
	@Inject
	private SmileLinkageOutputSettingRepository linkOutRep;
	
	@Inject
	private LinkedPaymentConversionRepository payConverRep;
	
	public SmileOutLinkSetDto getOutLinkSetForSmile() {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		
		Optional<SmileLinkageOutputSetting> linkOut = linkOutRep.get(contractCode, companyId);
		List<LinkedPaymentConversion> listPayConver = payConverRep.getLinkPayConver(contractCode, companyId);
		
		if(linkOut.isPresent()) {
			SmileOutLinkSetDto outSet = new SmileOutLinkSetDto(linkOut.get().getSalaryCooperationClassification().value, 
					linkOut.get().getMonthlyLockClassification().value, 
					linkOut.get().getMonthlyApprovalCategory().value, 
					linkOut.get().getSalaryCooperationConditions().isPresent() ? Optional.of(linkOut.get().getSalaryCooperationConditions().get().v()) : Optional.empty(), 
					listPayConver.stream().map(x -> new LinkedPaymentDto(x.getPaymentCode().value, 
													x.getSelectiveEmploymentCodes().stream().map(y -> new EmploymentLinkedMonthDto(y.getInterlockingMonthAdjustment().value, 
															y.getScd())).collect(Collectors.toList())
												)).collect(Collectors.toList()));
			return outSet;
		}
		return new SmileOutLinkSetDto(null, null, null, Optional.empty(), listPayConver.stream().map(x -> new LinkedPaymentDto(x.getPaymentCode().value, 
													x.getSelectiveEmploymentCodes().stream().map(y -> new EmploymentLinkedMonthDto(y.getInterlockingMonthAdjustment().value, 
															y.getScd())).collect(Collectors.toList())
												)).collect(Collectors.toList()));
		
	}
	
}
