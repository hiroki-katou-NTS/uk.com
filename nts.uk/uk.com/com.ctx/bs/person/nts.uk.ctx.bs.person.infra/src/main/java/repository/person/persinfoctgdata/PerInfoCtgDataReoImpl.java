/**
 * 
 */
package repository.person.persinfoctgdata;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.person.personinfoctgdata.PpemtPerInfoCtgData;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;

/**
 * @author danpv
 *
 */
@Stateless
public class PerInfoCtgDataReoImpl extends JpaRepository implements PerInfoCtgDataRepository {

	private static final String GET_BY_CTGID_PID = "select cd from PpemtPerInfoCtgData cd"
			+ " where cd.pInfoCtgId = :pInfoCtgId and cd.pId = :pId";

	@Override
	public Optional<PerInfoCtgData> getByRecordId(String recordId) {
		PpemtPerInfoCtgData entity = this.queryProxy().find(recordId, PpemtPerInfoCtgData.class).get();
		return Optional.of(new PerInfoCtgData(entity.recordId, entity.pInfoCtgId, entity.pId));
	}

	@Override
	public List<PerInfoCtgData> getByPerIdAndCtgId(String perId, String ctgId) {
		List<PpemtPerInfoCtgData> datas = this.queryProxy().namedQuery(GET_BY_CTGID_PID, PpemtPerInfoCtgData.class)
				.setParameter("pInfoCtgId", ctgId).setParameter("pId", perId).getList();
		return datas.stream().map(entity -> new PerInfoCtgData(entity.recordId, entity.pInfoCtgId, entity.pId))
				.collect(Collectors.toList());

	}

}
