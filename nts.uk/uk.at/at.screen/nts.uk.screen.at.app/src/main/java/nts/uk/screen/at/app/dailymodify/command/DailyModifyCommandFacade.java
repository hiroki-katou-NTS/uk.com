package nts.uk.screen.at.app.dailymodify.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;
import javax.transaction.Transactional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.actualworkinghours.AttendanceTimeOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.workrecord.daily.DailyWorkRecordCommand;
import nts.uk.ctx.at.record.app.find.dailyperform.AttendanceTimeOfDailyPerformFinder;
import nts.uk.ctx.at.record.app.find.workrecord.daily.DailyWorkRecordFinder;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemUtil;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQuery;

@Stateless
@Transactional
@SuppressWarnings("serial")
/** 日別修正CommandFacade */
public class DailyModifyCommandFacade {

	/** finder */
	private static final Map<String, Class<? extends FinderFacade>> FINDER_CLASSES;
	static {
		FINDER_CLASSES = new HashMap<>();
		//Sample
		FINDER_CLASSES.put("DailyWorkRecord", DailyWorkRecordFinder.class);
		FINDER_CLASSES.put("AttendanceTimeOfDailyPerformance", AttendanceTimeOfDailyPerformFinder.class);
	}

	private static final Map<String, TypeLiteral<?>> ADD_COMMAND_CLASSES;
	static {
		//Sample
		ADD_COMMAND_CLASSES = new HashMap<>();
		ADD_COMMAND_CLASSES.put("DailyWorkRecord", new TypeLiteral<CommandFacade<DailyWorkRecordCommand>>(){});
		ADD_COMMAND_CLASSES.put("AttendanceTimeOfDailyPerformance", new TypeLiteral<CommandFacade<AttendanceTimeOfDailyPerformCommand>>(){});
	}

	public void handle(Map<String, List<ItemValue>> itemValues, DailyModifyQuery query) {
		itemValues.entrySet().stream().forEach(entry -> {
			handle(ADD_COMMAND_CLASSES.get(entry.getKey()), toDto(entry, query.getEmployeeId(), query.getBaseDate()));
		});
	}

	private ConvertibleAttendanceItem toDto(Entry<String, List<ItemValue>> entry, String employeeId, GeneralDate baseDate) {
		FinderFacade finder = CDI.current().select(FINDER_CLASSES.get(entry.getKey())).get();
		ConvertibleAttendanceItem oldValues = finder.find(employeeId, baseDate);
		return AttendanceItemUtil.toConvertibleAttendanceItem(oldValues, entry.getValue());
	}
	
	@SuppressWarnings("unchecked")
	private <T extends AttendanceItemCommand> void handle(TypeLiteral<?> type, ConvertibleAttendanceItem data){
		CommandFacade<T> handler = (CommandFacade<T>) CDI.current().select(type).get();
		AttendanceItemCommand command = handler.newCommand();
		command.setRecords(data);
		handler.handle((T) command);
		
	}
}
