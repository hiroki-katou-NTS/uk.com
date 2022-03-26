package nts.uk.ctx.at.record.infra.repository.stampmanagement.timedifferencemanagement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTimeDifference;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timedifferencemanagement.RegionalTimeDifferenceRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.timedifferencemanagement.KrcmtRegionalTimeDifferenceMgt;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.timedifferencemanagement.KrcmtRegionalTimeDifferenceMgtPk;

/**
 * 
 * @author chungnt
 *
 */
@Stateless
public class JpaRegionalTimeDifferenceRepository extends JpaRepository implements RegionalTimeDifferenceRepository {
	
	private static final String GET_ALL = "select s from KrcmtRegionalTimeDifferenceMgt s " ;

	@Override
	public void insert(RegionalTimeDifference regionalTimeDifference) {
		KrcmtRegionalTimeDifferenceMgt entity = new KrcmtRegionalTimeDifferenceMgt();
		this.commandProxy().insert(entity.toEntity(regionalTimeDifference));
	}

	@Override
	public Optional<RegionalTimeDifference> get(int code) {
		KrcmtRegionalTimeDifferenceMgt entity = this.queryProxy().find(new KrcmtRegionalTimeDifferenceMgtPk(code ),KrcmtRegionalTimeDifferenceMgt.class).orElse(null);
		return entity == null ? Optional.empty() : Optional.of(entity.toDomain());
	}

	@Override
	public List<RegionalTimeDifference> getAll() {
		return this.queryProxy().query(GET_ALL, KrcmtRegionalTimeDifferenceMgt.class).getList(x -> x.toDomain());
	}
}
