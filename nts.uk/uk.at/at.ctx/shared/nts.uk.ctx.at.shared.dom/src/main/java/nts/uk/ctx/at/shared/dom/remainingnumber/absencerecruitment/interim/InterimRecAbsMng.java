package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定振出振休紐付け管理
 * @author do_dt
 *
 */
@Getter
@AllArgsConstructor
public class InterimRecAbsMng extends AggregateRoot{
	/**	振休管理データID */
	private String absenceMngId;
	/**	振休管理データ区分 */
	private DataManagementAtr absenceMngAtr;
	/**	振出管理データID */
	private String recruitmentMngId;
	/**	振出管理データ区分 */
	private DataManagementAtr recruitmentMngAtr;
	/**	使用日数 */
	private UseDay useDays;
	/**	対象選択区分 */
	private SelectedAtr selectedAtr;

}
