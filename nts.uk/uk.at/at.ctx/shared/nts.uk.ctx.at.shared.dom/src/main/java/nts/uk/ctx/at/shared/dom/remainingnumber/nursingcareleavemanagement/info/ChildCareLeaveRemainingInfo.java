package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;

/**
 * 子の看護休暇基本情報
 * @author danpv
 *
 */
public class ChildCareLeaveRemainingInfo extends NursingCareLeaveRemainingInfo implements DomainAggregate {

	/**
	 * コンストラクタ
	 * @param sId
	 * @param leaveType
	 * @param useClassification
	 * @param upperlimitSetting
	 * @param maxDayForThisFiscalYear
	 * @param maxDayForNextFiscalYear
	 */
	public ChildCareLeaveRemainingInfo(String sId, NursingCategory leaveType, boolean useClassification,
			UpperLimitSetting upperlimitSetting, Optional<ChildCareNurseUpperLimit> maxDayForThisFiscalYear,
			Optional<ChildCareNurseUpperLimit> maxDayForNextFiscalYear) {
		super(sId, leaveType, useClassification, upperlimitSetting, maxDayForThisFiscalYear, maxDayForNextFiscalYear);
	}

	/**
	 * ファクトリ
	 * @param nursingCareLeaveRemainingInfo
	 * @return
	 */
	static public ChildCareLeaveRemainingInfo of(NursingCareLeaveRemainingInfo nursingCareLeaveRemainingInfo) {
		ChildCareLeaveRemainingInfo c = new ChildCareLeaveRemainingInfo(
				nursingCareLeaveRemainingInfo.getSId(),
				nursingCareLeaveRemainingInfo.getLeaveType(),
				nursingCareLeaveRemainingInfo.isUseClassification(),
				nursingCareLeaveRemainingInfo.getUpperlimitSetting(),
				nursingCareLeaveRemainingInfo.getMaxDayForThisFiscalYear(),
				nursingCareLeaveRemainingInfo.getMaxDayForNextFiscalYear());
		return c;
	}

	/**
	 * ファクトリー
	 * @param sId 社員ID
	 * @param leaveType 介護看護区分
	 * @param useClassification 使用区分
	 * @param upperlimitSetting 上限設定
	 * @param maxDayForThisFiscalYear 本年度上限日数
	 * @param maxDayForNextFiscalYear 次年度上限日数
	 * @return 子の看護休暇基本情報（ChildCareLeaveRemainingInfo）
	 */
	public static ChildCareLeaveRemainingInfo of(
			String sId,
			NursingCategory leaveType,
			boolean useClassification,
			UpperLimitSetting upperlimitSetting,
			Optional<ChildCareNurseUpperLimit> maxDayForThisFiscalYear,
			Optional<ChildCareNurseUpperLimit> maxDayForNextFiscalYear) {

		ChildCareLeaveRemainingInfo domain = new ChildCareLeaveRemainingInfo(sId, leaveType,
				useClassification,
				upperlimitSetting,
				maxDayForThisFiscalYear,
				maxDayForNextFiscalYear);
		return domain;

	}

	public static ChildCareLeaveRemainingInfo createChildCareLeaveInfo(String sId, int useClassification,
			int upperlimitSetting, Integer maxDayForThisFiscalYear, Integer maxDayForNextFiscalYear) {
		return new ChildCareLeaveRemainingInfo(sId, NursingCategory.ChildNursing, useClassification == 1,
				EnumAdaptor.valueOf(upperlimitSetting, UpperLimitSetting.class),
				maxDayForThisFiscalYear != null ? Optional.of(new ChildCareNurseUpperLimit(maxDayForThisFiscalYear))
						: Optional.empty(),
				maxDayForNextFiscalYear != null ? Optional.of(new ChildCareNurseUpperLimit(maxDayForNextFiscalYear))
						: Optional.empty());
	}

	public static ChildCareLeaveRemainingInfo createChildCareLeaveInfoCps013(String sId, int useClassification,
			int upperlimitSetting, Integer maxDayForThisFiscalYear, Integer maxDayForNextFiscalYear) {
		return new ChildCareLeaveRemainingInfo(sId,
				NursingCategory.ChildNursing,
				useClassification == 1,
				UpperLimitSetting.PER_INFO_EVERY_YEAR,
				maxDayForThisFiscalYear != null ? Optional.of(new ChildCareNurseUpperLimit(maxDayForThisFiscalYear)): Optional.empty(),
				maxDayForNextFiscalYear != null ? Optional.of(new ChildCareNurseUpperLimit(maxDayForNextFiscalYear)): Optional.empty());
	}
}
