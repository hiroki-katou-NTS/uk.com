package nts.uk.ctx.pereg.infra.entity.roles.auth.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_ROLE_CTG_AUTH")
@Entity
public class PpemtPersonCategoryAuth extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtPersonCategoryAuthPk ppemtPersonCategoryAuthPk;

	@Basic(optional = false)
	@Column(name = "ALLOW_PER_REF_ATR")
	public int allowPersonRef;

	@Basic(optional = false)
	@Column(name = "ALLOW_OTHER_REF_ATR")
	public int allowOtherRef;	

	@Basic(optional = false)
	@Column(name = "ALLOW_OTHER_C_REF_ATR")
	public int allowOtherCompanyRef;

	@Column(name = "SELF_PAST_HIS_AUTH_TYPE")
	public Integer selfPastHisAuth;

	@Column(name = "SELF_FUTURE_HIS_AUTH_TYPE")
	public Integer selfFutureHisAuth;

	@Column(name = "SELF_ALLOW_DEL_HIS_ATR")
	public Integer selfAllowDelHis;

	@Column(name = "SELF_ALLOW_ADD_HIS_ATR")
	public Integer selfAllowAddHis;

	@Column(name = "OTHER_PAST_HIS_AUTH_TYPE")
	public Integer otherPastHisAuth;

	@Column(name = "OTHER_FUTURE_HIS_AUTH_TYPE")
	public Integer otherFutureHisAuth;

	@Column(name = "OTHER_ALLOW_DEL_HIS_ATR")
	public Integer otherAllowDelHis;

	@Column(name = "OTHER_ALLOW_ADD_HIS_ATR")
	public Integer otherAllowAddHis;

	@Column(name = "SELF_ALLOW_DEL_MULTI_ATR")
	public Integer selfAllowDelMulti;

	@Column(name = "SELF_ALLOW_ADD_MULTI_ATR")
	public Integer selfAllowAddMulti;

	@Column(name = "OTHER_ALLOW_DEL_MULTI_ATR")
	public Integer otherAllowDelMulti;

	@Column(name = "OTHER_ALLOW_ADD_MULTI_ATR")
	public Integer otherAllowAddMulti;

	@Override
	protected Object getKey() {
		return this.ppemtPersonCategoryAuthPk;
	}

	public PpemtPersonCategoryAuth updateFromDomain(PersonInfoCategoryAuth domain) {

		this.allowPersonRef = domain.getAllowPersonRef().value;
		this.allowOtherRef = domain.getAllowOtherRef().value;
		this.allowOtherCompanyRef = domain.getAllowOtherCompanyRef().value;
		this.selfPastHisAuth = domain.getSelfPastHisAuth() == null? null: domain.getSelfPastHisAuth().value;
		this.selfFutureHisAuth = domain.getSelfFutureHisAuth() == null? null: domain.getSelfFutureHisAuth().value;
		this.selfAllowAddHis = domain.getSelfAllowAddHis() == null? null: domain.getSelfAllowAddHis().value;
		this.selfAllowDelHis = domain.getSelfAllowDelHis() == null? null: domain.getSelfAllowDelHis().value;
		this.otherPastHisAuth = domain.getOtherPastHisAuth() == null? null: domain.getOtherPastHisAuth().value;
		this.otherFutureHisAuth = domain.getOtherFutureHisAuth() == null? null: domain.getOtherFutureHisAuth().value;
		this.otherAllowAddHis = domain.getOtherAllowAddHis() == null? null: domain.getOtherAllowAddHis().value;
		this.otherAllowDelHis = domain.getOtherAllowDelHis() == null? null: domain.getOtherAllowDelHis().value;
		this.selfAllowAddMulti = domain.getSelfAllowAddMulti() == null?  null: domain.getSelfAllowAddMulti().value;
		this.selfAllowDelMulti = domain.getSelfAllowDelMulti() == null? null: domain.getSelfAllowDelMulti().value;
		this.otherAllowAddMulti = domain.getOtherAllowAddMulti() == null? null: domain.getOtherAllowAddMulti().value;
		this.otherAllowDelMulti = domain.getOtherAllowDelMulti() == null? null: domain.getOtherAllowDelMulti().value;
		return this;
	}

}
