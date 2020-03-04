package nts.uk.ctx.hr.shared.app.databeforereflecting.find;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.shared.dom.databeforereflecting.RetirementInformation;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.service.RetirementInformationService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class DatabeforereflectingFinder {

	@Inject
	private RetirementInformationService retirementInfoService;
	
	public List<DataBeforeReflectDto> getDataBeforeReflect() {

		// アルゴリズム[退職者登録一覧の取得]を実行する
		// (Thực hiện thuật toán "Get list đăng ký người nghỉ hưu")

		// アルゴリズム[退職者情報の取得]を実行する(Thực hiện thuật toán[Get thông tin người nghỉ
		// hưu/Retired information] )

		String cid = AppContexts.user().companyId();
		Integer workId = 1;
		List<String> listPid = new ArrayList<>();
		Optional<Boolean> includReflected = Optional.empty();
		Optional<String> sortByColumnName = Optional.of("date_01");
		Optional<String> orderType = Optional.of("asc");

		List<RetirementInformation> listRetirementInfo = retirementInfoService.getRetirementInfo(cid, workId, listPid,
				includReflected, sortByColumnName, orderType);

		if (listRetirementInfo.size() > 0) {
			return convertToDto(listRetirementInfo);
		}

		return new ArrayList<>();
	}

	private List<DataBeforeReflectDto> convertToDto(List<RetirementInformation> listRetirementInfo) {

		List<DataBeforeReflectDto> result = listRetirementInfo.stream().map(i -> {
			DataBeforeReflectDto obj = new DataBeforeReflectDto();
			obj.historyId = i.historyId;
			obj.contractCode = i.contractCode;
			obj.companyId = i.companyId;
			obj.companyCode = i.companyCode;
			obj.workId = i.workId;
			obj.workName = i.workName;
			obj.inputDate = i.inputDate;
			obj.pendingFlag = i.pendingFlag;
			obj.histId_Refer = i.histId_Refer;
			obj.releaseDate = i.releaseDate;
			obj.pId = i.pId;
			obj.sId = i.sId;
			obj.scd = i.scd;
			obj.employeeName = i.personName;
			obj.retirementDate = i.retirementDate;
			obj.retirementCategory = i.retirementCategory.value;
			obj.retirementReasonCtg1 = Integer.valueOf(i.retirementReasonCtgCode1);
			obj.retirementReasonCtg2 = Integer.valueOf(i.retirementReasonCtgCode2);
			obj.retirementRemarks = i.retirementRemarks;
			obj.retirementReasonVal = i.retirementReasonVal;
			obj.dismissalNoticeDate = i.dismissalNoticeDate;
			obj.dismissalNoticeDateAllow = i.dismissalNoticeDateAllow;
			obj.reaAndProForDis = i.reaAndProForDis;
			obj.naturalUnaReasons_1 = i.naturalUnaReasons_1;
			obj.naturalUnaReasons_2 = i.naturalUnaReasons_2;
			obj.naturalUnaReasons_3 = i.naturalUnaReasons_3;
			obj.naturalUnaReasons_4 = i.naturalUnaReasons_4;
			obj.naturalUnaReasons_5 = i.naturalUnaReasons_5;
			obj.naturalUnaReasons_6 = i.naturalUnaReasons_6;
			obj.naturalUnaReasons_1Val = i.naturalUnaReasons_1Val;
			obj.naturalUnaReasons_2Val = i.naturalUnaReasons_2Val;
			obj.naturalUnaReasons_3Val = i.naturalUnaReasons_3Val;
			obj.naturalUnaReasons_4Val = i.naturalUnaReasons_4Val;
			obj.naturalUnaReasons_5Val = i.naturalUnaReasons_5Val;
			obj.naturalUnaReasons_6Val = i.naturalUnaReasons_6Val;
			if (i.status == 0) {
				obj.status = "";
			} else if (i.status == 1) {
				obj.status = TextResource.localize("JCM007_A3_2");
			} else if (i.status == 2) {
				obj.status = TextResource.localize("JCM007_A3_3");
			}
			
			if (i.notificationCategory == 0) {
				obj.notificationCategory = "";
			} else if (i.notificationCategory == 1) {
				obj.notificationCategory = TextResource.localize("JCM007_A3_1");
			}
			
			return obj;
		}).collect(Collectors.toList());

		return result;
	}

}
