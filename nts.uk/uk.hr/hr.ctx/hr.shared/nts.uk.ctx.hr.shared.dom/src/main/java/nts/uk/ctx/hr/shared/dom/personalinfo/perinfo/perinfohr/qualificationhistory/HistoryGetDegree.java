package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.資格取得履歴 .基準日、資格リストから保有者を取得する .基準日、資格リストから保有者を取得する
 * @author chungnt
 *
 */
public class HistoryGetDegree {

	
	public static List<QualificationHolderInformation> get(Require require, GeneralDate baseDate, String cId,
			List<String> qualificationIds, boolean getEmployeeCode, boolean getEmployeeName) {

		List<QualificationHolderInformation> qualificationHolderInformations = new ArrayList<>();
		
		List<PersonalInformation> informations = require.getLstPersonInfoByQualificationIds(cId, qualificationIds, 8,
				baseDate);
		
		if (!informations.isEmpty()) {
			qualificationHolderInformations = informations.stream().map(m -> {
				QualificationHolderInformation holderInformation = new QualificationHolderInformation();
				
				holderInformation.setQualificationId(String.valueOf(m.getSelectId01()));
				
				List<Holder> holders = new ArrayList<>();
				Holder holder = new Holder();
	
				holder.setEmployeeId(m.getSid().map(c -> c).orElse(""));
				
				if (getEmployeeCode) {
					holder.setEmployeeCd(m.getScd().map(c -> c).orElse(""));
				}
				
				if (getEmployeeName) {
					holder.setEmployeeName(m.getPersonName());
				}
				
				holderInformation.setHolders(holders);
				
				holders.add(holder);
				
				return holderInformation;
			}).collect(Collectors.toList());
		}

		return qualificationHolderInformations;
	}
	
	public static interface Require {

		List<PersonalInformation> getLstPersonInfoByQualificationIds(String cId, List<String> qualificationIds,
				int workId, GeneralDate baseDate);
	}
}