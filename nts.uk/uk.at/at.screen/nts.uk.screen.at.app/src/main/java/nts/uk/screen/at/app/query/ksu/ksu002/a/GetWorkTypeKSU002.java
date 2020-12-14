package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.WorkTypeDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 社員の所属職場情報を取得する
 * @author chungnt
 *
 */
@Stateless
public class GetWorkTypeKSU002 {

	@Inject
	private BasicScheduleService basicScheduleService;

	public List<WorkTypeDto> getWorkType() {
		String cid = AppContexts.user().companyId();

		// <<Public>> 廃止されていない勤務種類をすべて取得する
		List<WorkType> listWorkType = basicScheduleService.getAllWorkTypeNotAbolished(cid);
		List<WorkTypeDto> listWorkTypeDto = listWorkType.stream().map(mapper -> {
			return new WorkTypeDto(mapper);
		}).collect(Collectors.toList());

		return listWorkTypeDto;
	}
}
