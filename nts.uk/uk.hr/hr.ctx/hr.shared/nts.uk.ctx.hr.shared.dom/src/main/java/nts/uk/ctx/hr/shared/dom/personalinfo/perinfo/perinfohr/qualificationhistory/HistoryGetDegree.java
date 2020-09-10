package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.ArrayList;
import java.util.HashMap;
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

	public static HashMap<Long, List<Holder>> get(Require require, GeneralDate baseDate, String cId,
			List<String> qualificationIds, boolean getEmployeeCode, boolean getEmployeeName) {

		HashMap<Long, List<Holder>> hashList = new HashMap<Long, List<Holder>>();

		List<PersonalInformation> informations1 = require.getLstPersonInfoByQualificationIds(cId, qualificationIds, 8,
				baseDate);

		if (informations1 == null) {
			return hashList;
		}

		boolean check = false;

		List<PersonalInformation> informations = new ArrayList<>();

		for (int i = 0; i < qualificationIds.size(); i++) {
			for (int j = 0; j < informations1.size(); j++) {
				if (String.valueOf(informations1.get(j).getSelectId01()).equals(qualificationIds.get(i))) {
					informations.add(informations1.get(j));
					check = true;
				}
				if (j == informations1.size() - 1) {
					if (!check) {
						PersonalInformation information = new PersonalInformation();
						information.setSelectId01(Long.valueOf(qualificationIds.get(i)));
						informations.add(information);
						check = false;
					} else {
						check = false;
					}
				}
			}
		}

		if (informations == null) {
			return hashList;
		}

		informations.stream().forEach(m -> {
			Long key = m.getSelectId01();

			if (!hashList.containsKey(key)) {
				hashList.put(key, new ArrayList<Holder>());
			}

			List<Holder> holders = hashList.get(key);

			Holder holder = new Holder();

			if (m.getSid() != null) {
				holder.setEmployeeId(m.getSid().map(c -> c).orElse(""));
			}

			if (getEmployeeCode) {
				if (m.getScd() != null) {
					holder.setEmployeeCd(m.getScd().map(c -> c).orElse(""));
				}
			}

			if (getEmployeeName) {
				if (m.getPersonName() != null) {
					holder.setEmployeeName(m.getPersonName().map(c -> c).orElse(""));
				}
			}
			holders.add(holder);

		});

		return hashList;
	}

	public static interface Require {

		List<PersonalInformation> getLstPersonInfoByQualificationIds(String cId, List<String> qualificationIds,
				int workId, GeneralDate baseDate);
	}
}