///**
// * 3:38:09 PM Jan 30, 2018
// */
//package nts.uk.ctx.at.request.app.command.setting.workplace;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import nts.arc.enums.EnumAdaptor;
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplace;
//import nts.uk.ctx.at.request.dom.setting.workplace.SelectionFlg;
//
///**
// * @author hungnm
// *
// */
//@Data
//@AllArgsConstructor
//public class ApprovalSettingWkpCommand {
//
//	// 会社ID
//	public String companyId;
//	// 職場ID
//	private String wkpId;
//	// 選択
//	private Integer selectionFlg;
//	// 申請承認機能設定
//	public List<ApprovalFunctionSettingCommand> approvalFunctionSettingDtoLst;
//
//	public RequestOfEachWorkplace toDomain() {
//		return new RequestOfEachWorkplace(companyId, wkpId, EnumAdaptor.valueOf(selectionFlg, SelectionFlg.class),
//				approvalFunctionSettingDtoLst.stream().map(x -> x.toDomainDetail()).collect(Collectors.toList()));
//	}
//}
