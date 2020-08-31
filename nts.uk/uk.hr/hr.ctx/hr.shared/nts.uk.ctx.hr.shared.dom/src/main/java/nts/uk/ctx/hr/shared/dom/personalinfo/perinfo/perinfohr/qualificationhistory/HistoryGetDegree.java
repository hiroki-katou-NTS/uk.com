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

		List<PersonalInformation> informations = require.getLstPersonInfoByQualificationIds(cId, qualificationIds, 8,
				baseDate);
		
		if (informations == null) {
			return hashList;
		}

		informations.stream()
			.forEach(m -> {
				Long key = m.getSelectId01();
	
				if (!hashList.containsKey(key)) {
					hashList.put(key, new ArrayList<Holder>());
				}
	
				List<Holder> holders = hashList.get(key);
	
				Holder holder = new Holder();
	
				holder.setEmployeeId(m.getSid().map(c -> c).orElse(""));
	
				if (getEmployeeCode) {
					holder.setEmployeeCd(m.getScd().map(c -> c).orElse(""));
				}
	
				if (getEmployeeName) {
					holder.setEmployeeName(m.getPersonName().map(c -> c).orElse(""));
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