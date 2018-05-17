//package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//
//import javax.persistence.Basic;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//
//import lombok.NoArgsConstructor;
//import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlConGroup;
//import nts.uk.shr.infra.data.entity.UkJpaEntity;
//
//@NoArgsConstructor
//@Entity
//@Table(name = "KRCMT_EXTRA_RESULT_MON")
//public class KrcmtExtraResultMonthly extends UkJpaEntity implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@Column(name = "ERAL_CHECK_ID")
//	public String errorAlarmCheckID;
//	
//	@Column(name = "SORT_BY")
//	public int sortBy;
//	
//	@Column(name = "EXTRA_RESULT_MON_NAME")
//	public String  extraResultMonName;
//	
//	@Column(name = "USE_ATR")
//	public int useAtr;
//	
//	@Column(name = "TYPE_CHECK_ITEM")
//	public int typeCheckItem;
//	
//	@Column(name = "MESSAGE_DISPLAY")
//	public String messageDisplay;
//
//	@Column(name = "MESSAGE_BOLD")
//	public int messageBold;
//	
//	@Column(name = "MESSAGE_COLOR")
//	public Integer messageColor;
//	//attditemcondition
//	@Column(name = "OPERATOR_BETWEEN_GROUPS")
//	public BigDecimal operatorBetweenGroups;
//	
//	@Column(name = "GROUP2_USE_ATR")
//	public BigDecimal group2UseAtr;
//	
//	@Column(name = "ATD_ITEM_CONDITION_GROUP1")
//	public String atdItemConditionGroup1;
//	
//	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
//	@JoinColumn(name = "ATD_ITEM_CONDITION_GROUP1", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false)
//	public KrcstErAlConGroup krcstErAlConGroup1;
//
//	@Column(name = "ATD_ITEM_CONDITION_GROUP2")
//	public String atdItemConditionGroup2;
//
//	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
//	@JoinColumn(name = "ATD_ITEM_CONDITION_GROUP2", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false)
//	public KrcstErAlConGroup krcstErAlConGroup2;
//	
//	@Override
//	protected Object getKey() {
//		return errorAlarmCheckID;
//	}
//
//	public KrcmtExtraResultMonthly(String errorAlarmCheckID, int sortBy, String extraResultMonName, int useAtr, int typeCheckItem, String messageDisplay, int messageBold, Integer messageColor, BigDecimal operatorBetweenGroups,
//			BigDecimal group2UseAtr, String atdItemConditionGroup1, KrcstErAlConGroup krcstErAlConGroup1, String atdItemConditionGroup2, KrcstErAlConGroup krcstErAlConGroup2) {
//		super();
//		this.errorAlarmCheckID = errorAlarmCheckID;
//		this.sortBy = sortBy;
//		this.extraResultMonName = extraResultMonName;
//		this.useAtr = useAtr;
//		this.typeCheckItem = typeCheckItem;
//		this.messageDisplay = messageDisplay;
//		this.messageBold = messageBold;
//		this.messageColor = messageColor;
//		this.operatorBetweenGroups = operatorBetweenGroups;
//		this.group2UseAtr = group2UseAtr;
//		this.atdItemConditionGroup1 = atdItemConditionGroup1;
//		this.krcstErAlConGroup1 = krcstErAlConGroup1;
//		this.atdItemConditionGroup2 = atdItemConditionGroup2;
//		this.krcstErAlConGroup2 = krcstErAlConGroup2;
//	}
//	
//
//}
