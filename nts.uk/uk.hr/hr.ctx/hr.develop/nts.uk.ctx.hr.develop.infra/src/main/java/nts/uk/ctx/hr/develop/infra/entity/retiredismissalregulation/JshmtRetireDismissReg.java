package nts.uk.ctx.hr.develop.infra.entity.retiredismissalregulation;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.RetireDismissalRegulation;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JSHMT_RETIRE_DISMISS_REG")
public class JshmtRetireDismissReg extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "HIST_ID")
	public String histId;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String cId;
	
	@Basic(optional = false)
	@Column(name = "PUBLIC_TERM")
	public Integer publicTerm;
	
	@Column(name = "PUBLIC_SETTING_NUM")
	public Integer publicSettingNum;
	
	@Column(name = "PUBLIC_SETTING_DATE")
	public Integer publicSettingDate;
	
	@Basic(optional = false)
	@Column(name = "NOTICE_ALERM_FLG")
	public Integer noticeAlermFlg;
	
	@Basic(optional = false)
	@Column(name = "REST_ALERM_FLG")
	public Integer restAlermFlg;
    
	@JoinTable(name = "JSHMT_DISMISS_REST_TERM")
	@OneToMany(mappedBy = "retireDismissReg", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	public List<JshmtDismissRestTerm> listDismissRestTerm;
	
	@JoinTable(name = "JSHMT_DISMISS_NOTICE_TERM")
	@OneToMany(mappedBy = "retireDismissReg", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	public List<JshmtDismissNoticeTerm> listDismissNoticeTerm;
	
	@Override
	public Object getKey() {
		return histId;
	}
	
	public RetireDismissalRegulation toDomain() {
		return RetireDismissalRegulation.createFromJavaType(
				this.cId, 
				this.histId, 
				DateCaculationTerm.createFromJavaType
	    		(this.publicTerm, 
	    		 this.publicSettingNum, 
	    		 this.publicSettingDate), 
				this.noticeAlermFlg == 0 || this.noticeAlermFlg == null ? false : true, 
				this.restAlermFlg   == 0 || this.restAlermFlg   == null ? false : true, 
				this.listDismissNoticeTerm.stream().map(c -> c.toDomain()).collect(Collectors.toList()), 
				this.listDismissRestTerm.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
		}
	
	
	public JshmtRetireDismissReg(RetireDismissalRegulation domain) {
		super();
		this.histId = domain.getHistoryId();
		this.cId = domain.getCompanyId();
		this.publicTerm = domain.getPublicTerm() == null ? null : domain.getPublicTerm().getCalculationTerm().value;
		this.publicSettingNum = domain.getPublicTerm() == null ? null : domain.getPublicTerm().getDateSettingNum();
		this.publicSettingDate = domain.getPublicTerm() == null ? null : domain.getPublicTerm().getDateSettingDate().isPresent() ? domain.getPublicTerm().getDateSettingDate().get().value : null;
		this.noticeAlermFlg = domain.getDismissalNoticeAlerm() == true ? 1 : 0;
		this.restAlermFlg = domain.getDismissalRestrictionAlerm() == true ? 1 : 0;
		
		this.listDismissRestTerm = domain.getDismissalRestrictionTermList().stream()
				.map(c -> new JshmtDismissRestTerm(this.histId, this.cId, c))
				.collect(Collectors.toList());
		
		this.listDismissNoticeTerm = domain.getDismissalNoticeTermList().stream()
				.map(c -> new JshmtDismissNoticeTerm(this.cId, histId, DateCaculationTerm.createFromJavaType
			    			(this.publicTerm, 
			   	    		 this.publicSettingNum, 
			   	    		 this.publicSettingDate), c))
				.collect(Collectors.toList());
	}

}
