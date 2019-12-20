package nts.uk.ctx.hr.develop.dom.databeforereflecting.retirementinformation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.service.DataBeforeReflectingPerInfoService;
import nts.uk.ctx.hr.shared.dom.personalinfo.retirementinfo.RetirementCategory;

@Stateless
public class RetirementInformation_NewService {

	@Inject
	private DataBeforeReflectingPerInfoService dataBeforeReflectPerInfoService;

	/**
	 * 定年退職者情報の取得
	 * 
	 * @param ログイン会社ID
	 * @param （オプション）社員IDリスト
	 * @param （オプション）反映済みを含む
	 * @return List<退職者情報>
	 */
	public List<RetirementInformation_New> getRetirementInformation(String cId, List<String> employeeIds,
			Optional<Boolean> includingReflected) {
		// ドメイン [個人情報反映前データ] を取得する (Get domain "Data before reflecting personal
		// information/data trước khi phản ánh thông tin cá nhân")
		List<DataBeforeReflectingPerInfo> dataInfos = this.dataBeforeReflectPerInfoService.getDataBeforeReflectPerInfo(
				cId, 2, employeeIds, includingReflected, Optional.ofNullable("DATE01"), Optional.ofNullable("ASC"));
		// 個人情報反映前データリストを定年退職者情報リストへ変換する(Chuyển đổi list data trước khi phản ánh
		// thông tin các nhân sang list thông tin người nghỉ hưu)
		
		return dataInfos.stream().map(x ->
		RetirementInformation_New
				.builder()
				.historyId(x.getHistoryId())
				.desiredWorkingCourseCd(x.getSelect_code_02())
				.retirementDate(x.getDate_01())
				.releaseDate(x.getReleaseDate()).sId(x.getSId())
				.extendEmploymentFlg(EnumAdaptor.valueOf(Integer.valueOf(x.getSelect_code_04()) , ResignmentDivision.class))
				.employeeName(x.getPersonName()).scd(x.getScd()).companyId(x.getCompanyId()).workName(x.getWorkName())
				.status(EnumAdaptor.valueOf(x.getStattus().value, Status.class))
				.dst_HistId(x.getHistId_Refer())
				.desiredWorkingCourseId(x.getSelect_id_02())
				.pendingFlag(x.getOnHoldFlag())
				.companyCode(x.getCompanyCode())
				.PersonName(x.getPersonName())
				.desiredWorkingCourseName(x.getSelect_name_02())
				.contractCode(x.getContractCode())
				.workId(x.getWorkId())
				.inputDate(x.getRegisterDate())
				.retirementCategory(EnumAdaptor.valueOf(Integer.valueOf(x.getSelect_code_01()).intValue(),
						RetirementCategory.class))
				.notificationCategory(x.getRequestFlag())
				.retirementReasonCtgID1(x.getSelect_id_03())
				.retirementReasonCtgCd1(x.getSelect_code_03())
				.retirementReasonCtgName1(x.getSelect_name_03())
				.build()).collect(Collectors.toList());
	}

	// 定年退職者情報の変更
	// 定年退職者情報の削除
}
