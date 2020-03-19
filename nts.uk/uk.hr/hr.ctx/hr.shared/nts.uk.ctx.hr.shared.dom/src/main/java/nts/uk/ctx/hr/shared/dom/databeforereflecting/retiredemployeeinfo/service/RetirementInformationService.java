package nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.NoteRetiment;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.OnHoldFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.RequestFlag;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.Status;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.common.service.DataBeforeReflectingPerInfoService;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.RetirementCategory;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.RetirementInformation;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RetirementInformationService {

	@Inject
	DataBeforeReflectingPerInfoService dataBeforeReflectPerInfoService;

	// 退職者情報の取得
	public List<RetirementInformation> getRetirementInfo(String cid, Integer workId, List<String> listPid,
			Optional<Boolean> includReflected, Optional<String> sortByColumnName, Optional<String> orderType) {

		List<DataBeforeReflectingPerInfo> listDataBeforeReflectPerInfo = dataBeforeReflectPerInfoService
				.getDataBeforeReflectPerInfo(cid, workId, listPid, includReflected, sortByColumnName, orderType);
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

			return obj;
		}).collect(Collectors.toList());

		return result;
	}

	// 退職者情報の追加
	public void addRetireInformation(RetirementInformation domainObj) {

		// 退職者情報リストを個人情報反映前データリストへ変換する (Convert retired employee information
		// list to list data before reflecting personal information)
		DataBeforeReflectingPerInfo domain = new DataBeforeReflectingPerInfo();
		convertRetiredEmpIntoDataBefReflec(domainObj, domain);
		this.dataBeforeReflectPerInfoService.addDataBeforeReflectingPerInfo(domain);
	}

	// 退職者情報リストを個人情報反映前データリストへ変換する
	private void convertRetiredEmpIntoDataBefReflec(RetirementInformation domainObj,
			DataBeforeReflectingPerInfo domain) {
		domain.historyId = domainObj.historyId;
		domain.companyId = domainObj.companyId;
		domain.sId = domainObj.sId;
		domain.scd = domainObj.scd;
		domain.pId = domainObj.pId;
		domain.personName = domainObj.personName;
		domain.requestFlag = EnumAdaptor.valueOf(domainObj.notificationCategory, RequestFlag.class);
		domain.onHoldFlag = EnumAdaptor.valueOf(domainObj.pendingFlag, OnHoldFlag.class);
		domain.registerDate = GeneralDate.today();
		domain.releaseDate = domainObj.releaseDate;
		domain.stattus = EnumAdaptor.valueOf(domainObj.status, Status.class);
		domain.date_01 = domainObj.retirementDate;
		domain.select_code_01 = domainObj.retirementCategory.value + "";

		domain.select_code_02 = domainObj.retirementReasonCtgCode1;
		domain.select_name_02 = domainObj.retirementReasonCtgName1;

		domain.select_code_03 = domainObj.retirementReasonCtgCode2;
		domain.select_name_03 = domainObj.retirementReasonCtgName2;

		domain.str_01 = domainObj.retirementRemarks != "" ? new NoteRetiment(domainObj.retirementRemarks) : null;
		domain.str_02 = domainObj.retirementReasonVal != "" ? new NoteRetiment(domainObj.retirementReasonVal) : null;
		domain.date_02 = domainObj.dismissalNoticeDate != null
				? GeneralDateTime.legacyDateTime(domainObj.dismissalNoticeDate.date()) : null;
		domain.date_03 = domainObj.dismissalNoticeDateAllow != null
				? GeneralDateTime.legacyDateTime(domainObj.dismissalNoticeDateAllow.date()) : null;
		domain.str_03 = domainObj.reaAndProForDis != "" ? new NoteRetiment(domainObj.reaAndProForDis) : null;
		domain.int_01 = domainObj.naturalUnaReasons_1;
		domain.str_04 = domainObj.naturalUnaReasons_1Val != "" ? new NoteRetiment(domainObj.naturalUnaReasons_1Val)
				: null;
		domain.int_02 = domainObj.naturalUnaReasons_2;
		domain.str_05 = domainObj.naturalUnaReasons_2Val != "" ? new NoteRetiment(domainObj.naturalUnaReasons_2Val)
				: null;
		domain.int_03 = domainObj.naturalUnaReasons_3;
		domain.str_06 = domainObj.naturalUnaReasons_3Val != "" ? new NoteRetiment(domainObj.naturalUnaReasons_3Val)
				: null;
		domain.int_04 = domainObj.naturalUnaReasons_4;
		domain.str_07 = domainObj.naturalUnaReasons_4Val != "" ? new NoteRetiment(domainObj.naturalUnaReasons_4Val)
				: null;
		domain.int_05 = domainObj.naturalUnaReasons_5;
		domain.str_08 = domainObj.naturalUnaReasons_5Val != "" ? new NoteRetiment(domainObj.naturalUnaReasons_5Val)
				: null;
		domain.int_06 = domainObj.naturalUnaReasons_6;
		domain.str_09 = domainObj.naturalUnaReasons_6Val != "" ? new NoteRetiment(domainObj.naturalUnaReasons_6Val)
				: null;

		domain.contractCode = AppContexts.user().contractCode();
		domain.companyCode = AppContexts.user().companyCode();
		domain.workId = 1;
		domain.workName = "退職者情報の登録";
		switch (domainObj.retirementCategory.value) {
		case 1:
			domain.select_name_01 = "退職";
			break;
		case 2:
			domain.select_name_01 = "転籍";
			break;

		case 3:
			domain.select_name_01 = "解雇";
			break;

		case 4:
			domain.select_name_01 = "定年";
			break;
		}
	}

	public void updateRetireInformation(RetirementInformation domainObj) {
		DataBeforeReflectingPerInfo domain = new DataBeforeReflectingPerInfo();
		convertRetiredEmpIntoDataBefReflec(domainObj, domain);
		this.dataBeforeReflectPerInfoService.updateDataBeforeReflectingPerInfo(domain);

	}

	public void removeRetireInformation(String histId) {
		this.dataBeforeReflectPerInfoService.removeDataBeforeReflectingPerInfo(histId);

	}
}
