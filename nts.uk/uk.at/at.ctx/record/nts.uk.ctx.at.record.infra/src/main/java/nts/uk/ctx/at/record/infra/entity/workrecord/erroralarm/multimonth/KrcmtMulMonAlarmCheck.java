package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.multimonth;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecordMultipleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.HowDisplayMessage;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth.MulMonthAlarmCheckCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtErAlAtdItemCon;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 複数月のアラームチェック条件
 * @author do_dt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KRCDT_MULTIMONTH_COND_ALARM")
public class KrcmtMulMonAlarmCheck extends UkJpaEntity implements Serializable {

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
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtMulMonAlarmCheck", orphanRemoval=true)
	public KrcmtErAlAtdItemCon krcmtErAlAtdItemCon;
	
	@Override
	protected Object getKey() {
		return pk;
	}

	public static KrcmtMulMonAlarmCheck toEntity(MulMonthAlarmCheckCond domain ) {
		KrcmtMulMonAlarmCheckPK pk = new KrcmtMulMonAlarmCheckPK(domain.getEralCheckId(), domain.getCondNo());
		KrcmtMulMonAlarmCheck entity = new KrcmtMulMonAlarmCheck(domain.getCid(), 
				pk,
				AppContexts.user().contractCode(),
				domain.getNameAlarmCon().v(),
				domain.getTypeCheckItem().value,
				domain.isUseAtr() ? 1 : 0,
				domain.getContinuousMonths().isPresent() ? domain.getContinuousMonths().get() : null,
				domain.getNumbers().isPresent() ? domain.getNumbers().get() : null,
				domain.getCompaOperator().isPresent() ? domain.getCompaOperator().get().value : null,
				domain.getDisplayMessage().isPresent() ? domain.getDisplayMessage().get().v() : null,
						KrcmtErAlAtdItemCon.toEntity(domain.getEralCheckId(),domain.getErAlAttendanceItemCondition(),true));
		return entity;
	}
	
	public MulMonthAlarmCheckCond toDomain() {
		MulMonthAlarmCheckCond domain = new MulMonthAlarmCheckCond(this.cid, 
				this.pk.eralCheckId,
				this.pk.condNo,
				new NameAlarmExtractionCondition(this.nameAlarmCon),
				EnumAdaptor.valueOf(this.typeCheckItem, TypeCheckWorkRecordMultipleMonth.class),
				this.useAtr == 1? true : false,
				this.messageDisplay==null ? Optional.empty(): Optional.ofNullable(new MessageDisplay(this.messageDisplay)),
				this.krcmtErAlAtdItemCon.toDomain(this.krcmtErAlAtdItemCon, this.cid, null),
				Optional.ofNullable(this.continuousMonths),
				Optional.ofNullable(this.numbers),
				compaOperator == null ? Optional.empty() :
					Optional.ofNullable(EnumAdaptor.valueOf(compaOperator, SingleValueCompareType.class)));
		
		return domain;
	}
}
