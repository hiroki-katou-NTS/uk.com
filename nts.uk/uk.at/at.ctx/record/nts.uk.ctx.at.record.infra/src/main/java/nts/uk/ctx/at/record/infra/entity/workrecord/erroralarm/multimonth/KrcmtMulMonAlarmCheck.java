package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecordMultipleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.HowDisplayMessage;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthAlarmCheckCond;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCMT_MUL_MON_ALARM_CHECK")
public class KrcmtMulMonAlarmCheck extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "ERAL_CHECK_ID")
	public String errorAlarmCheckID;

	@Column(name = "NAME_ALARM_CON")
	@Basic(optional = false)
	public String nameAlarmCon;

	@Column(name = "TYPE_CHECK_ITEM")
	@Basic(optional = false)
	public int typeCheckItem;

	@Column(name = "MESSAGE_BOLD")
	@Basic(optional = false)
	public int messageBold;

	@Column(name = "MESSAGE_COLOR")
	@Basic(optional = true)
	public String messageColor;
	
	@Column(name = "MESSAGE_DISPLAY")
	@Basic(optional = true)
	public String messageDisplay;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtMulMonAlarmCheck", orphanRemoval=true)
	public KrcmtMulMonCond krcmtMulMonCond;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtMulMonAlarmCheck", orphanRemoval=true)
	public KrcmtMulMonCondAvg krcmtMulMonCondAvg;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtMulMonAlarmCheck", orphanRemoval=true)
	public KrcmtMulMonCondCont krcmtMulMonCondCont;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtMulMonAlarmCheck", orphanRemoval=true)
	public KrcmtMulMonCondCosp krcmtMulMonCondCosp;
	
	@Override
	protected Object getKey() {
		return errorAlarmCheckID;
	}
	
	public KrcmtMulMonAlarmCheck(String errorAlarmCheckID, String nameAlarmCon, int typeCheckItem, int messageBold, String messageColor, String messageDisplay) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.nameAlarmCon = nameAlarmCon;
		this.typeCheckItem = typeCheckItem;
		this.messageBold = messageBold;
		this.messageColor = messageColor;
		this.messageDisplay = messageDisplay;
	}

	public static KrcmtMulMonAlarmCheck toEntity(MulMonthAlarmCheckCond domain ) {
		return new KrcmtMulMonAlarmCheck(
				domain.getErrorAlarmCheckID(),
				domain.getNameAlarmCon().v(),
				domain.getTypeCheckItem().value,
				domain.getHowDisplayMessage().isMessageBold() ? 1 : 0,
				domain.getHowDisplayMessage().getMessageColor().isPresent() ? domain.getHowDisplayMessage().getMessageColor().get() : null,
				domain.getDisplayMessage().isPresent() ? domain.getDisplayMessage().get().v() : null);
	}
	
	public MulMonthAlarmCheckCond toDomain() {
		return new MulMonthAlarmCheckCond(
			this.errorAlarmCheckID,
			new NameAlarmExtractionCondition(this.nameAlarmCon),
			EnumAdaptor.valueOf(this.typeCheckItem, TypeCheckWorkRecordMultipleMonth.class),
			new HowDisplayMessage((this.messageBold==1 ? true : false),
					this.messageColor==null?null:this.messageColor
					),
			this.messageDisplay==null?null:new MessageDisplay(this.messageDisplay),
					this.krcmtMulMonCond == null ? null: this.krcmtMulMonCond.toDomain(),
					this.krcmtMulMonCondAvg == null ? null: this.krcmtMulMonCondAvg.toDomain(),
					this.krcmtMulMonCondCont == null ? null: this.krcmtMulMonCondCont.toDomain(),
					this.krcmtMulMonCondCosp == null ? null: this.krcmtMulMonCondCosp.toDomain());
	}
}
