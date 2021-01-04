package nts.uk.ctx.at.record.pubimp.application.divergence;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodRepository;
import nts.uk.ctx.at.record.pub.application.divergence.DivergenceReasonInputMethodExport;
import nts.uk.ctx.at.record.pub.application.divergence.DivergenceReasonInputMethodPub;

@Stateless
public class DivergenceReasonInputMethodPubImpl implements DivergenceReasonInputMethodPub {
	
	@Inject
	private DivergenceReasonInputMethodRepository divergenceReasonInputMethodExport;
	
	@Override
	public List<DivergenceReasonInputMethodExport> getData(String companyId, List<Integer> lstNo) {
		if (CollectionUtil.isEmpty(lstNo)) return Collections.emptyList();
		List<DivergenceReasonInputMethod> divergenceReasonInputMethods = divergenceReasonInputMethodExport.getByCidAndLstTimeInfo(companyId, lstNo);
		
		// parse export 
		return divergenceReasonInputMethods.stream()
									.map(x -> new DivergenceReasonInputMethodExport(
											x.getDivergenceTimeNo(),
											x.getCompanyId(),
											x.isDivergenceReasonInputed(),
											x.isDivergenceReasonSelected(),
											x.getReasons()))
									.collect(Collectors.toList());
		
	}
}
