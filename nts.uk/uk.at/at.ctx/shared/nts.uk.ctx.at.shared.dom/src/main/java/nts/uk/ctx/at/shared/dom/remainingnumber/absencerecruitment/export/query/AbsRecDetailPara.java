package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;

/**
 * 振出振休明細
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AbsRecDetailPara {
	/**
	 * 社員ID
	 */
	private String sid;
	/**
	 * 状態: 管理データ状態区分
	 */
	private MngDataStatus dataAtr;
	/**
	 * 年月日: 発生消化年月日
	 */
	private CompensatoryDayoffDate ymdData;
	/**
	 * 発生消化区分
	 */
	private OccurrenceDigClass occurrentClass;
	/**
	 * 振休の未相殺
	 */
	private Optional<UnOffsetOfAbs> unOffsetOfAb;
	/**
	 * 振出の未使用
	 */
	private Optional<UnUseOfRec> unUseOfRec;
}
