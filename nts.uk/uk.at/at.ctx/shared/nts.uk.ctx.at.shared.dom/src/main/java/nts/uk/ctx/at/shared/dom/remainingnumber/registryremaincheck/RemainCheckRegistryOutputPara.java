package nts.uk.ctx.at.shared.dom.remainingnumber.registryremaincheck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RemainCheckRegistryOutputPara {
	/**	代休不足区分: True: , False:  */
	private boolean daikyuAtr;
	/**	振休不足区分 */
	private boolean furikyuAtr;
	/**	年休不足区分 */
	private boolean nenkyuAtr;
	/**	積休不足区分 */
	private boolean sekikyuAtr;
	/**	特休不足区分 */
	private boolean tokkyuAtr;
	/**	公休不足区分 */
	private boolean koukyuAtr;
	/**	超休不足区分 */
	private boolean choukyuAtr;
}
