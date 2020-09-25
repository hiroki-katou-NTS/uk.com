//package nts.uk.ctx.at.request.infra.entity.setting.workplace;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinTable;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import nts.arc.enums.EnumAdaptor;
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompany;
//import nts.uk.ctx.at.request.dom.setting.workplace.SelectionFlg;
//import nts.uk.shr.infra.data.entity.UkJpaEntity;
///**
// * 会社別申請承認設定
// * @author Doan Duy Hung
// *
// */
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "KRQST_COM_APP_CONFIG")
//public class KrqstComAppConfig extends UkJpaEntity implements Serializable{
//	
//	private static final long serialVersionUID = 1L;
//	/**
//	 * 会社ID
//	 */
//	@Id
//	@Column(name = "CID")
//	public String companyId;	
//	/**
//	 * 申請時の承認者の選択
//	 */
//	@Column(name = "SELECT_OF_APPROVERS_FLG")
//	public int selectOfApproversFlg;
//		
//	@OneToMany(targetEntity=KrqstComAppConfigDetail.class, cascade = CascadeType.ALL, mappedBy = "krqstComAppConfig", orphanRemoval = true)
//	@JoinTable(name = "KRQST_COM_APP_CF_DETAIL")
//	public List<KrqstComAppConfigDetail> krqstComAppConfigDetails;
//	
//	@Override
//	protected Object getKey() {
//		return companyId;
//	}
//	
//	public RequestOfEachCompany toOvertimeAppSetDomain(){
//		return new RequestOfEachCompany(
//				this.companyId, 
//				EnumAdaptor.valueOf(this.selectOfApproversFlg, SelectionFlg.class), 
//				this.krqstComAppConfigDetails.stream().map(x -> x.toOvertimeAppSetDomain()).collect(Collectors.toList()));
//	}
//	
//	public static KrqstComAppConfig toEntity(RequestOfEachCompany requestOfEachCompany){
//		return new KrqstComAppConfig(
//				requestOfEachCompany.getCompanyID(), 
//				requestOfEachCompany.getSelectionFlg().value, 
//				requestOfEachCompany.getListApprovalFunctionSetting()
//					.stream()
//					.map(x -> KrqstComAppConfigDetail.fromDomain(x, requestOfEachCompany.getCompanyID())).collect(Collectors.toList()));
//	}
//
//}
