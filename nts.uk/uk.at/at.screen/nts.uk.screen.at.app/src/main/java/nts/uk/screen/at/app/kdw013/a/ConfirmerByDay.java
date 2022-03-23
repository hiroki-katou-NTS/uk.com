package nts.uk.screen.at.app.kdw013.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmerByDay {
	private GeneralDate date;
	private List<ConfirmerDto> confirmers;
}
