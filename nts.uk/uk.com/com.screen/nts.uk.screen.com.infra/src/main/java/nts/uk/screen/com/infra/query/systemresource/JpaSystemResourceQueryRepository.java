package nts.uk.screen.com.infra.query.systemresource;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.screen.com.app.repository.systemresource.SystemResourceQueryRepository;
import nts.uk.screen.com.app.systemresource.dto.SystemResourceDto;

public class JpaSystemResourceQueryRepository extends JpaRepository implements SystemResourceQueryRepository {

	@Override
	public List<SystemResourceDto> findByListResourceId(String listResourceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(List<SystemResourceDto> listData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(List<SystemResourceDto> listData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<SystemResourceDto> findByResourceId(String resourceId) {
		// TODO Auto-generated method stub
		return null;
	}

}
