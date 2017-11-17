package nts.uk.ctx.at.record.acc.jobtitle.affiliate;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleAdapter;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleSidDto;

@Stateless
public class AffJobTitleAdapterImpl implements AffJobTitleAdapter{

	@Override
	public List<AffJobTitleSidDto> findByListEmployeeId() {
		// TODO Auto-generated method stub
		return null;
	}

}
