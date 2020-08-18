package nts.uk.ctx.at.schedule.dom.shift.pattern.service;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.shared.dom.WorkInformation;

import java.util.Optional;

/**
 * カレンダー情報を取得する
 * @author datnk
 *
 */
public interface WorkMonthlySettingService {

    Optional<AtomTask> register(WorkInformation.Require requireWorkInfo, WorkMonthlySettingServiceImpl.Require require, WorkMonthlySetting workMonthlySetting, Boolean isOverwrite);
}
