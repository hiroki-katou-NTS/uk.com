package nts.uk.screen.at.app.dailymodify.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.ctx.at.record.app.command.workrecord.daily.DailyWorkRecordCommand;
import nts.uk.ctx.at.record.app.find.workrecord.daily.DailyWorkRecordFinder;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemUtil;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;

@Stateless
@Transactional
@SuppressWarnings("serial")
public class DailyModifyCommandFacade {

	/** finder */
	private static final Map<String, Class<? extends FinderFacade>> FINDER_CLASSES;
	static {
		FINDER_CLASSES = new HashMap<>();
		FINDER_CLASSES.put("DailyWorkRecord", DailyWorkRecordFinder.class);
	}

	private static final Map<String, Class<? extends AttendanceItemCommand>> ADD_COMMAND_CLASSES;
	static {
		ADD_COMMAND_CLASSES = new HashMap<>();
		ADD_COMMAND_CLASSES.put("DailyWorkRecord", DailyWorkRecordCommand.class);
	}

	// @Inject
	// private DailyWorkRecordFinder finder;
	//
	// @Inject
	// private DailyWorkRecordCommandHandler handler;

	public void handleWorkRecord(List<ItemValue> itemValues) {
		FINDER_CLASSES.entrySet().stream().forEach(entry -> {
			FinderFacade finder = CDI.current().select(entry.getValue()).get();
			ConvertibleAttendanceItem oldValues = finder.find();
			oldValues = AttendanceItemUtil.toConvertibleAttendanceItem(oldValues, itemValues);
			handle(ADD_COMMAND_CLASSES.get(entry.getKey()), oldValues);
		});
	}
	
	@SuppressWarnings("unchecked")
	private <T extends AttendanceItemCommand> void handle(Class<T> commandClass, ConvertibleAttendanceItem data){
		CommandHandler<T> handler = CDI.current().select(new TypeLiteral<CommandHandler<T>>(){}).get();
		AttendanceItemCommand command = ReflectionUtil.newInstance(commandClass);
		command.setRecords(data);
		handler.handle((T) command);
	}
}
