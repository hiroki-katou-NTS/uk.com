package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* @author sakuratani
*
*			項目値Export
*         
*/
@Getter
@AllArgsConstructor
public class ItemValueExport {

	//時間
	private int time;

	//金額
	private long amount;

	//色
	private Optional<String> color;

}