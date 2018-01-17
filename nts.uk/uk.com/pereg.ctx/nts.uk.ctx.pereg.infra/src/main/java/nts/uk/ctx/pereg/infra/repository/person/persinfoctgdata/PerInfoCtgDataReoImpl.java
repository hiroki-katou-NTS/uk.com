/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.person.persinfoctgdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.infra.entity.person.personinfoctgdata.PpemtPerInfoCtgData;

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
		List<PpemtPerInfoCtgData> datas = this.queryProxy().query(GET_BY_CTGID_PID, PpemtPerInfoCtgData.class)
				.setParameter("pInfoCtgId", ctgId).setParameter("pId", perId).getList();
		if(datas == null) return new ArrayList<>();
		return datas.stream().map(entity -> new PerInfoCtgData(entity.recordId, entity.pInfoCtgId, entity.pId))
				.collect(Collectors.toList());

	}
	private PpemtPerInfoCtgData toEntity(PerInfoCtgData domain){
		return new PpemtPerInfoCtgData(domain.getRecordId(), domain.getPersonInfoCtgId(), domain.getPersonId());
	}
	
	private void updateEntity(PerInfoCtgData domain, PpemtPerInfoCtgData entity){
		entity.recordId = domain.getRecordId();
		entity.pInfoCtgId = domain.getPersonInfoCtgId();
		entity.pId = domain.getPersonId();
	}
	/**
	 * Add person info category data ドメインモデル「個人情報カテゴリデータ」を新規登録する
	 * @param data
	 */
	@Override
	public void addCategoryData(PerInfoCtgData data) {
		Optional<PpemtPerInfoCtgData> existItem = this.queryProxy().find(data.getRecordId(), PpemtPerInfoCtgData.class);
		if (!existItem.isPresent()){
			this.commandProxy().insert(toEntity(data));
		}
	}
	/**
	 * Update person info category data ドメインモデル「個人情報カテゴリデータ」を更新する
	 * @param data
	 */
	@Override
	public void updateCategoryData(PerInfoCtgData data) {
		Optional<PpemtPerInfoCtgData> existItem = this.queryProxy().find(data.getRecordId(), PpemtPerInfoCtgData.class);
		if (!existItem.isPresent()){
			throw new RuntimeException("invalid PerInfoCtgData");
		}
		// Update entity
		updateEntity(data, existItem.get());
		// Update 
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void deleteCategoryData(PerInfoCtgData data) {
		this.commandProxy().remove(PpemtPerInfoCtgData.class,data.getRecordId());
		
	}

}
