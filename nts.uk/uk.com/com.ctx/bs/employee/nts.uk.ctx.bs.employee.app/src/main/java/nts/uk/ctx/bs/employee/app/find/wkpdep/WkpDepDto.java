package nts.uk.ctx.bs.employee.app.find.wkpdep;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author HungTT
 *
 */
@Value
public class WkpDepDto {

	private ConfigurationDto config;
	private List<InformationDto> items;

}
