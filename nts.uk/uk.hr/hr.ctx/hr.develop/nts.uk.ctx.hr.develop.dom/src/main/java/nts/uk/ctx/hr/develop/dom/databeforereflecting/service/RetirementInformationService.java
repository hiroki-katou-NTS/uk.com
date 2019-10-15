package nts.uk.ctx.hr.develop.dom.databeforereflecting.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.RetirementInformation;

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
	private List<RetirementInformation> convertData(List<DataBeforeReflectingPerInfo> listDataBeforeReflectPerInfo){
		
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
			obj.notificationCategory =  i.requestFlag.value;
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
			obj.retirementCategory.value = Integer.valueOf(i.select_code_01);
			obj.retirementReasonCtg1 = Integer.valueOf(i.select_code_02);
			obj.retirementReasonCtg2 = Integer.valueOf(i.select_code_03);
			obj.retirementRemarks = i.str_01;
			obj.retirementReasonVal = i.str_02;
			obj.dismissalNoticeDate = GeneralDate.legacyDate(i.date_02.date());
			obj.dismissalNoticeDateAllow = GeneralDate.legacyDate(i.date_03.date());
			obj.reaAndProForDis = i.str_03;
			obj.naturalUnaReasons_1 = i.int_01;
			obj.naturalUnaReasons_2 = i.int_02;
			obj.naturalUnaReasons_3 = i.int_03;
			obj.naturalUnaReasons_4 = i.int_04;
			obj.naturalUnaReasons_5 = i.int_05;
			obj.naturalUnaReasons_6 = i.int_06;
			obj.naturalUnaReasons_1Val = i.str_04;
			obj.naturalUnaReasons_2Val = i.str_05;
			obj.naturalUnaReasons_3Val = i.str_06;
			obj.naturalUnaReasons_4Val = i.str_07;
			obj.naturalUnaReasons_5Val = i.str_08;
			obj.naturalUnaReasons_6Val = i.str_09;
			
			return obj;
		}).collect(Collectors.toList());
		
		return result;
	}
}
