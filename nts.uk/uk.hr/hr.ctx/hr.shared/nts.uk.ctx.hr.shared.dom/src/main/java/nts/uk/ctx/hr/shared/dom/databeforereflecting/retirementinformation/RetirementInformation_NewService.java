package nts.uk.ctx.hr.shared.dom.databeforereflecting.retirementinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingRepository;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.RequestFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.service.DataBeforeReflectingPerInfoService;
import nts.uk.ctx.hr.shared.dom.personalinfo.retirementinfo.RetirementCategory;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RetirementInformation_NewService {

	@Inject
	private DataBeforeReflectingPerInfoService dataBeforeReflectPerInfoService;

	@Inject
	private DataBeforeReflectingRepository repo;

	/**
	 * 個人情報反映前データリストを定年退職者情報リストへ変換する
	 * 
	 * @param 個人情報反映前データ
	 * @return 個人情報反映前データ_定年退職者情報
	 */
	public RetirementInformation_New toRetirementInformation_New(DataBeforeReflectingPerInfo datareflect) {

		return RetirementInformation_New.builder().historyId(datareflect.getHistoryId())
				.desiredWorkingCourseCd(datareflect.getSelect_code_02()).retirementDate(datareflect.getDate_01())
				.releaseDate(datareflect.getReleaseDate()).sId(datareflect.getSId())
				.extendEmploymentFlg(datareflect.getSelect_code_04() != null ? EnumAdaptor
						.valueOf(Integer.valueOf(datareflect.getSelect_code_04()), ResignmentDivision.class) : null)
				.employeeName(datareflect.getPersonName()).scd(datareflect.getScd())
				.companyId(datareflect.getCompanyId()).workName(datareflect.getWorkName())
				.status(datareflect.getStattus() != null
						? EnumAdaptor.valueOf(datareflect.getStattus().value, Status.class) : null)
				.dst_HistId(datareflect.getHistId_Refer()).desiredWorkingCourseId(datareflect.getSelect_id_02())
				.pendingFlag(datareflect.getOnHoldFlag()).companyCode(datareflect.getCompanyCode())
				.PersonName(datareflect.getPersonName()).desiredWorkingCourseName(datareflect.getSelect_name_02())
				.contractCode(datareflect.getContractCode()).workId(datareflect.getWorkId())
				.inputDate(datareflect.getRegisterDate())
				.retirementCategory(datareflect.getSelect_code_01() != null ? EnumAdaptor.valueOf(
						Integer.valueOf(datareflect.getSelect_code_01()).intValue(), RetirementCategory.class) : null)
				.notificationCategory(datareflect.getRequestFlag())
				.retirementReasonCtgID1(datareflect.getSelect_id_03())
				.retirementReasonCtgCd1(datareflect.getSelect_code_03())
				.retirementReasonCtgName1(datareflect.getSelect_name_03()).build();
	}

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
				cId, 2, employeeIds, new ArrayList<>() , includingReflected, Optional.ofNullable("date_01"), Optional.ofNullable("ASC"));
		// 個人情報反映前データリストを定年退職者情報リストへ変換する(Chuyển đổi list data trước khi phản ánh
		// thông tin các nhân sang list thông tin người nghỉ hưu)

		return dataInfos.stream().map(x -> toRetirementInformation_New(x)).collect(Collectors.toList());
	}

	/**
	 * 定年退職者情報の新規登録_変更
	 * 
	 * @param List(個人情報反映前データ)
	 */
	public void registerRetirementInformation(List<DataBeforeReflectingPerInfo> listDomain) {
		// 定年退職者情報リストを個人情報反映前データリストへ変換する(Chuyển đổi RetirementInfoList thành
		// list data trước khi phản ánh thông tin cá nhân)

		List<DataBeforeReflectingPerInfo> addListDomain = listDomain.stream().filter(x -> x.getHistoryId() == null)
				.collect(Collectors.toList());

		List<DataBeforeReflectingPerInfo> updateListDomain = listDomain.stream().filter(x -> x.getHistoryId() != null)
				.collect(Collectors.toList());
		// 個人情報反映前データを変更する (Thay đổi data trước khi phản ánh thông tin cá nhân)

		if (!addListDomain.isEmpty()) {
			String cId = AppContexts.user().companyId();
			String cCD = AppContexts.user().companyCode();
			addListDomain.forEach(x -> {
				x.setHistoryId(IdentifierUtil.randomUniqueId());
				x.setCompanyId(cId);
				x.setCompanyCode(cCD);
				x.setWorkId(2);
				x.setRequestFlag(RequestFlag.Normal);
				x.setSelect_code_01("4");
				x.setSelect_code_03("2");
			});
			this.repo.addDataNoCheckSid(addListDomain);
		}

		if (!updateListDomain.isEmpty()) {
			this.repo.updateData(updateListDomain.stream()
					.filter(x -> !x.getStattus().equals(Status.Approved) && !x.getStattus().equals(Status.Reflected))
					.collect(Collectors.toList()));
		}
	}

	/**
	 * 定年退職者情報の削除
	 * 
	 * @param List(個人情報反映前データ)
	 */
	public void deleteRetirementInformation(List<DataBeforeReflectingPerInfo> listDomain) {
		// 定年退職者情報リストを個人情報反映前データリストへ変換する(Chuyển đổi RetirementInfoList thành
		// List data trước khi phản ánh thông tin cá nhân)

		// 個人情報反映前データを削除する (Delete data before reflecting personal information)
		this.repo.deleteData(listDomain);
	}

	/**
	 * 定年退職者情報リストを個人情報反映前データリストへ変換する vì có một số thuộc tính của
	 * DataBeforeReflectingPerInfo mà RetirementInformation_New không có nên
	 * phải map từ tầng command , không map được trong này nên input của những
	 * method sẽ nhận vào DataBeforeReflectingPerInfo luôn chứ không qua bước
	 * chuyển đổi này
	 */

}
