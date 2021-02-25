package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.資格取得履歴
 * .基準日、資格リストから保有者を取得する .基準日、資格リストから保有者を取得する
 * 
 * @author chungnt
 *
 */
public class HistoryGetDegree {

	public static List<HistoryGetDegreeOutput> get(Require require, GeneralDate baseDate, String cId,
			List<String> qualificationIds, boolean getEmployeeCode, boolean getEmployeeName) {

		List<HistoryGetDegreeOutput> outputs = new ArrayList<>();

		List<PersonalInformation> informations = require.getLstPersonInfoByQualificationIds(cId, qualificationIds, 8,
				baseDate);

		if (informations == null) {
			return outputs;
		}

		if (!qualificationIds.isEmpty()) {
			for (int i = 0; i < qualificationIds.size(); i++) {
				String qualificationId = qualificationIds.get(i);
				List<Holder> holders = new ArrayList<>();
				for (int j = 0; j < informations.size(); j++) {
					PersonalInformation information = informations.get(j);
					if (information.getSelectId01() != null) {
						if (qualificationId.equals(String.valueOf(information.getSelectId01()))) {

							Holder holder = new Holder();

							if (information.getSid() != null) {
								holder.setEmployeeId(information.getSid().map(c -> c).orElse(""));
							}

							if (getEmployeeCode) {
								if (information.getScd() != null) {
									holder.setEmployeeCd(information.getScd().map(c -> c).orElse(""));
								}
							}

							if (getEmployeeName) {
								if (information.getPersonName() != null) {
									holder.setEmployeeName(information.getPersonName().map(c -> c).orElse(""));
								}
							}

							holders.add(holder);
						}
					}
				}
				HistoryGetDegreeOutput historyGetDegreeOutput = new HistoryGetDegreeOutput(qualificationId, holders);
				outputs.add(historyGetDegreeOutput);
			}
		}

		return outputs;
	}

	public static interface Require {

		List<PersonalInformation> getLstPersonInfoByQualificationIds(String cId, List<String> qualificationIds,
				int workId, GeneralDate baseDate);
	}
}