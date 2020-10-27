package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.HowDisplayMessage;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeMonCheckItem;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlConGroup;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCMT_EXTRA_RESULT_MON")
public class KrcmtExtraResultMonthly extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ERAL_CHECK_ID")
	public String errorAlarmCheckID;

	@Column(name = "SORT_BY")
	public int sortBy;

	@Column(name = "EXTRA_RESULT_MON_NAME")
	public String extraResultMonName;

	@Column(name = "USE_ATR")
	public int useAtr;

	@Column(name = "TYPE_CHECK_ITEM")
	public int typeCheckItem;

	@Column(name = "MESSAGE_BOLD")
	public int messageBold;

	@Column(name = "MESSAGE_COLOR")
	public String messageColor;
	
	@Column(name = "MESSAGE_DISPLAY")
	@Basic(optional = true)
	public String messageDisplay;
	
	// attditemcondition
	@Column(name = "OPERATOR_BETWEEN_GROUPS")
	public Integer operatorBetweenGroups;

	@Column(name = "GROUP2_USE_ATR")
	@Basic(optional = true)
	public Integer	 group2UseAtr;

	@Column(name = "ATD_ITEM_CONDITION_GROUP1")
	@Basic(optional = true)
	public String atdItemConditionGroup1;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "ATD_ITEM_CONDITION_GROUP1", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false)
	public KrcstErAlConGroup krcstErAlConGroup1;

	@Column(name = "ATD_ITEM_CONDITION_GROUP2")
	@Basic(optional = true)
	public String atdItemConditionGroup2;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
	@JoinColumn(name = "ATD_ITEM_CONDITION_GROUP2", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false)
	public KrcstErAlConGroup krcstErAlConGroup2;

	@Override
	protected Object getKey() {
		return errorAlarmCheckID;
	}
	
	public KrcmtExtraResultMonthly(
			String errorAlarmCheckID, int sortBy, String extraResultMonName, int useAtr, int typeCheckItem, int messageBold, String messageColor, String messageDisplay, 
			Integer operatorBetweenGroups,
			Integer group2UseAtr, String atdItemConditionGroup1, KrcstErAlConGroup krcstErAlConGroup1, String atdItemConditionGroup2, KrcstErAlConGroup krcstErAlConGroup2) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.sortBy = sortBy;
		this.extraResultMonName = extraResultMonName;
		this.useAtr = useAtr;
		this.typeCheckItem = typeCheckItem;
		this.messageBold = messageBold;
		this.messageColor = messageColor;
		this.messageDisplay = messageDisplay;
		this.operatorBetweenGroups = operatorBetweenGroups;
		this.group2UseAtr = group2UseAtr;
		this.atdItemConditionGroup1 = atdItemConditionGroup1;
		this.krcstErAlConGroup1 = krcstErAlConGroup1;
		this.atdItemConditionGroup2 = atdItemConditionGroup2;
		this.krcstErAlConGroup2 = krcstErAlConGroup2;
	}

	public static KrcmtExtraResultMonthly toEntity(ExtraResultMonthly domain ) {
		boolean isCheckConMonthly = domain.getCheckConMonthly().isPresent();
		return new KrcmtExtraResultMonthly(
				domain.getErrorAlarmCheckID(),
				domain.getSortBy(),
				domain.getNameAlarmExtraCon().v(),
				domain.isUseAtr()?1:0,
				domain.getTypeCheckItem().value,
				domain.getHowDisplayMessage().isMessageBold()?1:0,
				!domain.getHowDisplayMessage().getMessageColor().isPresent()?null:domain.getHowDisplayMessage().getMessageColor().get(),
				!domain.getDisplayMessage().isPresent()?null:domain.getDisplayMessage().get().v(),
				!isCheckConMonthly?null:domain.getCheckConMonthly().get().getOperatorBetweenGroups().value,
				!isCheckConMonthly?null:domain.getCheckConMonthly().get().isUseGroup2()?1:0,
				!isCheckConMonthly?null:domain.getCheckConMonthly().get().getGroup1().getAtdItemConGroupId(),
				!isCheckConMonthly?null:KrcstErAlConGroup.toEntity(domain.getCheckConMonthly().get().getGroup1(), true),
				!isCheckConMonthly?null:(domain.getCheckConMonthly().get().getGroup2()==null?null:domain.getCheckConMonthly().get().getGroup2().getAtdItemConGroupId()),
				!isCheckConMonthly?null:(domain.getCheckConMonthly().get().getGroup2() == null?null:KrcstErAlConGroup.toEntity(domain.getCheckConMonthly().get().getGroup2(), false))
				);
	}
	
	public ExtraResultMonthly toDomain() {
		AttendanceItemCondition attdItemCon = null;
		if(this.operatorBetweenGroups != null) {
		 attdItemCon = AttendanceItemCondition.init(
				//LogicalOperator operatorBetweenGroups, Boolean group2UseAtr
				EnumAdaptor.valueOf(this.operatorBetweenGroups, LogicalOperator.class),	
				this.group2UseAtr ==null?null:new Boolean(this.group2UseAtr.intValue()==1?true:false)
				);
			attdItemCon.setGroup1(krcstErAlConGroup1.toDomain(AppContexts.user().companyId(), this.errorAlarmCheckID));
			attdItemCon.setGroup2(krcstErAlConGroup2 ==null?null :krcstErAlConGroup2.toDomain(AppContexts.user().companyId(), this.errorAlarmCheckID));
		}
		
		return new ExtraResultMonthly(
			this.errorAlarmCheckID,
			this.sortBy,
			new NameAlarmExtractionCondition(this.extraResultMonName),
			this.useAtr==1?true:false,
			EnumAdaptor.valueOf(this.typeCheckItem,TypeMonCheckItem.class),
			new HowDisplayMessage((this.messageBold==1?true:false),
					this.messageColor==null?null:this.messageColor
					),
			this.messageDisplay==null?null:new MessageDisplay(this.messageDisplay),
			attdItemCon
				);
	}
	
	


}
