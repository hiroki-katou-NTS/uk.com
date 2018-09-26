package nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.repo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.ContinuousHolCheckSet;

public interface ContinuousHolCheckSetRepo {
	
	public List<ContinuousHolCheckSet> find(List<String> companyIds);

	public Optional<ContinuousHolCheckSet> find(String companyId);
	
	public Optional<ContinuousHolCheckSet> findSpecial(String companyId);

	public void insert(ContinuousHolCheckSet setting);

	public void update(ContinuousHolCheckSet setting);

	public void remove(String cid);
}
