package nts.uk.ctx.at.shared.dom.reamainmana.absencerecruitment.interim;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.ManaDataAtr;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.reamainmana.interimremain.primitive.UseDay;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定振出振休紐付け管理
 * @author do_dt
 *
 */
@Getter
@AllArgsConstructor
public class InterimRecAbsMana {
	/**	振休管理データID */
	private String absenceManaId;
	/**	振休管理データ区分 */
	private ManaDataAtr absenceManaAtr;
	/**	振出管理データID */
	private String recruitmentManaId;
	/**	振出管理データ区分 */
	private ManaDataAtr recruitmentManaAtr;
	/**	使用日数 */
	private UseDay useDays;
	/**	対象選択区分 */
	private SelectedAtr selectedAtr;

}
