package nts.uk.ctx.office.dom.favorite.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBasicImport;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeJobHistImport;
import nts.uk.ctx.office.dom.favorite.adapter.SequenceMasterImport;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforImport;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.個人情報を取得
 * 
 * 
 * 個人情報を取得
 */
public class PersonalInfomationDomainService {

	@Inject

	public PersonalInfomationDomainService() {
	}

	/**
	 * [pvt-1]ソートする処理
	 * 
	 * ソート：職場 → 職位 → 社員コード
	 * 
	 * @param sIds
	 * @param workplaceInfo
	 * @param positionId
	 * @param positionOder
	 * @param personalInfo
	 * @return
	 */
	private List<PersonalInfomationObj> sortingProcess(List<String> sIds, Map<String, WorkplaceInforImport> workplaceInfo,
			Map<String, EmployeeJobHistImport> positionId, String positionOrder, Map<String, EmployeeBasicImport> personalInfo) {
		// create ソート情報：｛社員ID、階層コード、Optional<並び順>、職位コード、社員コード｝
//		List<PersonalInfomationObj> sortInfomation = sIds.stream().map(v -> {
//			return PersonalInfomationObj.builder()
//					.sid(v)
//					.hierarchyCode(workplaceInfo.get(v).getHierarchyCode())
//					.order(Optional.empty())
//					.positionCode(null)
//					.employeeCode(personalInfo.get(v).getEmployeeCode())
//					.build()
//			}).collect(Collectors.toList());
//		}
		return Collections.emptyList();
	}

	public static interface Require {

		/**
		 * [R-1] 社員の職場IDを取得する
		 * 
		 * 社員の職場IDを取得するAdapter.自分の職場IDを取得する(社員IDリスト、基準日)
		 * 
		 */
		public Map<String, String> getEmployeesWorkplaceId(List<String> sIds, GeneralDate baseDate);

		/**
		 * [R-2]職場情報を取得する
		 * 
		 * 職場情報を取得するAdapter.職場情報を取得する(職場IDリスト,基準日)
		 * 
		 */
		public Map<String, WorkplaceInforImport> getWorkplaceInfor(List<String> lstWorkplaceID, GeneralDate baseDate);

		/**
		 * [R-3] 社員の職位を取得する
		 * 
		 * 社員の職位を取得するAdapter.社員の職位を取得する(社員IDリスト,基準日)
		 * 
		 */
		public Map<String, EmployeeJobHistImport> getPositionBySidsAndBaseDate(List<String> sIds, GeneralDate baseDate);

		/**
		 * [R-4]職位の序列を取得する
		 * 
		 * 職位の序列を取得するAdapter.職位の序列を取得する()
		 * 
		 */
		public List<SequenceMasterImport> getRankOfPosition();

		/**
		 * [R-5] 個人情報を取得する
		 * 
		 * 個人情報を取得するAdapter.個人情報を取得する(社員IDリスト)
		 * 
		 */
		public Map<String, EmployeeBasicImport> getPersonalInformation(List<String> lstSid);

	}
}
