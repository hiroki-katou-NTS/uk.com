package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public static List<RetentionCredentials> get(Require require, GeneralDate baseDate, String cId, List<String> sIds,
			boolean getEndDate, boolean getNameMaster, boolean category, boolean getRank, boolean getnumber,
			boolean getQualifiedOrganization) {
		
		List<RetentionCredentials> lstretentionCredentials = new ArrayList<>();
		List<PersonalInformation> informations = require.getLstPersonInfoByCIdSIdsWorkId(cId, sIds, 8, baseDate);
		
		if (!informations.isEmpty()) {
			lstretentionCredentials = informations.stream().map(m -> {
				RetentionCredentials credentials = new RetentionCredentials();
				credentials.setSId(m.getSid().map(s -> s).orElse(""));

				List<EligibilityQualification> eligibilities = new ArrayList<>();

				EligibilityQualification eligibility = new EligibilityQualification();

				eligibility.setQualificationCd(String.valueOf(m.getSelectId01()));
				eligibility.setQualificationId(m.getSelectCode01().map(s -> s).orElse(""));

				if (getEndDate) {
					eligibility.setEndDate(Optional.of(m.getEndDate()));
				}

				if (category && getNameMaster) {
					eligibility.setDivisionName(m.getSelectName02());
					eligibility.setQualificationName(m.getSelectName01());
					eligibility.setCategoryCd(m.getSelectCode02());

				} else {

					if (getNameMaster) {
						eligibility.setQualificationName(m.getSelectName01());
					}

					if (category) {
						eligibility.setCategoryCd(m.getSelectCode02());
					}
				}

				if (getRank) {
					eligibility.setQualificationRank(m.getStr03());
				}

				if (getnumber) {
					eligibility.setQualificationNumber(m.getStr04());
				}

				if (getQualifiedOrganization) {
					eligibility.setQualificationOrganization(m.getStr05());
				}

				eligibilities.add(eligibility);
				credentials.setEligibilitys(eligibilities);

				return credentials;
			}).collect(Collectors.toList());
		}
		return lstretentionCredentials;
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