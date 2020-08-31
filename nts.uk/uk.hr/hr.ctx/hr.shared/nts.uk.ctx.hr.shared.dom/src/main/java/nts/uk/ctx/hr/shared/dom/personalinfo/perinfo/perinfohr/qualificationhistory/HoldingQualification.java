package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.資格取得履歴 (History get bằng cấp).基準日から有効な保有資格を取得する (Get bằng cấp hợp lệ từ base Date).基準日から有効な保有資格を取得する
 * @author chungnt
 *
 */

public class HoldingQualification {
	
	/**
	 * 
	 * @param require
	 * @param baseDate						基準日
	 * @param cId							会社ID
	 * @param sIds							List<社員ID>
	 * @param getEndDate					終了日を取得する			*2
	 * @param getNameMaster					選択マスタの名称を取得する		*1
	 * @param category						社内外区分を取得する		*3
	 * @param getRank						資格認定ランクを取得する		*4
	 * @param getnumber						資格認定番号を取得する		*5
	 * @param getQualifiedOrganization		資格認定団体を取得する		*6
	 * @return
	 */
	public static HashMap<String, List<EligibilityQualification>> get(Require require, GeneralDate baseDate, String cId, List<String> sIds,
			boolean getEndDate, boolean getNameMaster, boolean category, boolean getRank, boolean getnumber,
			boolean getQualifiedOrganization) {
		
		HashMap<String, List<EligibilityQualification>> hashList = new HashMap<String, List<EligibilityQualification>>();
		
		List<PersonalInformation> informations = require.getLstPersonInfoByCIdSIdsWorkId(cId, sIds, 8, baseDate);
		
		if (informations == null) {
			return hashList;
		}
		
		informations.stream()
			.forEach(m -> {
				String key = m.getSid().map(c -> c).orElse("");
				
				if (!hashList.containsKey(key)) {
					hashList.put(key, new ArrayList<EligibilityQualification>());
				}
				
				List<EligibilityQualification> eligibilities = hashList.get(key);
				
				EligibilityQualification eligibility = new EligibilityQualification();
				
				eligibility.setQualificationCd(String.valueOf(m.getSelectId01()));
				eligibility.setQualificationId(m.getSelectCode01().map(s -> s).orElse(""));
				
				if (getEndDate) {
					eligibility.setEndDate(m.getEndDate());
				}	
				
				if (category && getNameMaster) {
					eligibility.setDivisionName(m.getSelectName02().map(c -> c).orElse(""));
					eligibility.setQualificationName(m.getSelectName01().map(c -> c).orElse(""));
					eligibility.setCategoryCd(m.getSelectCode02().map(c -> c).orElse(""));
	
				} else {
		
					if (getNameMaster) {
						eligibility.setQualificationName(m.getSelectName01().map(c -> c).orElse(""));
					}
		
					if (category) {
						eligibility.setCategoryCd(m.getSelectCode02().map(c -> c).orElse(""));
					}
				}
				
				
				if (getRank) {
					eligibility.setQualificationRank(m.getStr03().map(c -> c).orElse(""));
				}

				if (getnumber) {
					eligibility.setQualificationNumber(m.getStr04().map(c -> c).orElse(""));
				}

				if (getQualifiedOrganization) {
					eligibility.setQualificationOrganization(m.getStr05().map(c -> c).orElse(""));
				}
				
				eligibilities.add(eligibility);
				
			});
			
		return hashList;
	}

	public static interface Require {
		/**
		 * 
		 * @param cId
		 * @param sids
		 * @param workId
		 * @param baseDate
		 * @return
		 */
		List<PersonalInformation> getLstPersonInfoByCIdSIdsWorkId(String cId, List<String> sids, int workId,
				GeneralDate baseDate);
	}
}