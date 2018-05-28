package nts.uk.screen.at.app.dailymodify.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommandHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.editstatecolor.EditStateColorOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.editstatecolor.EditStateColorOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.ContentApproval;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.ParamDayApproval;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.RegisterDayApproval;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.ParamIdentityConfirmDay;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.RegisterIdentityConfirmDay;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.SelfConfirmDay;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQuery;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemCheckBox;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
/** 日別修正CommandFacade */
public class DailyModifyCommandFacade {

	/** finder */
	@Inject
	private DailyRecordWorkFinder finder;

	@Inject
	private DailyRecordWorkCommandHandler handler;

	@Inject
	private EditStateColorOfDailyPerformCommandAddHandler editStateHandler;

	@Inject
	private RegisterIdentityConfirmDay registerIdentityConfirmDay;
	
	@Inject
	private RegisterDayApproval registerDayApproval;

	public void handleAdd(DailyModifyQuery query) {
		DailyRecordDto dto = toDto(query);
		this.handler.handleAdd(createCommand(dto, query));
	}

	public void handleUpdate(DailyModifyQuery query) {
		String sid = AppContexts.user().employeeId();
		List<EditStateOfDailyPerformance> editData = query.getItemValues().stream().map(x -> {
			return new EditStateOfDailyPerformance(query.getEmployeeId(), x.getItemId(), query.getBaseDate(),
					sid.equals(query.getEmployeeId()) ? EditStateSetting.HAND_CORRECTION_MYSELF
							: EditStateSetting.HAND_CORRECTION_OTHER);
		}).collect(Collectors.toList());
		DailyRecordDto dto = toDto(query);
		DailyRecordWorkCommand comand = createCommand(dto, query);
		comand.getEditState().updateDatas(editData);
		this.handler.handleUpdate(comand);
	}

	private DailyRecordDto toDto(DailyModifyQuery query) {
		DailyRecordDto oldValues = finder.find(query.getEmployeeId(), query.getBaseDate());
		oldValues = AttendanceItemUtil.fromItemValues(oldValues, query.getItemValues());
		oldValues.getTimeLeaving().ifPresent(dto -> {
			if(dto.getWorkAndLeave() != null) dto.getWorkAndLeave().removeIf(tl -> tl.getWorking() == null && tl.getLeave() == null);
		});
		return oldValues;
	}

	private DailyRecordWorkCommand createCommand(DailyRecordDto dto, DailyModifyQuery query) {
		return DailyRecordWorkCommand.open().forEmployeeId(query.getEmployeeId()).withWokingDate(query.getBaseDate())
				.withData(dto).fromItems(query.getItemValues());
	}

	public void handleEditCell(List<DPItemValue> data) {
		String sid = AppContexts.user().employeeId();
		List<EditStateOfDailyPerformance> editData = data.stream().map(x -> {
			return new EditStateOfDailyPerformance(x.getEmployeeId(), x.getItemId(), x.getDate(),
					sid.equals(x.getEmployeeId()) ? EditStateSetting.HAND_CORRECTION_MYSELF
							: EditStateSetting.HAND_CORRECTION_OTHER);
		}).collect(Collectors.toList());
		EditStateColorOfDailyPerformCommand command = new EditStateColorOfDailyPerformCommand();
		command.getData().addAll(editData);
		editStateHandler.handle(command);
	}

	public void insertSign(List<DPItemCheckBox> dataCheckSign) {
		ParamIdentityConfirmDay day = new ParamIdentityConfirmDay(AppContexts.user().employeeId(), dataCheckSign
				.stream().map(x -> new SelfConfirmDay(x.getDate(), x.isValue())).collect(Collectors.toList()));
		registerIdentityConfirmDay.registerIdentity(day);
	}
	
	public void insertApproval(List<DPItemCheckBox> dataCheckApproval) {
		ParamDayApproval param = new ParamDayApproval(AppContexts.user().employeeId(),
				dataCheckApproval.stream()
						.map(x -> new ContentApproval(x.getDate(), x.isValue(), x.getEmployeeId(), x.isFlagRemoveAll()))
						.collect(Collectors.toList()));
		registerDayApproval.registerDayApproval(param);
	}
}
