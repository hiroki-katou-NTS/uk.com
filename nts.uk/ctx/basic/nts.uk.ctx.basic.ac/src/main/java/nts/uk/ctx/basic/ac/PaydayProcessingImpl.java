package nts.uk.ctx.basic.ac;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.employment.PaydayProcessing;
import nts.uk.ctx.basic.dom.organization.employment.PaydayProcessingAdapter;



@Stateless
public class PaydayProcessingImpl implements PaydayProcessingAdapter{
	
	//@Inject
	//private IPaydayProcessingPub paydayProcessingPub;
	
	@Override
	public List<PaydayProcessing> getPaydayProcessing(String companyCd) {
	
		return null;
	}

}
