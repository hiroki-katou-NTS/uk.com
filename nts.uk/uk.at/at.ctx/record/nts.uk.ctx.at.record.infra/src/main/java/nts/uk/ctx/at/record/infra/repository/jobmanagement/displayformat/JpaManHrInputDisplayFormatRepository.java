package nts.uk.ctx.at.record.infra.repository.jobmanagement.displayformat;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.ManHrInputDisplayFormat;
import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.ManHrInputDisplayFormatRepository;

/**
 * 
 * @author tutt
 *
 */
public class JpaManHrInputDisplayFormatRepository extends JpaRepository implements ManHrInputDisplayFormatRepository {

	@Override
	public void insert(ManHrInputDisplayFormat format) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ManHrInputDisplayFormat format) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String cId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<ManHrInputDisplayFormat> get(String cId) {
		// TODO Auto-generated method stub
		return null;
	}

}
