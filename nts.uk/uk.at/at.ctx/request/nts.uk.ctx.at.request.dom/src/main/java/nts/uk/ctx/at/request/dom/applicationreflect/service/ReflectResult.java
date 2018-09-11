package nts.uk.ctx.at.request.dom.applicationreflect.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 実行結果
 * @author do_dt
 *
 */
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ReflectResult {
	/**
	 * 勤務予定 : True: 反映済み、False：　未反映
	 */
	private boolean scheResult;
	/**
	 * 勤務実績 : True: 反映済み、False：　未反映
	 */
	private boolean recordResult;
}
