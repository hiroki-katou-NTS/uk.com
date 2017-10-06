package nts.uk.ctx.at.schedule.app.find.executionlog.dto;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.CreateMethodAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ProcessExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentSetMemento;

/**
 * The Class ExecutionContentDto.
 */
@Setter
public class ScheduleCreateContentDto implements ScheduleCreateContentSetMemento {

	/** The execution id. */
	// 実行ID
	public String executionId;

	/** The copy start date. */
	// コピー開始日
	public GeneralDate copyStartDate;

	// 作成方法区分
	public Integer createMethodAtr;

	/** The confirm. */
	// 作成時に確定済みにする
	public Boolean confirm;

	/** The implement atr. */
	// 実施区分
	public Integer implementAtr;

	/** The process execution atr. */
	// 処理実行区分
	public Integer processExecutionAtr;

	/** The re create atr. */
	// 再作成区分
	public Integer reCreateAtr;

	/** The reset master info. */
	// マスタ情報再設定
	public Boolean resetMasterInfo;

	/** The reset absent holiday business. */
	// 休職休業再設定
	public Boolean resetAbsentHolidayBusines;

	/** The reset working hours. */
	// 就業時間帯再設定
	public Boolean resetWorkingHours;

	/** The reset time assignment. */
	// 申し送り時間再設定
	public Boolean resetTimeAssignment;

	/** The reset direct line bounce. */
	// 直行直帰再設定
	public Boolean resetDirectLineBounce;

	/** The reset time child care. */
	// 育児介護時間再設定
	public Boolean resetTimeChildCare;
	
	/** The start date. */
	public GeneralDate startDate;
	
	/** The end date. */
	public GeneralDate endDate;
	
	/** The execution start. */
	public GeneralDateTime executionStart;
	
	/** The execution end. */
	public GeneralDateTime executionEnd;
	
	/** The count execution. */
	public Integer countExecution;
	
	/** The count error. */
	public Integer countError;

	@Override
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	@Override
	public void setCopyStartDate(GeneralDate copyStartDate) {
		this.copyStartDate = copyStartDate;
	}

	@Override
	public void setCreateMethodAtr(CreateMethodAtr createMethodAtr) {
		this.createMethodAtr = createMethodAtr.value;
	}

	@Override
	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

	@Override
	public void setImplementAtr(ImplementAtr implementAtr) {
		this.implementAtr = implementAtr.value;
	}

	@Override
	public void setProcessExecutionAtr(ProcessExecutionAtr processExecutionAtr) {
		this.processExecutionAtr = processExecutionAtr.value;
	}

	@Override
	public void setReCreateAtr(ReCreateAtr reCreateAtr) {
		this.reCreateAtr = reCreateAtr.value;
	}

	@Override
	public void setResetMasterInfo(Boolean resetMasterInfo) {
		this.resetMasterInfo = resetMasterInfo;
	}

	@Override
	public void setResetAbsentHolidayBusines(Boolean resetAbsentHolidayBusines) {
		this.resetAbsentHolidayBusines = resetAbsentHolidayBusines;
	}

	@Override
	public void setResetWorkingHours(Boolean resetWorkingHours) {
		this.resetWorkingHours = resetWorkingHours;
	}

	@Override
	public void setResetTimeAssignment(Boolean resetTimeAssignment) {
		this.resetTimeAssignment = resetTimeAssignment;
	}

	@Override
	public void setResetDirectLineBounce(Boolean resetDirectLineBounce) {
		this.resetDirectLineBounce = resetDirectLineBounce;
	}

	@Override
	public void setResetTimeChildCare(Boolean resetTimeChildCare) {
		this.resetTimeChildCare = resetTimeChildCare;
	}

}
