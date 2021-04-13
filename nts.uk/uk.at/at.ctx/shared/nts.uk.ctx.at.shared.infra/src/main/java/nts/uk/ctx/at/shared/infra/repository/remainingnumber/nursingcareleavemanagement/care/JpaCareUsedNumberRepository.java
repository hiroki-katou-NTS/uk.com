package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.care;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUse;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcdtHdnursingUsePK;
import nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.JpaChildCareNurseUsedNumberRepository;

/**
 * リポジトリ実装：子の看護休暇使用数データ
 * @author yuri_tamakoshi
 */
@Stateless
public class JpaCareUsedNumberRepository extends JpaChildCareNurseUsedNumberRepository implements CareUsedNumberRepository{

	/** 検索 */
	@Override
	public Optional<CareUsedNumberData> find(String employeeId) {

		// 共通処理呼び出し
		Optional<ChildCareNurseUsedNumber> childCareNurseUsedNumberOpt = this.find(employeeId, NursingCategory.ChildNursing);

		// 型変換
		Optional<CareUsedNumberData> careUsedNumberData
				= childCareNurseUsedNumberOpt.map(mapper->new CareUsedNumberData(employeeId, mapper ));

		return careUsedNumberData;
	}

	/** 登録および更新 */
	@Override
	public void persistAndUpdate(String employeeId, CareUsedNumberData domain) {
		this.persistAndUpdate(employeeId, NursingCategory.Nursing, domain);
	}

	/** 削除 */
	@Override
	public void remove(String employeeId) {
		this.commandProxy().remove(KrcdtHdnursingUse.class, new KrcdtHdnursingUsePK(employeeId,
				NursingCategory.Nursing.value));
	}

}
