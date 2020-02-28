package nts.uk.ctx.hr.develop.infra.entity.careermgmt.careertype;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHCMT_CAREER_TYPE_MPITEM")
public class JhcmtCareerTypeMpItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String cId;

	@Column(name = "CAREER_TYPE_ID")
	public String careerTypeId;
	
	@Column(name = "HIST_ID")
	public String histId;
	
	@Column(name = "DISP_NUM")
	public Integer dispNum;

	@Id
	@Column(name = "MATER_ITEM_ID")
	public String materItemId;
	
	@Column(name = "MASTER_ITEM")
	public String masterItem;

	@Override
	public Object getKey() {
		return materItemId;
	}
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "CAREER_TYPE_ID", referencedColumnName = "CAREER_TYPE_ID", insertable = false, updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
			@JoinColumn(name = "DISP_NUM", referencedColumnName = "DISP_NUM", insertable = false, updatable = false) })
	public JhcmtCareerTypeMp itemList;

}
