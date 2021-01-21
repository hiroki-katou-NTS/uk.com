package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.toppagealarm;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.ReadStatusManagementEmployee;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.TopPageAlarmStamping;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author chungnt
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TOP_AL_MGR_STAMP")
public class KrcdtTopAlMgrStamp extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@EmbeddedId
	public KrcdtTopAlMgrStampPk pk;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column (name = "CID")
	public String cid;
	
	@Basic(optional = false)
	@Column (name = "ROGER_FLAG")
	public int roger_flag;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID_TGT", referencedColumnName = "SID_TGT", insertable = false, updatable = false),
		@JoinColumn(name = "FINISH_TIME", referencedColumnName = "FINISH_TIME", insertable = false, updatable = false) 
	})
	public KrcdtTopAlStamp krcdtTopAlStamp;
	
	public static KrcdtTopAlMgrStamp toEntity(TopPageAlarmStamping topPageAlarmStamping, ReadStatusManagementEmployee managementEmployee) {
		return new KrcdtTopAlMgrStamp(
					new KrcdtTopAlMgrStampPk(
							topPageAlarmStamping.getLstTopPageDetail().isEmpty() ? ""
							: topPageAlarmStamping.getLstTopPageDetail().get(0).getSid_tgt(),
							topPageAlarmStamping.getPageAlarm().getFinishDateTime(), 
							managementEmployee.getSid_mgr()), 
					topPageAlarmStamping.getPageAlarm().getCid(), 
					managementEmployee.getRogerFlag().value);
	}

	public KrcdtTopAlMgrStamp(KrcdtTopAlMgrStampPk pk, String cid, int roger_flag) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.roger_flag = roger_flag;
	}
	
}
