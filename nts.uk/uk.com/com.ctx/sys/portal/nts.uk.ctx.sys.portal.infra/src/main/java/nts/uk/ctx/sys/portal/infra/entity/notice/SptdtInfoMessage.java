package nts.uk.ctx.sys.portal.infra.entity.notice;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.notice.MessageNotice;
import nts.uk.ctx.sys.portal.dom.notice.NotificationMessage;

/**
 * Entity お知らせメッセージ
 * @author DungDV
 *
 */
@Data
@Entity
@Table(name = "SPTDT_INFO_MESSAGE")
@EqualsAndHashCode(callSuper = true)
public class SptdtInfoMessage extends UkJpaEntity implements MessageNotice.MementoGetter, MessageNotice.MementoSetter, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 作成者ID + 入力日 */
	@EmbeddedId
	private SptdtInfoMessagePK pk;

	/** 排他バージョン */
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;
	
	/** 契約コード */
	@Column(name = "CONTRACT_CD")
	private String contractCd;
	
	/** 会社ID */
	@Column(name = "CID")
	private String companyId;
	
	/** 開始日 */
	@Column(name = "START_DATE")
	private GeneralDate startDate;
	
	/** 終了日 */
	@Column(name = "END_DATE")
	private GeneralDate endDate;
	
	/** 変更日 */
	@Column(name = "UPDATE_DATE")
	private GeneralDateTime updateDate;
	
	/** メッセージの内容 */
	@Column(name = "MESSAGE_CONTENT")
	private String message;
	
	/** 宛先区分  ０：全社員, １：職場選択 , ２：社員選択 */
	@Column(name = "DESTINATION_ATR")
	private Integer destination;
	
	@OneToMany(targetEntity = SptdtInfoMessageTgt.class
			, cascade = CascadeType.ALL
			, mappedBy = "sptdtInfoMessage"
			, orphanRemoval = true
			, fetch = FetchType.LAZY)
	@JoinTable(name = "SPTDT_INFO_MESSAGE_TGT")
	public List<SptdtInfoMessageTgt> sptdtInfoMessageTgts;
	
	@OneToMany(targetEntity = SptdtInfoMessageRead.class
			, cascade = CascadeType.ALL
			, mappedBy = "sptdtInfoMessage"
			, orphanRemoval = true
			, fetch = FetchType.LAZY)
	@JoinTable(name = "SPTDT_INFO_MESSAGE_READ")
	public List<SptdtInfoMessageRead> sptdtInfoMessageReads;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	@Override
	public void setCreatorID(String creatorID) {
		if (this.pk == null) {
			this.pk = new SptdtInfoMessagePK();
		}
		this.pk.setSid(creatorID);
	}

	@Override
	public void setInputDate(GeneralDateTime inputDate) {
		if (this.pk == null) {
			this.pk = new SptdtInfoMessagePK();
		}
		this.pk.setInputDate(inputDate);
	}

	@Override
	public void setModifiedDate(GeneralDateTime modifiedDate) {
		this.updateDate = modifiedDate;
	}

	@Override
	public void setDatePeriod(DatePeriod datePeriod) {
		this.startDate = datePeriod.start();
		this.endDate = datePeriod.end();
	}

	@Override
	public void setEmployeeIdSeen(List<String> employeeIdSeen) {
		if (employeeIdSeen == null) {
			return;
		}
		for (String readId : employeeIdSeen) {
			SptdtInfoMessageRead domainObj = new SptdtInfoMessageRead();
			SptdtInfoMessageReadPK pk = new SptdtInfoMessageReadPK();
			pk.setSid(this.pk.getSid());
			pk.setInputDate(this.pk.getInputDate());
			pk.setReadSid(readId);
			domainObj.setPk(pk);
			sptdtInfoMessageReads.add(domainObj);
		}
	}

	@Override
	public void setNotificationMessage(NotificationMessage notificationMessage) {
		this.message = notificationMessage.toString();
	}

	@Override
	public String getCreatorID() {
		return this.pk != null ? this.pk.getSid() : null;
	}

	@Override
	public GeneralDateTime getInputDate() {
		return this.pk != null ? this.pk.getInputDate() : null;
	}

	@Override
	public GeneralDateTime getModifiedDate() {
		return this.updateDate;
	}

	@Override
	public DatePeriod getDatePeriod() {
		return new DatePeriod(this.startDate, this.endDate);
	}

	@Override
	public List<String> getEmployeeIdSeen() {
		return this.sptdtInfoMessageReads.stream()
						.map(x -> x.getPk().getReadSid())
						.collect(Collectors.toList());
	}

	@Override
	public String getNotificationMessage() {
		return this.message;
	}
	
}
