package nts.uk.screen.at.ws.knr.knr002.g;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.g.ConfirmTransmissionMaster;
import nts.uk.screen.at.app.query.knr.knr002.g.ConfirmTransmissionMasterDto;
import nts.uk.screen.at.app.query.knr.knr002.g.DeterminingTransferContents;
import nts.uk.screen.at.app.query.knr.knr002.g.DeterminingTransferContentsDto;

/**
 * 
 * @author xuannt
 *
 */
@Path("screen/at/checkremotesetting")
@Produces(MediaType.APPLICATION_JSON)
public class DeterminingTransferContentsScreenWS {
	//	送受信内容の決定
	@Inject
	private DeterminingTransferContents determiningTransferContents;
	@Inject
	private ConfirmTransmissionMaster confirmTransmissionMaster;


	@POST
	@Path("confirm/{empInfoTerCode}")
	public ConfirmTransmissionMasterDto confirm(@PathParam("empInfoTerCode") String empInforTerCode) {
		return this.confirmTransmissionMaster.getTimeRecordReqSetting(empInforTerCode);
	}

	@POST
	@Path("determine")
	public DeterminingTransferContentsDto determine(DeterminingTransferContentsDto dto) {
		return this.determiningTransferContents.determine(dto.getEmpInfoTerCode(),
														  dto.isSendEmployeeId(),
														  dto.isSendWorkType(),
														  dto.isSendWorkTime(),
														  dto.isOverTimeHoliday(),
														  dto.isApplicationReason(),
														  dto.isSendBentoMenu(),
														  dto.isTimeSetting(),
														  dto.isReboot(),
														  dto.isStampReceive(),
														  dto.isApplicationReceive(),
														  dto.isReservationReceive());
	}
}
