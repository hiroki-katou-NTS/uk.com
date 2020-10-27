// dan_pv REMOVED 2018/06/15 

//package nts.uk.ctx.pereg.infra.entity.roles.auth;
//
//import java.io.Serializable;
//
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoRoleAuth;
//import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "PPEMT_PERSON_ROLE_AUTH")
//@Entity
//public class PpemtPersonRoleAuth extends ContractUkJpaEntity implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//	@EmbeddedId
//	public PpemtPersonRoleAuthPk ppemtPersonRoleAuthPk;
//
//	@Basic(optional = false)
//	@Column(name = "CID")
//	public String companyId;
//
//	@Basic(optional = false)
//	@Column(name = "ALLOW_MAP_UPLOAD_ATR")
//	public int allowMapUpload;
//
//	@Basic(optional = false)
//	@Column(name = "ALLOW_MAP_BROWSE_ATR")
//	public int allowMapBrowse;
//
//	@Basic(optional = false)
//	@Column(name = "ALLOW_DOC_UPLOAD_ATR")
//	public int allowDocUpload;
//
//	@Basic(optional = false)
//	@Column(name = "ALLOW_DOC_REF_ATR")
//	public int allowDocRef;
//
//	@Basic(optional = false)
//	@Column(name = "ALLOW_AVT_UPLOAD_ATR")
//	public int allowAvatarUpload;
//
//	@Basic(optional = false)
//	@Column(name = "ALLOW_AVT_REF_ATR")
//	public int allowAvatarRef;
//
//	@Override
//	protected Object getKey() {
//		return this.ppemtPersonRoleAuthPk;
//	}
//
//}
