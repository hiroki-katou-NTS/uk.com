package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe;

import java.util.Optional;

public interface OuenAggregateFrameSetOfMonthlyRepo {

	public Optional<OuenAggregateFrameSetOfMonthly> find(String companyId);
	
	public void update(OuenAggregateFrameSetOfMonthly domain);
	
	public void insert(OuenAggregateFrameSetOfMonthly domain);
}

