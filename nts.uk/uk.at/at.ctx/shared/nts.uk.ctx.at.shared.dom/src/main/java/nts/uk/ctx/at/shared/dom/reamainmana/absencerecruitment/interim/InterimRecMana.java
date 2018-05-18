package nts.uk.ctx.at.shared.dom.reamainmana.absencerecruitment.interim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.UnUsedDay;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定振出管理データ
 * @author do_dt
 *
 */
@Getter
@AllArgsConstructor
public class InterimRecMana {
	/**	暫定振出管理データID */
	private String recruitmentManaId;
	/**	使用期限日 */
	private GeneralDate expirationDays;
	/**	発生日数 */
	private OccurrenceDay occurrenceDays;
	/**	法定内外区分 */
	private StatutoryAtr statutoryAtr;
	/**	未使用日数 */
	private UnUsedDay unUsedDays;

}
