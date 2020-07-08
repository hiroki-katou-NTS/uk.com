package nts.uk.ctx.at.shared.dom.calculationsetting.query;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.calculationsetting.AutoStampForFutureDayClass;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.shared.dom.calculationsetting.repository.StampReflectionManagementRepository;

/**
 * @author ThanhNX
 *
 *         未来日の打刻を自動セットするか判断する
 */
@Stateless
public class DetermineAutoSetFutureDayStamp {

	@Inject
	private StampReflectionManagementRepository stampReflectManagementRepo;

	// 未来日の打刻を自動セットするか判断する
	public boolean determine(String companyId, GeneralDate date) {

		// ドメインモデル「打刻反映管理」を取得する
		Optional<StampReflectionManagement> stampManagerOpt = stampReflectManagementRepo.findByCid(companyId);

		if (!stampManagerOpt.isPresent())
			return false;
		if (stampManagerOpt.get().getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.SET_AUTO_STAMP) {
			return true;
		}

		return date.beforeOrEquals(GeneralDate.today());

	}
}
