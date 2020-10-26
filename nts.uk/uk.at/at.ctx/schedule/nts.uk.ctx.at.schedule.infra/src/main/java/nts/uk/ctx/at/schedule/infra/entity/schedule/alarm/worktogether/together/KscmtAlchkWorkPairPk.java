package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.worktogether.together;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * KSCMT_ALCHK_WORK_PAIR の主キー
 * @author hiroko_miura
 *
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscmtAlchkWorkPairPk {

	/**
	 * 同時出勤指定.社員ID
	 */
	@Column(name = "SID")
	public String employeeId;

	/**
	 * 同時出勤指定.同時に出勤すべき社員の候補
	 */
	@Column(name = "PAIR_SID")
	public String pairEmployeeId;
}
