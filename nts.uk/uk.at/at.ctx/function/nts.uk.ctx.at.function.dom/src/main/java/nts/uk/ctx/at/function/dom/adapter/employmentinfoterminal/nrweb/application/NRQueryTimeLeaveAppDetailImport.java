package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         時間年休申請詳細
 */
@AllArgsConstructor
@Getter
public class NRQueryTimeLeaveAppDetailImport {

	//時間休暇反映先
	private String reflectDest;
	
	//時間
	private String time;
}
