package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.aftercorrectatt.CorrectionAfterTimeChange;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm.aftercorrectwork.CorrectionAfterChangeWorkInfo;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX
 *
 *         勤怠ルールの補正処理
 */
@Stateless
public class CorrectionAttendanceRule implements ICorrectionAttendanceRule {

	private DailyRecordToAttendanceItemConverter attendanceItemConvertFactory;

	@Inject
	private CorrectionAfterTimeChange correctionAfterTimeChange;

	@Inject
	private CorrectionAfterChangeWorkInfo correctionAfterChangeWorkInfo;

	// 勤怠ルールの補正処理
	@Override
	public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt) {

		String companyId = AppContexts.user().companyId();

		DailyRecordToAttendanceItemConverter converter = attendanceItemConvertFactory.setData(domainDaily).completed();
		List<Integer> atendanceId = converter.editStates().stream().filter(x -> x.isHandCorrect())
				.map(x -> x.getAttendanceItemId()).distinct().collect(Collectors.toList());

		// 補正前の状態を保持
		// IntegrationOfDaily beforeDomain = converter.toDomain();
		List<ItemValue> beforeItems = converter.convert(atendanceId);

		// 勤怠変更後の補正
		/// TODO: 設計中 waiting design map
		IntegrationOfDaily afterDomain = correctionAfterTimeChange.corection(companyId, domainDaily);

		// TODO: 設計中 waiting design map case 出退勤 .....
		if (changeAtt.workInfo) {
			// 変更する勤怠項目を確認
			/// TODO: processing mock new domain
			afterDomain = correctionAfterChangeWorkInfo.correction(companyId, afterDomain);

		}

		// 手修正を基に戻す
		DailyRecordToAttendanceItemConverter afterConverter = attendanceItemConvertFactory.setData(afterDomain)
				.completed();
		afterConverter.merge(beforeItems);

		return afterConverter.toDomain();
	}

}
