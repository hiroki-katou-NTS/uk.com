package nts.uk.ctx.sys.log.app.finder.datacorrectionlog;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.log.dom.datacorrectionlog.DataCorrectionLogRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class DataCorrectionLogFinder {

	@Inject
	private DataCorrectionLogRepository repo;

	public List<DataCorrectionLogDto> getDataLog(DataCorrectionLogParams params) {
		List<DataCorrectionLogDto> result = new ArrayList<>();
		if (params.getDisplayFormat() == 0) {//by date
			for (int i = 0; i < 500; i++) {
				for (int j = 0; j < params.getListEmployeeId().size(); j++) {
					for (int k = 0; k < 4; k++) {
						DataCorrectionLogDto log = new DataCorrectionLogDto(GeneralDate.ymd(2018, 5, (i%28)+1), "User" + j, "Item" + k,
								"0900", "1508", k%4, "Admin", GeneralDateTime.now(), (i%28)+1);
						result.add(log);
					}
				}
			}
		} else { //by individual
			for (int i = 0; i < 500; i++) {
				for (int j = 0; j < params.getListEmployeeId().size(); j++) {
					for (int k = 0; k < 4; k++) {
						DataCorrectionLogDto log = new DataCorrectionLogDto(GeneralDate.ymd(2018, 5, (i%28)+1), "User" + i, "Item" + k,
								"0900", "1508", k%4, "Admin", GeneralDateTime.now(), (i%28)+1);
						result.add(log);
						
					}
				}
			}
		}
		return result;
	}
}
