package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.getfirstreflect;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.EndStatus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeReflectFromWorkinfo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別実績の一覧から一個目反映範囲を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績処理.作成処理.日別作成Mgrクラス.アルゴリズム.社員の日別実績を作成する.日別実績の一覧から一個目反映範囲を取得する.日別実績の一覧から一個目反映範囲を取得する
 * 
 * @author tutk
 *
 */
@Stateless
public class GetFirstReflectFromListDaily {

	@Inject
	private TimeReflectFromWorkinfo fromWorkinfo;
	
	/**
	 * 
	 * @param listIntegrationOfDaily 日別実績一覧
	 * @param startCategory          開始区分
	 */
	public GetFirstReflectOutput get(List<IntegrationOfDaily> listIntegrationOfDaily, boolean startCategory) {
		String companyId = AppContexts.user().companyId();
		if (listIntegrationOfDaily.isEmpty()) {
			return new GetFirstReflectOutput();
		}
		// 日別実績を取得する
		IntegrationOfDaily integrationOfDaily = listIntegrationOfDaily.get(0);
		if (!startCategory) {
			integrationOfDaily = listIntegrationOfDaily.get(listIntegrationOfDaily.size() - 1);
		}
		// 勤務情報から打刻反映時間帯を取得する
		OutputTimeReflectForWorkinfo workinfo = fromWorkinfo.get(companyId, integrationOfDaily.getEmployeeId(),
				integrationOfDaily.getYmd(), integrationOfDaily.getWorkInformation());
		if (workinfo.getEndStatus() == EndStatus.NORMAL) {
			// 反映範囲を返す
			return new GetFirstReflectOutput(Optional.of(workinfo.getStampReflectRangeOutput()),
					Optional.of(integrationOfDaily.getYmd()));
		}
		return new GetFirstReflectOutput();

	}
}
