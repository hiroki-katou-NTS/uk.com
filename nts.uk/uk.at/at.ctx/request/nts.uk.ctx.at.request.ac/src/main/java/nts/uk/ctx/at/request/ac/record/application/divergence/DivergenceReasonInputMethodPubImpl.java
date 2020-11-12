package nts.uk.ctx.at.request.ac.record.application.divergence;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.pub.application.divergence.DivergenceReasonInputMethodExport;
import nts.uk.ctx.at.record.pub.application.divergence.DivergenceReasonInputMethodPub;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceInputRequired;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReason;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonCode;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethod;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethodI;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonSelect;
/**
 * Refactor5
 * RQ693
 * @author hoangnd
 *
 */
@Stateless
public class DivergenceReasonInputMethodPubImpl implements DivergenceReasonInputMethodI{
	
	@Inject
	private DivergenceReasonInputMethodPub divergenceReasonInputMethodPub;

	@Override
	public List<DivergenceReasonInputMethod> getData(String companyId, List<Integer> lstNo) {
		List<DivergenceReasonInputMethodExport> divergenceReasonInputMethodExports = divergenceReasonInputMethodPub.getData(companyId, lstNo);		
		
		return divergenceReasonInputMethodExports.stream()
												 .map(x -> this.convertObject(x))
												 .collect(Collectors.toList());
	}
	
	public DivergenceReasonInputMethod convertObject(DivergenceReasonInputMethodExport export) {
		
		return new DivergenceReasonInputMethod(
				export.getDivergenceTimeNo(),
				export.getCompanyId(),
				export.getDivergenceReasonInputed(),
				export.getDivergenceReasonSelected(),
				export.getReasons().stream()
								   .map(x -> this.convertObject(x))
								   .collect(Collectors.toList()));
	}
	public DivergenceReasonSelect convertObject(nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect select) {
		
		return new DivergenceReasonSelect(
				new DivergenceReasonCode(select.getDivergenceReasonCode().v()),
				new DivergenceReason(select.getReason().v()),
				EnumAdaptor.valueOf(select.getReasonRequired().value, DivergenceInputRequired.class));
	}
}
