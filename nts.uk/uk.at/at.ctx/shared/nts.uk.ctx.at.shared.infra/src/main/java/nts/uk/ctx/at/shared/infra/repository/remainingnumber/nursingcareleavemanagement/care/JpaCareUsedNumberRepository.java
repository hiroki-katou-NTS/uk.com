package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.care;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUse;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUsePK;
import nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.JpaChildCareNurseUsedNumberRepository;

/**
 * リポジトリ実装：子の看護休暇使用数データ
 * @author yuri_tamakoshi
 */
public class JpaCareUsedNumberRepository extends JpaChildCareNurseUsedNumberRepository implements ChildCareNurseUsedNumberRepository{

	/** 検索 */
	@Override
	public Optional<ChildCareNurseUsedNumber> find(String employeeId) {
		return this.find(employeeId, NursingCategory.Nursing);
	}

	/** 登録および更新 */
	@Override
	public void persistAndUpdate(String employeeId, ChildCareNurseUsedNumber domain) {
		this.persistAndUpdate(employeeId, NursingCategory.Nursing, domain);
	}

	/** 削除 */
	@Override
	public void remove(String employeeId) {
		this.commandProxy().remove(KrcdtHdnursingUse.class, new KrcdtHdnursingUsePK(employeeId,
				NursingCategory.Nursing.value));
	}

}
