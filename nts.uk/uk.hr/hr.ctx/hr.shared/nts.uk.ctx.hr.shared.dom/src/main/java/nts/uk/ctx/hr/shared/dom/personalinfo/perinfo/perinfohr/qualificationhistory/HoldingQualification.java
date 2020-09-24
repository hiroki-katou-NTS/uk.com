package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.資格取得履歴
 * (History get bằng cấp).基準日から有効な保有資格を取得する (Get bằng cấp hợp lệ từ base
 * Date).基準日から有効な保有資格を取得する
 * 
 * @author chungnt
 *
 */

public class HoldingQualification {

	/**
	 * 
	 * @param require
	 * @param baseDate                 基準日
	 * @param cId                      会社ID
	 * @param sIds                     List<社員ID>
	 * @param getEndDate               終了日を取得する *2
	 * @param getNameMaster            選択マスタの名称を取得する *1
	 * @param category                 社内外区分を取得する *3
	 * @param getRank                  資格認定ランクを取得する *4
	 * @param getnumber                資格認定番号を取得する *5
	 * @param getQualifiedOrganization 資格認定団体を取得する *6
	 * @return
	 */
	public static List<HoldingQualificationOutput> get(Require require, GeneralDate baseDate, String cId, List<String> sIds,
			boolean getEndDate, boolean getNameMaster, boolean category, boolean getRank, boolean getnumber,
			boolean getQualifiedOrganization) {

		List<HoldingQualificationOutput> outputs = new ArrayList<>();

		List<PersonalInformation> informations = require.getLstPersonInfoByCIdSIdsWorkId(cId, sIds, 8, baseDate);

		if (informations == null) {
			return outputs;
		}

		if (!sIds.isEmpty()) {
			for (int i = 0; i < sIds.size(); i++) {
				String sId = sIds.get(i);
				List<EligibilityQualification> eligibilityQualifications = new ArrayList<>();
				for (int j = 0; j < informations.size(); j++) {
					PersonalInformation information = informations.get(j);
					if (information.getSid() != null || information.getSid().isPresent()) {
						if (sId.equals(information.getSid().get())) {
							
							EligibilityQualification eligibility = new EligibilityQualification();
							
							eligibility.setQualificationCd(String.valueOf(information.getSelectId01()));
							eligibility.setQualificationId(information.getSelectCode01().map(s -> s).orElse(""));
							
							if (getEndDate) {
								eligibility.setEndDate(information.getEndDate());
							}	
							
							if (category && getNameMaster) {
								eligibility.setDivisionName(information.getSelectName02().map(c -> c).orElse(""));
								eligibility.setQualificationName(information.getSelectName01().map(c -> c).orElse(""));
								eligibility.setCategoryCd(information.getSelectCode02().map(c -> c).orElse(""));
				
							} else {
					
								if (getNameMaster) {
									eligibility.setQualificationName(information.getSelectName01().map(c -> c).orElse(""));
								}
					
								if (category) {
									eligibility.setCategoryCd(information.getSelectCode02().map(c -> c).orElse(""));
								}
							}
							
							
							if (getRank) {
								eligibility.setQualificationRank(information.getStr03().map(c -> c).orElse(""));
							}
			
							if (getnumber) {
								eligibility.setQualificationNumber(information.getStr04().map(c -> c).orElse(""));
							}
			
							if (getQualifiedOrganization) {
								eligibility.setQualificationOrganization(information.getStr05().map(c -> c).orElse(""));
							}
							
							eligibilityQualifications.add(eligibility);
						}
					}
				}
				HoldingQualificationOutput holdingQualification = new HoldingQualificationOutput(sId, eligibilityQualifications);
				outputs.add(holdingQualification);
			}
		}

		return outputs;
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