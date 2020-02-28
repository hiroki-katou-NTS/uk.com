package nts.uk.ctx.hr.notice.infra.entity.report;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHNMT_RPT_LAYOUT")
public class JhnmtRptLayout extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public JhnmtRptLayoutPk jhnmtRptLayoutPk;
	
	@Column(name = "WORK_ID")
	public Integer workId;

    @Column(name = "RPT_LAYOUT_CD")
	public String rptLayouCd;
	
    @Column(name = "RPT_LAYOUT_NAME")
	public String rptLayouName;
	
	@Column(name = "RPT_LAYOUT_NAME_Y")
	public String rptLayouNameYomi;
	
	@Column(name = "DSP_ORDER")
	public int displayOrder;
	
	@Column(name = "ABOLITION_ATR")
	public boolean abolition;
	
	@Column(name = "RPT_KIND")
	public Integer rptKind;
	
	@Column(name = "REMARK")
	public String remark;
	
	@Column(name = "MEMO")
	public String memo;
	
	@Column(name = "MESSAGE")
	public String message;
	
	@Column(name = "FORM_PRINT")
	public Boolean formPrint;
	
	@Column(name = "RPT_AGENT")
	public Boolean rptAgent;
	
	@Column(name = "NO_RANK_ORDER")
	public Boolean noRankOrder;
	@Override
	protected Object getKey() {
		return this.jhnmtRptLayoutPk;
	}
}
