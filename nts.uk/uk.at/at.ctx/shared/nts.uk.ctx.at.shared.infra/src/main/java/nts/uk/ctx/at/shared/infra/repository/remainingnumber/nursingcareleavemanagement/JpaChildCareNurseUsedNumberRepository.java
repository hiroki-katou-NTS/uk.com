package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUse;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUsePK;
import nts.uk.shr.com.context.AppContexts;

/**
 * リポジトリ実装：子の看護介護(共通)休暇使用数データ
 */
@Stateless
public class JpaChildCareNurseUsedNumberRepository extends JpaRepository {

	private static final String SEL_USED_NUMBER;
	static {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT k ");
		stringBuilder.append("FROM KrcdtHdnursingUse k ");
		stringBuilder.append("WHERE k.pk.employeeId IN :emplyeeIds");
		stringBuilder.append(" AND k.pk.nursingType = :nursingType");
		SEL_USED_NUMBER = stringBuilder.toString();
	}

	/** 検索 */
	protected Optional<ChildCareNurseUsedNumber> find(
			String employeeId, NursingCategory nursingCategory) {
		return this.queryProxy()
				.find(new KrcdtHdnursingUsePK(employeeId, nursingCategory.value) , KrcdtHdnursingUse.class)
				.map(entity -> entity.toDomain());
	}

	/** 検索 */
	public List<ChildCareNurseUsedNumber> find(List<String> emplyeeIdsIn, NursingCategory nursingTypeIn) {
		List<ChildCareNurseUsedNumber> resultList = new ArrayList<>();
		CollectionUtil.split(emplyeeIdsIn, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SEL_USED_NUMBER, KrcdtHdnursingUse.class)
				.setParameter("emplyeeIds", subList)
				.setParameter("nursingType", nursingTypeIn.value)
				.getList(entity -> entity.toDomain()));
		});
		return resultList;
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
		entity.fromDomain(AppContexts.user().companyId(), domain);
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
