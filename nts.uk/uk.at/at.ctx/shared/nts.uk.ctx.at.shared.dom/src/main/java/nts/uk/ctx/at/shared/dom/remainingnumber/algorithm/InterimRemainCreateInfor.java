package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InterimRemainCreateInfor {
	/**	実績(List)*/
	private Optional<RecordRemainCreateInfor> recordData;
	/**	予定(List)  */
	private Optional<ScheRemainCreateInfor> scheData;
	/**	申請(List) */
	private List<AppRemainCreateInfor> appData;
}
