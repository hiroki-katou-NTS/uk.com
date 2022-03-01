package nts.uk.smile.infra.entity.smilelinked;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class LsmmtEmplinkMonthSetPK implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// column 契約コード
	@NotNull
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	// column 会社ID
	@NotNull
	@Column(name = "CID")
	private String cid;

	// column 支払コード
	@NotNull
	@Column(name = "PAYMENT_CD")
	private Integer paymentCd;

	// column 選択雇用コード
	@NotNull
	@Column(name = "EMP_CD")
	private String empCd;
}