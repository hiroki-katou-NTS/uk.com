package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;

/**
 * 介護休暇基本情報
 *
 * @author xuan vinh
 *
 */
public class CareLeaveRemainingInfo extends NursingCareLeaveRemainingInfo  implements DomainAggregate  {

	public CareLeaveRemainingInfo() {
	}

	public CareLeaveRemainingInfo(String sId, NursingCategory leaveType, boolean useClassification,
			UpperLimitSetting upperlimitSetting, Optional<ChildCareNurseUpperLimit> maxDayForThisFiscalYear,
			Optional<ChildCareNurseUpperLimit> maxDayForNextFiscalYear) {
		super(sId, leaveType, useClassification, upperlimitSetting, maxDayForThisFiscalYear, maxDayForNextFiscalYear);
	}

	/**
	 * ファクトリ
	 * @param nursingCareLeaveRemainingInfo
	 * @return
	 */
	static public CareLeaveRemainingInfo of(NursingCareLeaveRemainingInfo nursingCareLeaveRemainingInfo) {
		CareLeaveRemainingInfo c = new CareLeaveRemainingInfo(
				nursingCareLeaveRemainingInfo.getSId(),
				nursingCareLeaveRemainingInfo.getLeaveType(),
				nursingCareLeaveRemainingInfo.isUseClassification(),
				nursingCareLeaveRemainingInfo.getUpperlimitSetting(),
				nursingCareLeaveRemainingInfo.getMaxDayForThisFiscalYear(),
				nursingCareLeaveRemainingInfo.getMaxDayForNextFiscalYear());
		return c;
	}

	public static CareLeaveRemainingInfo createCareLeaveInfo(String sId, int useClassification, int upperlimitSetting,
			Integer maxDayForThisFiscalYear, Integer maxDayForNextFiscalYear) {
		return new CareLeaveRemainingInfo(sId,
				NursingCategory.Nursing,
				useClassification == 1,
				EnumAdaptor.valueOf(upperlimitSetting, UpperLimitSetting.class),
				maxDayForThisFiscalYear != null ? Optional.of(new ChildCareNurseUpperLimit(maxDayForThisFiscalYear))
						: Optional.empty(),
				maxDayForNextFiscalYear != null ? Optional.of(new ChildCareNurseUpperLimit(maxDayForNextFiscalYear))
						: Optional.empty());
	}

	public static CareLeaveRemainingInfo createCareLeaveInfoCps013(String sId, int useClassification, int upperlimitSetting,
			Integer maxDayForThisFiscalYear, Integer maxDayForNextFiscalYear) {
		return new CareLeaveRemainingInfo(sId,
				NursingCategory.Nursing,
				useClassification == 1,
				UpperLimitSetting.PER_INFO_EVERY_YEAR,
				maxDayForThisFiscalYear != null ? Optional.of(new ChildCareNurseUpperLimit(maxDayForThisFiscalYear))
				: Optional.empty(),
				maxDayForNextFiscalYear != null ? Optional.of(new ChildCareNurseUpperLimit(maxDayForNextFiscalYear))
				: Optional.empty());
	}
}
