package nts.uk.ctx.hr.develop.infra.entity.retiredismissalregulation;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.DissisalNoticeTerm;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JSHMT_DISMISS_NOTICE_TERM")
public class JshmtDismissNoticeTerm extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JshmtDismissNoticeTermPk jshmtDismissNoticeTermPk;
	
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;
	
	@Basic(optional = false)
	@Column(name = "NOTICE_FLG")
	public Integer noticeFlg;
	
	@Column(name = "NOTICE_TERM")
	public Integer noticeTerm;
	
	@Column(name = "NOTICE_SETTING_NUM")
	public Integer noticeSettingNum;
	
	@Column(name = "NOTICE_SETTING_DATE")
	public Integer noticeSettingDate;
	
	@ManyToOne
	@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false)
	public JshmtRetireDismissReg retireDismissReg;

	
	@Override
	public Object getKey() {
		return jshmtDismissNoticeTermPk;
	}
	
	public DissisalNoticeTerm toDomain() {
		return DissisalNoticeTerm.createFromJavaType(
				this.jshmtDismissNoticeTermPk.wageType, 
				this.noticeFlg == 0 || this.noticeFlg == null ? false : true,
			    Optional.of(DateCaculationTerm.createFromJavaType
			    		(this.noticeTerm, 
			    		 this.noticeSettingNum, 
			    		 this.noticeSettingDate)));
	}
	
	
	public JshmtDismissNoticeTerm(String cid, String histId, DateCaculationTerm dateCaculationTerm, DissisalNoticeTerm domain) {
		super();
		this.jshmtDismissNoticeTermPk.histId = histId;
		this.jshmtDismissNoticeTermPk.wageType = domain.getWageType().value;
		this.cid = cid;
		this.noticeFlg =  domain.getNoticeTermFlg() == true ? 1 : 0;
		this.noticeTerm = dateCaculationTerm.getCalculationTerm().value;
		this.noticeSettingNum  = dateCaculationTerm.getDateSettingNum();
		this.noticeSettingDate =  dateCaculationTerm.getDateSettingDate().isPresent() ? dateCaculationTerm.getDateSettingDate().get().value : null;
	}

}
