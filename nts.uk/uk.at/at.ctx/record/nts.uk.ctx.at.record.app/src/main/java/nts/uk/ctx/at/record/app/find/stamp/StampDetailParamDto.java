package nts.uk.ctx.at.record.app.find.stamp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StampDetailParamDto {
	String startDate;
	String endDate;
	List<String> sIDs;

}
