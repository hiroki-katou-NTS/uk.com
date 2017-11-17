package nts.uk.ctx.at.record.ac.classification.affiliate;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidDto;

@Stateless
public class AffClassificationAdapterImpl implements AffClassificationAdapter {

	@Override
	public List<AffClassificationSidDto> findByListEmployeeId() {
		// TODO Auto-generated method stub
		return null;
	}

}
