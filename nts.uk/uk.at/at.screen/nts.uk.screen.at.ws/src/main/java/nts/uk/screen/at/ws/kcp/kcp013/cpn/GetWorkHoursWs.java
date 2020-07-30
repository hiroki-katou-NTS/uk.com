package nts.uk.screen.at.ws.kcp.kcp013.cpn;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.query.kcp013.AcquireWorkingHoursDto;
import nts.uk.screen.at.app.query.kcp013.AcquireWorkingHoursRequest;

/**
 * 
 * @author thanhlv
 *
 */
@Path("at/record/stamp/workhours")
@Produces("application/json")
public class GetWorkHoursWs {

//	@Inject
//	private GetAllWorkingHours getAllWorkingHours;

	@POST
	@Path("workhours")
	public List<AcquireWorkingHoursDto> getWorkHours(AcquireWorkingHoursRequest request) {

//		getAllWorkingHours.getWorkingHours(request);

		AcquireWorkingHoursDto acquireWorkingHoursDto = new AcquireWorkingHoursDto("001", "機能", "8:00", "9:00", "20:00",
				"22:00", "計書", "システム名", 1);
		AcquireWorkingHoursDto acquireWorkingHoursDto1 = new AcquireWorkingHoursDto("002", "機能", "8:00", "9:00",
				"20:00", "22:00", "計書", "システム名", 0);
		List<AcquireWorkingHoursDto> dtos = new ArrayList<AcquireWorkingHoursDto>();
		dtos.add(acquireWorkingHoursDto);
		dtos.add(acquireWorkingHoursDto1);

		return dtos;
	}

	@POST
	@Path("getallworkhours")
	public List<AcquireWorkingHoursDto> getAllWorkHours() {
//		getAllWorkingHours.getAllWorkingHoursDtos();

		AcquireWorkingHoursDto acquireWorkingHoursDto = new AcquireWorkingHoursDto("001", "機能", "8:00", "9:00", "20:00",
				"22:00", "計書", "システム名", 1);
		AcquireWorkingHoursDto acquireWorkingHoursDto1 = new AcquireWorkingHoursDto("002", "機能", "8:00", "9:00",
				"20:00", "22:00", "計書", "システム名", 0);
		AcquireWorkingHoursDto acquireWorkingHoursDto2 = new AcquireWorkingHoursDto("003", "機能", "8:00", "9:00",
				"20:00", "22:00", "計書", "システム名", 0);
		AcquireWorkingHoursDto acquireWorkingHoursDto3 = new AcquireWorkingHoursDto("004", "機能", "8:00", "9:00",
				"20:00", "22:00", "計書", "システム名", 1);
		AcquireWorkingHoursDto acquireWorkingHoursDto4 = new AcquireWorkingHoursDto("005", "機能", "8:00", "9:00",
				"20:00", "22:00", "計書", "システム名", 1);
		List<AcquireWorkingHoursDto> dtos = new ArrayList<AcquireWorkingHoursDto>();
		dtos.add(acquireWorkingHoursDto);
		dtos.add(acquireWorkingHoursDto1);
		dtos.add(acquireWorkingHoursDto2);
		dtos.add(acquireWorkingHoursDto3);
		dtos.add(acquireWorkingHoursDto4);
		return dtos;
	}

}
