package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 所定内・外の深夜時間帯
 * @author daiki_ichioka
 *
 */
@AllArgsConstructor
@Getter
public class InOutMidNightTimeSheet {

	/** 所定内 */
	List<MidNightTimeSheetForCalc> within = new ArrayList<>();
	
	/** 所定外 */
	List<MidNightTimeSheetForCalc> outside = new ArrayList<>();

}
