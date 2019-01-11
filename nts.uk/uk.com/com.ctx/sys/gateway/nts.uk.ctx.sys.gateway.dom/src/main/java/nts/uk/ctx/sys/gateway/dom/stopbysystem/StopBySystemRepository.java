package nts.uk.ctx.sys.gateway.dom.stopbysystem;

import java.util.Optional;

public interface StopBySystemRepository {
	
	public void insert(StopBySystem domain);

	public void update(StopBySystem domain);

	public Optional<StopBySystem> findByKey(String contractCd);
}
