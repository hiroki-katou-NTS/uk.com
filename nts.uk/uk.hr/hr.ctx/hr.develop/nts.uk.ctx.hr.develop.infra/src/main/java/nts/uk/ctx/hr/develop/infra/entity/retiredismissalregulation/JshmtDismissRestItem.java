package nts.uk.ctx.hr.develop.infra.entity.retiredismissalregulation;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.RestrictionItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JSHMT_DISMISS_REST_ITEM")
public class JshmtDismissRestItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JshmtDismissRestItemPk pk;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String cId;
	
	@Basic(optional = false)
	@Column(name = "TERM_FLG")
	public Integer termFlg;
	
	@Column(name = "SETTING_NUM")
	public Integer settingNum;
	
	@Override
	public Object getKey() {
		return pk;
	}
	
	@ManyToOne
	@JoinColumns( {
		@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
		@JoinColumn(name = "TERM_CASE", referencedColumnName = "TERM_CASE", insertable = false, updatable = false)
    })
	public JshmtDismissRestTerm dismissRestTerm;

	
	public RestrictionItem toDomain() {
		return RestrictionItem.createFromJavaType(
				this.pk.causesId.toString(), 
				this.termFlg == 0 || this.termFlg == null ? false : true, 
				this.settingNum == null ? Optional.empty() : Optional.of(this.settingNum));}

	public JshmtDismissRestItem(String histId, Integer termCase, String cId,  RestrictionItem domain) {
		super();
		this.pk.causesId = BigInteger.valueOf(Long.valueOf(domain.getCausesId()));
		this.pk.histId   = histId;
		this.pk.termCase = termCase;
		this.cId  = cId;
		this.termFlg = domain.getRestrictionTermFlg() == true ? 1 : 0;
		this.settingNum = domain.getSettingNum().isPresent() ? domain.getSettingNum().get() : null;
	}
	
	
}
