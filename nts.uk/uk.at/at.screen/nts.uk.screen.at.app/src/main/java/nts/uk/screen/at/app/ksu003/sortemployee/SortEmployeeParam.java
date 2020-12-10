package nts.uk.screen.at.app.ksu003.sortemployee;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author phongtq
 *
 */
@Value
public class SortEmployeeParam {

	private String ymd;
	
	private List<String> lstEmpId;
}
