package nts.uk.ctx.hr.develop.infra.entity.retiredismissalregulation;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.DismissRestrictionTerm;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JSHMT_DISMISS_REST_TERM")
public class JshmtDismissRestTerm extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JshmtDismissRestTermPk pk;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String cId;
	
	@Basic(optional = false)
	@Column(name = "REST_FLG")
	public Integer restFlg;
	
	@ManyToOne
	@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false)
	public JshmtRetireDismissReg retireDismissReg;
	
	
	@JoinTable(name = "JSHMT_DISMISS_REST_ITEM")
	@OneToMany(mappedBy = "dismissRestTerm", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<JshmtDismissRestItem> listDismissRestItem;

	
	@Override
	public Object getKey() {
		return pk;
	}
	
	public DismissRestrictionTerm toDomain() {
		return DismissRestrictionTerm.createFromJavaType(
				this.pk.termCase, 
				this.restFlg == 0 || this.restFlg == null ? false : true,
				this.listDismissRestItem.stream().map(c -> c.toDomain()).collect(Collectors.toList()));}
	
	
	public JshmtDismissRestTerm(String histId, String cId, DismissRestrictionTerm domain) {
		super();
		this.pk.histId = histId;
		this.pk.termCase = domain.getRestrictionCase().value;
		this.cId = cId;
		this.restFlg = domain.getRestrictionFlg() == true ? 1 : 0;
		this.listDismissRestItem = domain.getRestrictionItems().stream()
				.map(c -> new JshmtDismissRestItem(histId, domain.getRestrictionCase().value, cId, c))
				.collect(Collectors.toList());
	}
	

}
