package nts.uk.ctx.at.record.app.find.dailyperform.midnight;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.actualworkinghours.daily.midnight.MidnightTimeSheetRepo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author yennh
 *
 */
@Stateless
public class MidnightTimeSheetFinder {
	@Inject
	private MidnightTimeSheetRepo repo;
	
	/**
	 * Find all MidNightTimeSheet
	 * 
	 * @return
	 */
	public List<MidnightTimeSheetDto> findAllMidnightTimeSheet() {
		String companyId = AppContexts.user().companyId();
		return repo.findByCompanyId(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}
	
	private MidnightTimeSheetDto convertToDbType(MidNightTimeSheet midNightTimeSheet) {

		MidnightTimeSheetDto midnightTimeSheetDto = new MidnightTimeSheetDto();
		midnightTimeSheetDto.setEndTime(midNightTimeSheet.getEnd().v());
		midnightTimeSheetDto.setStartTime(midNightTimeSheet.getStart().v());

		return midnightTimeSheetDto;
	}
	
	public MidnightTimeSheetDto findByCid(){
		String companyId = AppContexts.user().companyId();
		return convertToDbType(repo.findByCId(companyId).get());
	}
}
