package nts.uk.ctx.hr.notice.infra.entity.report;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class JhnmtRptLayoutPk implements Serializable{
	private static final long serialVersionUID = 1L;

	//LAYOUT_ID
	@Basic(optional = false)
    @Column(name = "RPT_LAYOUT_ID")
	public int rptLayoutId;
	
	//CID
	@Basic(optional = false)
    @Column(name = "CID")
	public String cid;
	
}

