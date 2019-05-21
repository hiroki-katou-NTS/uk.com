package nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "JHCMT_CAREER_PATH_REQITEM")
public class JhcmtCareerPathReqitem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhcmtCareerPathReqitemPK PK_JHCMT_CAREER_PATH_REQITEM;

	@Column(name = "CID")
	public String companyID;

	@Column(name = "CAREER_ID")
	public String careerId;

	@Column(name = "DISP_NUM")
	public Integer dispNum;

	@Column(name = "MASTER_ITEM")
	public String masterItem;

	@Override
	public Object getKey() {
		return PK_JHCMT_CAREER_PATH_REQITEM;
	}

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
			@JoinColumn(name = "DISP_NUM", referencedColumnName = "DISP_NUM", insertable = false, updatable = false) })
	public JhcmtCareerPathReq masterItemList;

	public JhcmtCareerPathReqitem(String companyID, String careerId, Integer dispNum, String masterItem) {
		super();
		this.PK_JHCMT_CAREER_PATH_REQITEM = new JhcmtCareerPathReqitemPK(IdentifierUtil.randomUniqueId());
		this.companyID = companyID;
		this.careerId = careerId;
		this.dispNum = dispNum;
		this.masterItem = masterItem;
	}

}
