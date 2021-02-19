package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecordMultipleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthAlarmCheckCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtEralstCndgrp;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KRCMT_ALST_CHKMLT_UD")
public class KrcmtAlstChkmltUd extends ContractUkJpaEntity implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String cid;
	
	@EmbeddedId
	public KrcmtMulMonAlarmCheckPK pk;
	
	@Column(name = "CONTRACT_CD")
	public String contractCd;
	/** アラームリスト抽出条件の名称	 */
	@Column(name = "NAME_ALARM_CON")
	@Basic(optional = false)
	public String nameAlarmCon;

	/**複数月チェック種類	 */
	@Column(name = "TYPE_CHECK_ITEM")
	@Basic(optional = false)
	public int typeCheckItem;
	/**使用区分	 */
	@Column(name = "USE_ATR")
	@Basic(optional = false)
	public int useAtr;
	/**	連続月数 */
	@Column(name = "CONTINUOUS_MONTHS")
	@Basic(optional = false)
	public Integer continuousMonths;
	/**	回数 */
	@Column(name = "NUMBERS")
	@Basic(optional = false)
	public Integer numbers;
	/**比較演算子	 */
	@Column(name = "COMPA_OPERATOR")
	@Basic(optional = false)
	public Integer compaOperator;
	/**	メッセージ */
	@Column(name = "COND_MESSAGE")
	@Basic(optional = false)
	public String messageDisplay;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="KrcmtAlstChkmltUd", orphanRemoval=true)
	public KrcmtEralstCndgrp krcmtEralstCndgrp;
	@Override
	protected Object getKey() {
		return pk;
	}

	public static KrcmtAlstChkmltUd toEntity(MulMonthAlarmCheckCond domain ) {
		KrcmtMulMonAlarmCheckPK pk = new KrcmtMulMonAlarmCheckPK(domain.getEralCheckId(), domain.getCondNo());
		KrcmtAlstChkmltUd entity = new KrcmtAlstChkmltUd(domain.getCid(), 
				pk,
				AppContexts.user().contractCode(),
				domain.getNameAlarmCon().v(),
				domain.getTypeCheckItem().value,
				domain.isUseAtr() ? 1 : 0,
				domain.getContinuousMonths().isPresent() ? domain.getContinuousMonths().get() : null,
				domain.getNumbers().isPresent() ? domain.getNumbers().get() : null,
				domain.getCompaOperator().isPresent() ? domain.getCompaOperator().get().value : null,
				domain.getDisplayMessage().isPresent() ? domain.getDisplayMessage().get().v() : null,
						KrcmtEralstCndgrp.toEntity(domain.getEralCheckId(),domain.getErAlAttendanceItemCondition(),true));
		return entity;
	}
	
	public MulMonthAlarmCheckCond toDomain() {
		ErAlAttendanceItemCondition<?> erAlAttendanceItemCondition = this.krcmtEralstCndgrp.toDomain(this.krcmtEralstCndgrp, this.cid, null);
		MulMonthAlarmCheckCond domain = new MulMonthAlarmCheckCond(this.cid,
				this.pk.eralCheckId,
				this.pk.condNo,
				new NameAlarmExtractionCondition(this.nameAlarmCon),
				EnumAdaptor.valueOf(this.typeCheckItem, TypeCheckWorkRecordMultipleMonth.class),
				this.useAtr == 1 ? true : false,
						this.messageDisplay==null ? Optional.empty(): Optional.ofNullable(new MessageDisplay(this.messageDisplay)),
				erAlAttendanceItemCondition,
				Optional.ofNullable(this.continuousMonths),
				Optional.ofNullable(this.numbers),
				this.compaOperator == null ? Optional.empty() :
					Optional.ofNullable(EnumAdaptor.valueOf(this.compaOperator, SingleValueCompareType.class)));
		return domain;
	}
}
