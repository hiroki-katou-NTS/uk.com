package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;

/**
 * @author thanh_nx
 *
 *         代休設定を取得する
 */
public class GetSubHolOccurrenceSetting {

	public static Optional<SubHolTransferSet> process(Require require, String cid, Optional<String> workTimeCode,
			CompensatoryOccurrenceDivision originAtr) {
		Optional<FlowWorkSetting> flowSetting = Optional.empty();
		if (workTimeCode.isPresent()) {
			flowSetting = require.findFlowWorkSetting(cid, workTimeCode.get());
		}

		if (flowSetting.isPresent() && flowSetting.get().getCommonSetting().getSubHolTimeSet().stream()
				.filter(x -> x.getOriginAtr() == originAtr).findFirst().map(x -> x.getSubHolTimeSet().isUseDivision())
				.orElse(false)) {
			return flowSetting.get().getCommonSetting().getSubHolTimeSet().stream()
					.filter(x -> x.getOriginAtr() == originAtr).map(x -> x.getSubHolTimeSet()).findFirst();
		}
		val comLeavSet = require.findCompensatoryLeaveComSet(cid);
		if (comLeavSet == null)
			return Optional.empty();
		SubHolTransferSet result = comLeavSet.getCompensatoryOccurrenceSetting().stream()
				.filter(x -> x.getOccurrenceType().value == originAtr.value).map(x -> x.getTransferSetting())
				.findFirst().orElse(null);
		return (result != null && result.isUseDivision()) ? Optional.of(result) : Optional.empty();
	}

	public static interface Require {

		// FlowWorkSettingRepository
		Optional<FlowWorkSetting> findFlowWorkSetting(String companyId, String workTimeCode);

		// CompensLeaveComSetRepository
		CompensatoryLeaveComSetting findCompensatoryLeaveComSet(String companyId);
	}
}
