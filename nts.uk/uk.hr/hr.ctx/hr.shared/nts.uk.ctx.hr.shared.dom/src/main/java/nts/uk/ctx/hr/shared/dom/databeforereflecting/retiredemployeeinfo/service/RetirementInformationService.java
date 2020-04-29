
package nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingRepository;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.NoteRetiment;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.OnHoldFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.RequestFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.Status;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.service.DataBeforeReflectingPerInfoService;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.RetirementCategory;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.RetirementInformation;
import nts.uk.ctx.hr.shared.dom.employee.AffCompanyHistItemImport;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RetirementInformationService {

	@Inject
	DataBeforeReflectingPerInfoService dataBeforeReflectPerInfoService;
	
	@Inject
	private EmployeeInformationAdaptor empInfoAdaptor;
	
	@Inject
	private  DataBeforeReflectingRepository dataBeforeReflectingRepo;

	// 退職者情報の取得
	public List<RetirementInformation> getRetirementInfo(String cid, List<String> listSid, Optional<Boolean> includReflected) {

		List<DataBeforeReflectingPerInfo> listDataBeforeReflectPerInfo = dataBeforeReflectPerInfoService
				.getDataBeforeReflectPerInfo(cid, 1, listSid, new ArrayList<>(), 
				includReflected, Optional.ofNullable("date_01"), Optional.ofNullable("ASC"));
		return convertData(listDataBeforeReflectPerInfo);
	}
	
	// 承認データの取得
	public List<RetirementInformation> getApproveData(String cid, String sID, Optional<Boolean> includReflected) {

		List<DataBeforeReflectingPerInfo> listDataBeforeReflectPerInfo = dataBeforeReflectPerInfoService
				.getDataBeforeReflectPerInfo(cid, 1, sID, includReflected,
						Optional.ofNullable("date_01"), Optional.ofNullable("ASC"));
		return convertData(listDataBeforeReflectPerInfo);
	}

	// Convert DataBeforeReflectingPerInfo list into RetirementInformation list
	private List<RetirementInformation> convertData(List<DataBeforeReflectingPerInfo> listDataBeforeReflectPerInfo) {

		if (listDataBeforeReflectPerInfo.isEmpty()) {
			return new ArrayList<>();
		}

		List<RetirementInformation> result = listDataBeforeReflectPerInfo.stream().map(i -> {
			RetirementInformation obj = new RetirementInformation();
			obj.historyId = i.historyId;
			obj.contractCode = i.contractCode;
			obj.companyId = i.companyId;
			obj.companyCode = i.companyCode;
			obj.workId = i.workId;
			obj.workName = i.workName;
			obj.notificationCategory = i.requestFlag.value;
			obj.inputDate = i.registerDate;
			obj.pendingFlag = i.onHoldFlag.value;
			obj.status = i.stattus.value;
			obj.histId_Refer = i.histId_Refer;
			obj.releaseDate = i.releaseDate;
			obj.pId = i.pId;
			obj.sId = i.sId;
			obj.scd = i.scd;
			obj.personName = i.personName;
			obj.retirementDate = i.date_01;
			obj.retirementCategory = EnumAdaptor.valueOf(Integer.valueOf(i.select_code_01).intValue(),
					RetirementCategory.class);
			obj.retirementReasonCtgCode1 = i.select_code_02;
			obj.retirementReasonCtgCode2 = i.select_code_03;
			obj.retirementRemarks = i.str_01 == null ? "" : i.str_01.toString();
			obj.retirementReasonVal = i.str_02 == null ? "" : i.str_02.toString();
			obj.dismissalNoticeDate = i.date_02 != null ? GeneralDate.legacyDate(i.date_02.date()) : null;
			obj.dismissalNoticeDateAllow = i.date_03 != null ? GeneralDate.legacyDate(i.date_03.date()) : null;
			obj.reaAndProForDis = i.str_03 == null ? "" : i.str_03.toString();
			obj.naturalUnaReasons_1 = i.int_01;
			obj.naturalUnaReasons_2 = i.int_02;
			obj.naturalUnaReasons_3 = i.int_03;
			obj.naturalUnaReasons_4 = i.int_04;
			obj.naturalUnaReasons_5 = i.int_05;
			obj.naturalUnaReasons_6 = i.int_06;
			obj.naturalUnaReasons_1Val = i.str_04 == null ? "" : i.str_04.toString();
			obj.naturalUnaReasons_2Val = i.str_05 == null ? "" : i.str_05.toString();
			obj.naturalUnaReasons_3Val = i.str_06 == null ? "" : i.str_06.toString();
			obj.naturalUnaReasons_4Val = i.str_07 == null ? "" : i.str_07.toString();
			obj.naturalUnaReasons_5Val = i.str_08 == null ? "" : i.str_08.toString();
			obj.naturalUnaReasons_6Val = i.str_09 == null ? "" : i.str_09.toString();
			obj.approveSid1 = i.approveSid1;
			obj.approveStatus1 = i.approveStatus1 != null ? i.approveStatus1.value : null;
			obj.approveComment1 = i.approveComment1;
			obj.approveSendMailFlg1 = i.approveSendMailFlg1;
			obj.approveSid2 = i.approveSid2;
			obj.approveStatus2 = i.approveStatus2 != null ? i.approveStatus2.value : null;
			obj.approveComment2 = i.approveComment2;
			obj.approveSendMailFlg2 = i.approveSendMailFlg2;
			return obj;
		}).collect(Collectors.toList());

		return result;
	}

	// 退職者情報の追加
	// path:UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報反映前データ.個人情報反映前データ_退職者情報.アルゴリズム.退職者情報の追加.退職者情報の追加
	public void addRetireInformation(List<RetirementInformation> listDomain) {

		// 退職者情報リストを個人情報反映前データリストへ変換する (Convert retired employee information
		// list to list data before reflecting personal information)
		if (!listDomain.isEmpty()) {
			List<DataBeforeReflectingPerInfo> listDataBeforeReflect = convertRetiredEmpIntoDataBefReflec(listDomain);
			this.dataBeforeReflectPerInfoService.addDataBeforeReflectingPerInfo(listDataBeforeReflect);
		}
	}

	// 退職者情報リストを個人情報反映前データリストへ変換する
	private List<DataBeforeReflectingPerInfo> convertRetiredEmpIntoDataBefReflec(List<RetirementInformation> listDomainRetirementInfor) {
		List<DataBeforeReflectingPerInfo> result = new ArrayList<>();
		for (int i = 0; i < listDomainRetirementInfor.size(); i++) {
			DataBeforeReflectingPerInfo dataBeforeReflect = DataBeforeReflectingPerInfo.builder().build();
			dataBeforeReflect.historyId = listDomainRetirementInfor.get(i).historyId;
			dataBeforeReflect.companyId = listDomainRetirementInfor.get(i).companyId;
			dataBeforeReflect.sId = listDomainRetirementInfor.get(i).sId;
			dataBeforeReflect.scd = listDomainRetirementInfor.get(i).scd;
			dataBeforeReflect.pId = listDomainRetirementInfor.get(i).pId;
			dataBeforeReflect.personName = listDomainRetirementInfor.get(i).personName;
			dataBeforeReflect.requestFlag = EnumAdaptor.valueOf(listDomainRetirementInfor.get(i).notificationCategory, RequestFlag.class);
			dataBeforeReflect.onHoldFlag = EnumAdaptor.valueOf(listDomainRetirementInfor.get(i).pendingFlag, OnHoldFlag.class);
			dataBeforeReflect.registerDate = GeneralDate.today();
			dataBeforeReflect.releaseDate = listDomainRetirementInfor.get(i).releaseDate;
			dataBeforeReflect.stattus = EnumAdaptor.valueOf(listDomainRetirementInfor.get(i).status, Status.class);
			dataBeforeReflect.date_01 = listDomainRetirementInfor.get(i).retirementDate;
			dataBeforeReflect.select_code_01 = listDomainRetirementInfor.get(i).retirementCategory.value + "";

			dataBeforeReflect.select_code_02 = listDomainRetirementInfor.get(i).retirementReasonCtgCode1;
			dataBeforeReflect.select_name_02 = listDomainRetirementInfor.get(i).retirementReasonCtgName1;

			dataBeforeReflect.select_code_03 = listDomainRetirementInfor.get(i).retirementReasonCtgCode2;
			dataBeforeReflect.select_name_03 = listDomainRetirementInfor.get(i).retirementReasonCtgName2;

			dataBeforeReflect.str_01 = listDomainRetirementInfor.get(i).retirementRemarks != "" ? new NoteRetiment(listDomainRetirementInfor.get(i).retirementRemarks) : null;
			dataBeforeReflect.str_02 = listDomainRetirementInfor.get(i).retirementReasonVal != "" ? new NoteRetiment(listDomainRetirementInfor.get(i).retirementReasonVal) : null;
			dataBeforeReflect.date_02 = listDomainRetirementInfor.get(i).dismissalNoticeDate != null
					? GeneralDateTime.legacyDateTime(listDomainRetirementInfor.get(i).dismissalNoticeDate.date()) : null;
			dataBeforeReflect.date_03 = listDomainRetirementInfor.get(i).dismissalNoticeDateAllow != null
					? GeneralDateTime.legacyDateTime(listDomainRetirementInfor.get(i).dismissalNoticeDateAllow.date()) : null;
			dataBeforeReflect.str_03 = listDomainRetirementInfor.get(i).reaAndProForDis != "" ? new NoteRetiment(listDomainRetirementInfor.get(i).reaAndProForDis) : null;
			dataBeforeReflect.int_01 = listDomainRetirementInfor.get(i).naturalUnaReasons_1;
			dataBeforeReflect.str_04 = listDomainRetirementInfor.get(i).naturalUnaReasons_1Val != "" ? new NoteRetiment(listDomainRetirementInfor.get(i).naturalUnaReasons_1Val)
					: null;
			dataBeforeReflect.int_02 = listDomainRetirementInfor.get(i).naturalUnaReasons_2;
			dataBeforeReflect.str_05 = listDomainRetirementInfor.get(i).naturalUnaReasons_2Val != "" ? new NoteRetiment(listDomainRetirementInfor.get(i).naturalUnaReasons_2Val)
					: null;
			dataBeforeReflect.int_03 = listDomainRetirementInfor.get(i).naturalUnaReasons_3;
			dataBeforeReflect.str_06 = listDomainRetirementInfor.get(i).naturalUnaReasons_3Val != "" ? new NoteRetiment(listDomainRetirementInfor.get(i).naturalUnaReasons_3Val)
					: null;
			dataBeforeReflect.int_04 = listDomainRetirementInfor.get(i).naturalUnaReasons_4;
			dataBeforeReflect.str_07 = listDomainRetirementInfor.get(i).naturalUnaReasons_4Val != "" ? new NoteRetiment(listDomainRetirementInfor.get(i).naturalUnaReasons_4Val)
					: null;
			dataBeforeReflect.int_05 = listDomainRetirementInfor.get(i).naturalUnaReasons_5;
			dataBeforeReflect.str_08 = listDomainRetirementInfor.get(i).naturalUnaReasons_5Val != "" ? new NoteRetiment(listDomainRetirementInfor.get(i).naturalUnaReasons_5Val)
					: null;
			dataBeforeReflect.int_06 = listDomainRetirementInfor.get(i).naturalUnaReasons_6;
			dataBeforeReflect.str_09 = listDomainRetirementInfor.get(i).naturalUnaReasons_6Val != "" ? new NoteRetiment(listDomainRetirementInfor.get(i).naturalUnaReasons_6Val)
					: null;

			dataBeforeReflect.contractCode = AppContexts.user().contractCode();
			dataBeforeReflect.companyCode = AppContexts.user().companyCode();
			dataBeforeReflect.workId = 1;
			dataBeforeReflect.workName = "退職者情報の登録";
			switch (listDomainRetirementInfor.get(i).retirementCategory.value) {
			case 1:
				dataBeforeReflect.select_name_01 = "退職";
				break;
			case 2:
				dataBeforeReflect.select_name_01 = "転籍";
				break;

			case 3:
				dataBeforeReflect.select_name_01 = "解雇";
				break;

			case 4:
				dataBeforeReflect.select_name_01 = "定年";
				break;
			}
			
			result.add(dataBeforeReflect);
		}
		
		return result;
	}

	// 退職者情報の変更
	public void updateRetireInformation(List<RetirementInformation> listRetirementInfor) {
		if (!listRetirementInfor.isEmpty()) {
			List<DataBeforeReflectingPerInfo> listDataBeforeReflect = convertRetiredEmpIntoDataBefReflec(listRetirementInfor);
			this.dataBeforeReflectPerInfoService.updateDataBeforeReflectingPerInfo(listDataBeforeReflect);
		}
	}

	
	public void removeRetireInformation(String histId) {
		this.dataBeforeReflectPerInfoService.removeDataBeforeReflectingPerInfo(histId);

	}
	
	// 退職登録済みチェック
	//[Input]
	//・会社ID// companyID
	//・社員ID// EmployeeID
	//・基準日/ BaseDate
	public int retirementRegisteredCheck(String cid, String sid, GeneralDate baseDate) {
		
		// 社員ID（List）と基準日から所属会社履歴項目を取得する(Lấy AffCompanyHistoryItem từ EmployeeID(List) và BaseDate)
		List<AffCompanyHistItemImport> listAffCompanyHistItemImport = this.empInfoAdaptor.getByIDAndBasedate(baseDate, Arrays.asList(sid));
		
		// List<所属会社履歴項目> を確認する
		boolean check = false;
		Integer confirmationResul =9;
		
		for (int i = 0; i < listAffCompanyHistItemImport.size(); i++) {
			AffCompanyHistItemImport affComHistItem = listAffCompanyHistItemImport.get(i);
			if ((affComHistItem.isDestinationData() == false) && (!affComHistItem.getEndDate().equals(GeneralDate.max()))) {
				check = true;
			}
		}
		if (check) {
			confirmationResul = 1;
		}
		
		if (confirmationResul == 1) {
			return confirmationResul;
		}
		
		if (confirmationResul == 9) {
			// ドメイン [個人情報反映前データ_退職者情報] を取得する
			boolean domainExit = dataBeforeReflectingRepo.checkExitByWorkIdCidSid(cid, sid);
			if (domainExit) {
				confirmationResul = 2;
			}else{
				confirmationResul = 9;
			}
		}
		
		return confirmationResul;
	}
	
}