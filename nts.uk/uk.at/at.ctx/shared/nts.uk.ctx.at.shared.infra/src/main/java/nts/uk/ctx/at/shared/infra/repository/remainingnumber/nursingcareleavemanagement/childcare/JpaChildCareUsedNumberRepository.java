package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.childcare;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUse;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUsePK;
import nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.JpaChildCareNurseUsedNumberRepository;

/**
 * リポジトリ実装：介護休暇使用数データ
 */
@Stateless
public class JpaChildCareUsedNumberRepository extends JpaChildCareNurseUsedNumberRepository implements ChildCareUsedNumberRepository{

	/** 検索 */
	@Override
	public Optional<ChildCareUsedNumberData> find(String employeeId) {

		// 共通処理呼び出し
		Optional<ChildCareNurseUsedNumber> childCareNurseUsedNumberOpt = this.find(employeeId, NursingCategory.ChildNursing);

		// 型変換
		Optional<ChildCareUsedNumberData> childCareUsedNumberData
				= childCareNurseUsedNumberOpt.map(mapper->new ChildCareUsedNumberData(employeeId, mapper ));

		return childCareUsedNumberData;
	}

	/** 登録および更新 */
	@Override
	public void persistAndUpdate(String employeeId, ChildCareUsedNumberData domain) {
		this.persistAndUpdate(employeeId, NursingCategory.ChildNursing, domain);
	}

	/** 削除 */
	@Override
	public void remove(String employeeId) {
		this.commandProxy().remove(KrcdtHdnursingUse.class, new KrcdtHdnursingUsePK(employeeId,
				NursingCategory.ChildNursing.value));
	}

}
