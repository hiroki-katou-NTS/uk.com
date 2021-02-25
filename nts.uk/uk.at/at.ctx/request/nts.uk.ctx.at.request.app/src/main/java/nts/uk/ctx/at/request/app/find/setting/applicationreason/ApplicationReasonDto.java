//package nts.uk.ctx.at.request.app.find.setting.applicationreason;
//
//import lombok.AllArgsConstructor;
//import lombok.Value;
//import nts.arc.enums.EnumAdaptor;
//import nts.uk.ctx.at.request.dom.application.ApplicationType;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.DefaultFlg;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.ReasonTemp;
//
///**
// * 申請定型理由
// * 
// * @author ducpm
// *
// */
//@AllArgsConstructor
//@Value
//public class ApplicationReasonDto {
//	String companyId;
//	/**
//	 * 申請種類
//	 */
//	int appType;
//
//	/**
//	 * 理由ID
//	 */
//	String reasonID;
//	/**
//	 * 表示順
//	 */
//	int dispOrder;
//	/**
//	 * 定型理由
//	 */
//	String reasonTemp;
//	/**
//	 * 既定
//	 */
//	int defaultFlg;
//
//	/**
//	 * 
//	 * @param domain
//	 * @return
//	 */
//	public static ApplicationReasonDto convertToDto(ApplicationReason domain) {
//		return new ApplicationReasonDto(
//				domain.getCompanyId(), 
//				domain.getAppType().value, 
//				domain.getReasonID(),
//				domain.getDispOrder(), 
//				domain.getReasonTemp().v(), 
//				domain.getDefaultFlg().value);
//	}
//	
//	public ApplicationReason toDomain() {
//		return new ApplicationReason(
//				companyId, 
//				EnumAdaptor.valueOf(appType, ApplicationType.class), 
//				reasonID, 
//				dispOrder, 
//				new ReasonTemp(reasonTemp), 
//				EnumAdaptor.valueOf(defaultFlg, DefaultFlg.class));
//	}
//
//}
