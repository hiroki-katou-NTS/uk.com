package nts.uk.ctx.at.record.app.command.kdp.kdp004.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;

/**
 * 
 * @author sonnlb
 * 
 *         打刻ボタン
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StampButtonCommand {
	/** ページNO */
	private Integer pageNo;

	/** ボタン位置NO */
	private Integer buttonPositionNo;

	public StampButton toDomainValue() {

		return new StampButton(new PageNo(this.pageNo), new ButtonPositionNo(this.buttonPositionNo));
	}

}
