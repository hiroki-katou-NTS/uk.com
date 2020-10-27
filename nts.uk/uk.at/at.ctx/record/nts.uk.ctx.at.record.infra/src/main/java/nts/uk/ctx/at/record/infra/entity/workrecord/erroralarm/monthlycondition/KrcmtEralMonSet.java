/**
 * 11:08:41 AM Mar 29, 2018
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectExtractCondition;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author hungnm 月別修正の抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ERAL_MON_SET")
public class KrcmtEralMonSet extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtEralMonSetPK krcmtEralMonSetPK;

	@Column(name = "ERROR_ALARM_NAME")
	public String errorAlarmName;

	@Column(name = "USE_ATR")
	public BigDecimal useAtr;

	@Basic(optional = true)
	@Column(name = "ERAL_CHECK_ID")
	public String eralCheckId;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
	@JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false)
	public KrcmtMonCorrectCndAtd krcmtMonCorrectCndAtd;

	public KrcmtEralMonSet(KrcmtEralMonSetPK krcmtEralMonSetPK, String errorAlarmName,
			BigDecimal useAtr, String eralCheckId) {
		super();
		this.krcmtEralMonSetPK = krcmtEralMonSetPK;
		this.errorAlarmName = errorAlarmName;
		this.useAtr = useAtr;
		this.eralCheckId = eralCheckId;
	}

	@Override
	protected Object getKey() {
		return this.krcmtEralMonSetPK;
	}

	public static MonthlyCorrectExtractCondition toDomain(KrcmtEralMonSet entity) {
		return MonthlyCorrectExtractCondition.createFromJavaType(entity.krcmtEralMonSetPK.companyId,
				entity.krcmtEralMonSetPK.errorAlarmCode, entity.errorAlarmName, entity.useAtr.intValue() == 1,
				entity.eralCheckId);
	}

	public static KrcmtEralMonSet fromDomain(MonthlyCorrectExtractCondition domain) {
		return new KrcmtEralMonSet(new KrcmtEralMonSetPK(AppContexts.user().companyId(), domain.getCode().v()),
				domain.getName().v(), domain.getUseAtr() ? new BigDecimal(1) : new BigDecimal(0),
				domain.getErrorAlarmCheckID());
	}

}
