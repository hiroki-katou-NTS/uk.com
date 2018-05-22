package nts.uk.ctx.sys.log.app.finder.datacorrectionlog;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
		if (params.getDisplayFormat() == 0) {
			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < 2; j++) {
					for (int k = 0; k < 5; k++) {
						DataCorrectionLogDto log = new DataCorrectionLogDto("Date " + i, "User" + j, "Item" + k,
								"valueBefore", "valueAfter", "Admin", GeneralDateTime.now(), "Manual");
						result.add(log);
					}
				}
			}
		} else {
			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < 2; j++) {
					for (int k = 0; k < 5; k++) {
						DataCorrectionLogDto log = new DataCorrectionLogDto("Date " + j, "User" + i, "Item" + k,
								"valueBefore", "valueAfter", "Admin", GeneralDateTime.now(), "Manual");
						result.add(log);
					}
				}
			}
		}
		return result;
	}
}
