package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.toppagealarm;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.ExistenceError;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.IsCancelled;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.ReadStatusManagementEmployee;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.RogerFlag;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.TopPageAlarmMgrStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm.TopPageAlarmStamping;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TOP_AL_STAMP")
public class KrcdtTopAlStamp extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTopAlStampPk pk;
	
	//CID
	@Basic(optional = false)
	@Column(name = "CID")
	public String cId;
	
	/**
	 * エラーの有無
	 */
	@Basic(optional = false)
	@Column(name = "EXISTENCE_ERROR")
	public int existenceError;
	
	/**
	 * 中止フラグ
	 */
	@Basic(optional = false)
	@Column(name = "IS_CANCELLED")
	public int isCancelled;

	@OneToMany(mappedBy = "krcdtTopAlStamp" , cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	public List<KrcdtTopAlDetailStamp> krcdtTopAlDetailStamp;
	
	@OneToMany(mappedBy = "krcdtTopAlStamp",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	public List<KrcdtTopAlMgrStamp> krcdtTopAlMgrStamp;
	
	public TopPageAlarmStamping toDomain() {
		return new TopPageAlarmStamping(
				this.krcdtTopAlDetailStamp.stream().map(c -> c.toDomain()).collect(Collectors.toList()),
				this.toTopPageAlarm());
	}
	
	public static KrcdtTopAlStamp toEntity(TopPageAlarmStamping domain) {
		return new KrcdtTopAlStamp(
				new KrcdtTopAlStampPk(
						domain.getLstTopPageDetail().isEmpty()
								? ""
								: domain.getLstTopPageDetail().get(0).getSid_tgt(),
						domain.getPageAlarm().getFinishDateTime()),
						domain.getPageAlarm().getCid(),
						domain.getPageAlarm().getError().value, 
						domain.getPageAlarm().getIsCancelled().value,
						domain.getLstTopPageDetail().stream().map(c -> KrcdtTopAlDetailStamp.toEntity(c, domain)).collect(Collectors.toList()),
						domain.getPageAlarm().getLstManagementEmployee().stream().map(c -> KrcdtTopAlMgrStamp.toEntity(domain, c)).collect(Collectors.toList()));
	}
	
	private TopPageAlarmMgrStamp toTopPageAlarm() {
		return new TopPageAlarmMgrStamp(
				this.cId, 
				this.pk.finishDateTime, 
				EnumAdaptor.valueOf(this.existenceError, ExistenceError.class),
				EnumAdaptor.valueOf(this.isCancelled, IsCancelled.class),
				this.krcdtTopAlMgrStamp.stream().map(c->
						new ReadStatusManagementEmployee(
								EnumAdaptor.valueOf(c.roger_flag, RogerFlag.class), 
								c.pk.sid_mgr
					)).collect(Collectors.toList())
			);
	}
	
	@Override
	protected Object getKey() {
		return pk;
	}}
