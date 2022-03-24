package nts.uk.ctx.at.request.app.find.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;

@AllArgsConstructor
@Getter
public class AppHdsubRecDto {
	/**
	 * 振出申請ID
	 */
	private String recAppID;

	/**
	 * 振休申請ID
	 */
	private String absenceLeaveAppID;
	/**
	 * 同期中
	 */
	private int syncing;
	
	public AppHdsubRec toDomain() {
		return new AppHdsubRec(recAppID, absenceLeaveAppID, EnumAdaptor.valueOf(syncing, SyncState.class));
	}
	
	public static AppHdsubRecDto fromDomain(AppHdsubRec appHdsubRec) {
		return new AppHdsubRecDto(appHdsubRec.getRecAppID(), appHdsubRec.getAbsenceLeaveAppID(), appHdsubRec.getSyncing().value);
	}
}
