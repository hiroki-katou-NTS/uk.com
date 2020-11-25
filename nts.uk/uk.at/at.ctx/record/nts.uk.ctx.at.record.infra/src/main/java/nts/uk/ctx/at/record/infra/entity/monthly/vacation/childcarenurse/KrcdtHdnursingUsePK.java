package nts.uk.ctx.at.record.infra.entity.monthly.vacation.childcarenurse;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：子の看護介護休暇使用数データ
 * @author yuri_tamakoshi
 */

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtHdnursingUsePK implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String employeeId;

	/**介護看護区分 */
	@Column(name = "NURSING_TYPE")
	public Integer nursingType;

}
