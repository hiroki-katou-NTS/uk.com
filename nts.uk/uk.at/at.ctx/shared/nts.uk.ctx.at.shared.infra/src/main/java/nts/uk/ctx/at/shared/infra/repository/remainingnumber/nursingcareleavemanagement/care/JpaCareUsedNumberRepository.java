package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.care;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUse;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUsePK;
import nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.JpaChildCareNurseUsedNumberRepository;

/**
 * リポジトリ実装：介護休暇使用数データ
 */
@Stateless
public class JpaCareUsedNumberRepository extends JpaChildCareNurseUsedNumberRepository implements CareUsedNumberRepository{

	// 介護
	private static NursingCategory NURSING_CATEGORY = NursingCategory.Nursing;

	/** 検索 */
	@Override
	public Optional<CareUsedNumberData> find(String employeeId) {

		// 共通処理呼び出し
		Optional<ChildCareNurseUsedNumber> childCareNurseUsedNumberOpt = this.find(employeeId, NURSING_CATEGORY);

		// 型変換
		Optional<CareUsedNumberData> careUsedNumberData
				= childCareNurseUsedNumberOpt.map(mapper->new CareUsedNumberData(employeeId, mapper));

		return careUsedNumberData;
	}

	/** 検索 */
	@Override
	public List<CareUsedNumberData> find(List<String> employeeIds) {

		// 共通処理呼び出し
		List<ChildCareNurseUsedNumber> childCareNurseUsedNumberList
			= this.find(employeeIds, NURSING_CATEGORY);

		// 型変換
		List<CareUsedNumberData> careUsedNumberDataList
			= childCareNurseUsedNumberList.stream().map(mapper->((CareUsedNumberData)mapper)).collect(Collectors.toList());

		return careUsedNumberDataList;
	}

	/** 登録および更新 */
	@Override
	public void persistAndUpdate(String employeeId, CareUsedNumberData domain) {
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
	public void add(String cId, CareUsedNumberData domain) {
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
	public void addAll(String cid, List<CareUsedNumberData> domainList) {
		// 登録
		domainList.forEach(action->this.add(cid, action));
	}

	/** 更新 */
	@Override
	public void update(String cid, CareUsedNumberData domain) {
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
	public void updateAll(String cid, List<CareUsedNumberData> domainList) {
		// 登録
		domainList.forEach(action->this.update(cid, action));
	}

}
