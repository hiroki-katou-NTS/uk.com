package nts.uk.ctx.at.record.infra.entity.monthly.roundingset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：月別実績の項目丸め設定
 * @author shuichu_ishida
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcmtCalcMRoundPK implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** 勤怠項目ID */
	@Column(name = "ATTENDANCE_ITEM_ID")
	public int attendanceItemId;
}
