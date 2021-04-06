package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement;

import java.util.Optional;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUse;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUsePK;

/**
 * リポジトリ実装：子の看護休暇使用数データ
 * @author yuri_tamakoshi
 */
public class JpaChildCareNurseUsedNumberRepository extends JpaRepository implements ChildCareNurseUsedNumberRepository{

	/** 検索 */
	@Override
	public Optional<ChildCareNurseUsedNumber> find(String employeeId) {
		return this.queryProxy()
				.find(new KrcdtHdnursingUsePK(employeeId, NursingCategory.ChildNursing.value) , KrcdtHdnursingUse.class)
				.map(c -> c.toDomain());

	}

	/** 登録および更新 */
	@Override
	public void persistAndUpdate(String employeeId, ChildCareNurseUsedNumber domain) {

		// キー
		val key = new KrcdtHdnursingUsePK(employeeId,NursingCategory.ChildNursing.value);

		// 登録・更新
		KrcdtHdnursingUse entity = this.getEntityManager().find(KrcdtHdnursingUse.class, key);
		if (entity == null){
			entity = new KrcdtHdnursingUse();
			entity.fromDomainForPersist(employeeId, domain);
			this.getEntityManager().persist(entity);
		}
		else {
			entity.fromDomainForUpdate(employeeId, domain);
		}
	}


	/** 削除 */
	@Override
	public void remove(String employeeId) {

		this.commandProxy().remove(KrcdtHdnursingUse.class, new KrcdtHdnursingUsePK(employeeId,
				NursingCategory.ChildNursing.value));
	}
}
