///**
// * 2:40:22 PM Jan 30, 2018
// */
//package nts.uk.ctx.at.request.app.find.setting.workplace;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplace;
//
///**
// * @author hungnm
// *
// */
//@AllArgsConstructor
//@Data
//public class ApplicationApprovalSettingWkpDto {
//
//	// 会社ID
//	private String companyId;
//	// 職場ID
//	private String wkpId;
//	// 選択
//	private Integer selectionFlg;
//	// 申請承認機能設定
//	private List<ApprovalFunctionSettingDto> approvalFunctionSettingDtoLst;
//
//	public static ApplicationApprovalSettingWkpDto fromDomain(RequestOfEachWorkplace domain) {
//		return new ApplicationApprovalSettingWkpDto(domain.getCompanyID(), domain.getWorkPlaceID(),
//				domain.getSelectionFlg().value, domain.getListApprovalFunctionSetting().stream().map((setting) -> {
//					return ApprovalFunctionSettingDto.convertToDto(setting);
//				}).collect(Collectors.toList()));
//	}
//}
