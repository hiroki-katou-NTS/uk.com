package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUse;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUsePK;

/**
 * リポジトリ実装：子の看護介護(共通)休暇使用数データ
 */
@Stateless
public class JpaChildCareNurseUsedNumberRepository extends JpaRepository {

	/** 検索 */
	protected Optional<ChildCareNurseUsedNumber> find(
			String employeeId, NursingCategory nursingCategory) {
		return this.queryProxy()
				.find(new KrcdtHdnursingUsePK(employeeId, nursingCategory.value) , KrcdtHdnursingUse.class)
				.map(c -> c.toDomain());
	}

	/** 登録および更新 */
	protected void persistAndUpdate(
			String employeeId, NursingCategory nursingCategory, ChildCareNurseUsedNumber domain) {

		// キー
		val key = new KrcdtHdnursingUsePK(employeeId, nursingCategory.value);

		// 登録・更新
		KrcdtHdnursingUse entity = this.getEntityManager().find(KrcdtHdnursingUse.class, key);
		if (entity == null){
			entity = new KrcdtHdnursingUse();
		}
		entity.fromDomainForPersist(employeeId, domain);
		entity.pk = key;
		this.getEntityManager().persist(entity);

	}

	/** 削除 */
	protected void remove(String employeeId, NursingCategory nursingCategory) {

		// キー
		val key = new KrcdtHdnursingUsePK(employeeId, nursingCategory.value);

		// 削除
		this.commandProxy().remove(KrcdtHdnursingUse.class, key);

	}
}
