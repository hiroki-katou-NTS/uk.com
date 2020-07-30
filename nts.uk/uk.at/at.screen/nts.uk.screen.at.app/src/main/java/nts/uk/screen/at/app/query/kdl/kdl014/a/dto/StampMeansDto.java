package nts.uk.screen.at.app.query.kdl.kdl014.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.layer.ws.json.serializer.EnumObject;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;

@Data
@AllArgsConstructor
public class StampMeansDto {

	@EnumObject
	private StampMeans stampMeans;
	
}
