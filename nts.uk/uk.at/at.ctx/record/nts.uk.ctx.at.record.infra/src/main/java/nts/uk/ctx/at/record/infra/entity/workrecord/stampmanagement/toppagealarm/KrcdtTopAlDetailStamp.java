package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.toppagealarm;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.TopPageAlarmDetail;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.TopPageAlarmStamping;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author chungnt
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TOP_AL_DETAIL_STAMP")
public class KrcdtTopAlDetailStamp extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTopAlDetailStampPk pk;

	/**
	 * エラーメッセージ
	 */
	@Basic(optional = false)
	@Column(name = "ERROR_MESSAGE")
	public String error_message;

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
	
	public TopPageAlarmDetail toDomain() {
		return new TopPageAlarmDetail(this.error_message, this.pk.serial_no, this.pk.sid_tgt);
	}
	
	public static KrcdtTopAlDetailStamp toEntity(TopPageAlarmDetail domain, TopPageAlarmStamping topPageAlarmStamping) {
		return new KrcdtTopAlDetailStamp(
					new KrcdtTopAlDetailStampPk(
							domain.getSid_tgt(), 
							topPageAlarmStamping.getPageAlarm().getFinishDateTime(), 
							domain.getSerialNumber()), 
					domain.getErrorMessage());
	}

	public KrcdtTopAlDetailStamp(KrcdtTopAlDetailStampPk pk, String error_message) {
		super();
		this.pk = pk;
		this.error_message = error_message;
	}
	
	
}
