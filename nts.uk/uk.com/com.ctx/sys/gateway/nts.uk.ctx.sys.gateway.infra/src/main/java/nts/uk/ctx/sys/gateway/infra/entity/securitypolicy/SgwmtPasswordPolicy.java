package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SGWMT_PASSWORD_POLICY")
public class SgwmtPasswordPolicy extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CONTRACT_CODE")
	public String contractCode;
	@Column(name = "NOTIFICATION_PASSWORD_CHANGE")
	public BigDecimal notificationPasswordChange;
	@Column(name = "LOGIN_CHECK")
	public BigDecimal loginCheck;
	@Column(name = "INITIAL_PASSWORD_CHANGE")
	public BigDecimal initialPasswordChange;
	@Column(name = "IS_USE")
	public BigDecimal isUse;
	@Column(name = "HISTORY_COUNT")
	public BigDecimal historyCount;
	@Column(name = "LOWEST_DIGITS")
	public BigDecimal lowestDigits;
	@Column(name = "VALIDITY_PERIOD")
	public BigDecimal validityPeriod;
	@Column(name = "NUMBER_OF_DIGITS")
	public BigDecimal numberOfDigits;
	@Column(name = "SYMBOL_CHARACTERS")
	public BigDecimal symbolCharacters;
	@Column(name = "ALPHABET_DIGIT")
	public BigDecimal alphabetDigit;

	@Override
	protected Object getKey() {
		return this.contractCode;
	}

}
