package nts.uk.screen.at.app.query.kdp.kdp001.a;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationStampInputDto {

	private String workLocationName;

	private List<String> workpalceId = new ArrayList<>();

}
