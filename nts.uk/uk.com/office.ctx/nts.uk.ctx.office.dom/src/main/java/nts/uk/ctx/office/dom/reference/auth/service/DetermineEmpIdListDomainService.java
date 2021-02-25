package nts.uk.ctx.office.dom.reference.auth.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeJobHistImport;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiry;

/*
 * Domain service 社員IDリストから参照できるか判断する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.参照権限.社員IDリストから参照できるか判断する
 */
public class DetermineEmpIdListDomainService {

	private DetermineEmpIdListDomainService() {}
	
	/**
	 * [1] 参照できるか判断する
	 * 
	 * @param require
	 * @param sids     List<社員ID>
	 * @param baseDate 基準日
	 * @return List<String> List<社員ID>
	 */
	public static List<String> determineReferenced(Require require, List<String> sids, GeneralDate baseDate, String loginCid, String loginRoleid, String loginSid) {
		// 在席照会で参照できる権限の指定を取得する
		Optional<SpecifyAuthInquiry> specifyAuthInquiry = require.getByCidAndRoleId(loginCid, loginRoleid);
		List<String> positionIdSeen = specifyAuthInquiry.map(item -> item.getPositionIdSeen())
				.orElse(Collections.emptyList());
		if (positionIdSeen.isEmpty()) {
			return Collections.emptyList();
		}
		// 社員の職位を取得する
		Map<String, EmployeeJobHistImport> map = require.getPositionBySidsAndBaseDate(sids, baseDate);
		return sids.stream()
				.filter(sid -> {
					if(map.get(sid) == null) {
						return false;
					}
					return !sid.equalsIgnoreCase(loginSid) && 
					positionIdSeen.contains(map.get(sid).getJobTitleID());
				})
				.collect(Collectors.toList());
	}

	public static interface Require {
		/**
		 * [R-1] 参照できる権限を取得する
		 * 
		 * 在席照会で参照できる権限の指定Repository.取得する(会社ID、ロールID)
		 * 
		 * @param cid    会社ID
		 * @param roleId ロールID
		 * @return Optional<SpecifyAuthInquiry> Optional<在席照会で参照できる権限の指定>
		 */
		public Optional<SpecifyAuthInquiry> getByCidAndRoleId(String cid, String roleId);

		/**
		 * [R-2] 社員の職位を取得する
		 * 
		 * 社員の職位を取得するAdapter.社員の職位を取得する(社員IDリスト,基準日)
		 * 
		 * @param wpkIds   - 社員IDリスト
		 * @param baseDate - 基準日
		 * @return Map<社員ID、職場ID>
		 */
		public Map<String, EmployeeJobHistImport> getPositionBySidsAndBaseDate(List<String> sIds, GeneralDate baseDate);
	}

}
