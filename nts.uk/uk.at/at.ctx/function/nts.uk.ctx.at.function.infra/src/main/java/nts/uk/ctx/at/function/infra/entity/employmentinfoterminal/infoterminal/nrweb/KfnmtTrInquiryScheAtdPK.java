package nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal.nrweb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtTrInquiryScheAtdPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 契約コード
	 */
	@Column(name = "CONTRACT_CD")
	public String contractCode;

	/**
	 * 勤怠項目ID
	 */
	@Column(name = "ATTENDANCE_ITEM_ID")
	public int attendanceId;

}
