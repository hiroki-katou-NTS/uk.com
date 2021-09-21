package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.childcare;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUse;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUsePK;
import nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.JpaChildCareNurseUsedNumberRepository;

/**
 * リポジトリ実装：子の看護休暇使用数データ
 */
@Stateless
public class JpaChildCareUsedNumberRepository extends JpaChildCareNurseUsedNumberRepository implements ChildCareUsedNumberRepository{

	// 子の看護
	private static NursingCategory NURSING_CATEGORY = NursingCategory.ChildNursing;

	/** 検索 */
	@Override
	public Optional<ChildCareUsedNumberData> find(String employeeId) {

		// 共通処理呼び出し
		Optional<ChildCareNurseUsedNumber> childCareNurseUsedNumberOpt = this.find(employeeId, NURSING_CATEGORY);

		// 型変換
		Optional<ChildCareUsedNumberData> careUsedNumberData
				= childCareNurseUsedNumberOpt.map(mapper->new ChildCareUsedNumberData(employeeId, mapper));

		return careUsedNumberData;
	}

	/** 検索 */
	@Override
	public List<ChildCareUsedNumberData> find(List<String> employeeIds) {

		// 共通処理呼び出し
		List<ChildCareNurseUsedNumber> childCareNurseUsedNumberList
			= this.find(employeeIds, NURSING_CATEGORY);

		// 型変換
		List<ChildCareUsedNumberData> careUsedNumberDataList
			= childCareNurseUsedNumberList.stream().map(mapper->((ChildCareUsedNumberData)mapper)).collect(Collectors.toList());

		return careUsedNumberDataList;
	}

	/** 登録および更新 */
	@Override
	public void persistAndUpdate(String employeeId, ChildCareUsedNumberData domain) {
		this.persistAndUpdate(employeeId, NURSING_CATEGORY, domain);
	}

	/** 削除 */
	@Override
	public void remove(String employeeId) {
		this.commandProxy().remove(
				KrcdtHdnursingUse.class,
				new KrcdtHdnursingUsePK(employeeId, NURSING_CATEGORY.value));
	}

	/** 追加 */
	@Override
	public void add(String cId, ChildCareUsedNumberData domain) {
		KrcdtHdnursingUsePK key
			= new KrcdtHdnursingUsePK(
				domain.getEmployeeId(), NURSING_CATEGORY.value);
		KrcdtHdnursingUse krcdtHdnursingUse = new KrcdtHdnursingUse();
		krcdtHdnursingUse.fromDomain(cId, domain);
		krcdtHdnursingUse.pk = key;

		// 登録
		this.commandProxy().insert(krcdtHdnursingUse);
	}

	/** 追加 */
	@Override
	public void addAll(String cid, List<ChildCareUsedNumberData> domainList) {
		// 登録
		domainList.forEach(action->this.add(cid, action));
	}

	/** 更新 */
	@Override
	public void update(String cid, ChildCareUsedNumberData domain) {
		// キー
		val key = new KrcdtHdnursingUsePK(domain.getEmployeeId(), NURSING_CATEGORY.value);

		KrcdtHdnursingUse entity = this.getEntityManager().find(KrcdtHdnursingUse.class, key);
		if (entity == null) {
			KrcdtHdnursingUse krcdtHdnursingUse = new KrcdtHdnursingUse();
			krcdtHdnursingUse.fromDomain(cid, domain);
			krcdtHdnursingUse.pk = key;
			this.getEntityManager().persist(krcdtHdnursingUse);
		} else {
			entity.fromDomain(cid, domain);
			this.commandProxy().update(entity);
		}
	}

	/** 更新 */
	@Override
	public void updateAll(String cid, List<ChildCareUsedNumberData> domainList) {
		// 登録
		domainList.forEach(action->this.update(cid, action));
	}

}

