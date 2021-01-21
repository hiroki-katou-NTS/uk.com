/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.work;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WorkMonthlySettingBatchSaveCommandHandler.
 */
@Stateless
public class WorkMonthlySettingBatchSaveCommandHandler
		extends CommandHandler<WorkMonthlySettingBatchSaveCommand> {

	/** The Constant ADD. */
	public static final int ADD = 1;

	/** The Constant UPDATE. */
	public static final int UPDATE = 2;

	/** The Constant INDEX_FIRST. */
	public static final int INDEX_FIRST = 0;

	/** The repository. */
	@Inject
	private WorkMonthlySettingRepository workMonthlySettingRepository;

	/** The monthly pattern repository. */
	@Inject
	private MonthlyPatternRepository monthlyPatternRepository;

	/** The monthly pattern repository. */
	@Inject
	private WorkTimeSettingRepository workTimeRepository;

	/** The monthly pattern repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;

	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;
	
	/** The date format. */
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkMonthlySettingBatchSaveCommand> context) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get command
		WorkMonthlySettingBatchSaveCommand command = context.getCommand();

		// convert to map domain update
		Map<Integer, WorkMonthlySettingDto> mapWorkMonthlySetting = command
				.getWorkMonthlySetting().stream().collect(Collectors.toMap((dto) -> {
					return Integer.parseInt(dto.getYmdk().replaceAll("/", ""));
				}, Function.identity()));

		// get to date
		Date toDate = GeneralDate.fromString(
				command.getWorkMonthlySetting().get(INDEX_FIRST).getYmdk(), DATE_FORMAT).date();

		// get year month
		int yearMonth = this.getYearMonth(toDate);

		// update begin date
		toDate = this.toDate(yearMonth * MONTH_MUL + NEXT_DAY);

		// to list domain
		List<WorkMonthlySetting> lstDomain = command.toDomainMonth(companyId);
        List<GeneralDate> baseDates = lstDomain.stream().map(domainsetting -> domainsetting.getYmdk()).collect(Collectors.toList());
		lstDomain = lstDomain.stream().filter(x -> x.getWorkInformation().getWorkTypeCode() != null &&
				!StringUtil.isNullOrEmpty(x.getWorkInformation().getWorkTypeCode().v(),false)
				&& !x.getWorkInformation().getWorkTypeCode().equals("000")).collect(Collectors.toList());


		// check setting work type
		lstDomain.forEach(domain -> {
			Optional<WorkType> worktype = this.workTypeRepository.findByPK(companyId,
					domain.getWorkInformation().getWorkTypeCode().v());

			// not exist data
			if (!worktype.isPresent()) {
				throw new BusinessException("Msg_389");
			}

			/*// not use
			if (worktype.get().getDeprecate().value == DeprecateClassification.Deprecated.value) {
				throw new BusinessException("Msg_416");
			}*/
		});

		// check setting work time
		lstDomain.forEach(domain -> {
			if (domain.getWorkInformation().getWorkTimeCode()!= null
					&& !StringUtil.isNullOrEmpty(domain.getWorkInformation().getWorkTimeCode().v(), true)) {
				Optional<WorkTimeSetting> worktime = this.workTimeRepository.findByCode(companyId,
						domain.getWorkInformation().getWorkTimeCode().v());

				// not exist data
				if (!worktime.isPresent()) {
					throw new BusinessException("Msg_390");
				}

				/*// not use
				if (worktime.get().getAbolishAtr().value == AbolishAtr.ABOLISH.value) {
					throw new BusinessException("Msg_417");
				}*/
			}
		});

		// check pair work type and work time
		lstDomain.forEach(domain -> {
			this.basicScheduleService.checkPairWorkTypeWorkTime(domain.getWorkInformation().getWorkTypeCode().v(),
					domain.getWorkInformation().getWorkTimeCode() == null ? null : domain.getWorkInformation().getWorkTimeCode().v());
		});

		// command to domain
		MonthlyPattern domain = command.toDomain(companyId);

		// validate domain
		domain.validate();

		// check mode ADD
		if (command.getMode() == ADD) {
			// check exist data add
			Optional<MonthlyPattern> monthlyPattern = this.monthlyPatternRepository
					.findById(companyId, domain.getMonthlyPatternCode().v());

			// show message
			if (monthlyPattern.isPresent()) {
				throw new BusinessException("Msg_3");
			}

			// call repository add domain
			this.monthlyPatternRepository.add(domain);
		} else {
			// check exist data add
			Optional<MonthlyPattern> monthlyPattern = this.monthlyPatternRepository
					.findById(companyId, domain.getMonthlyPatternCode().v());

			// add domain
			if (!monthlyPattern.isPresent()) {
				this.monthlyPatternRepository.add(domain);
			}
			// call repository update domain
			this.monthlyPatternRepository.update(domain);
		}

		// get list domain update
		List<WorkMonthlySetting> domainUpdates = this.workMonthlySettingRepository.findByYMD(
				companyId,command.getMonthlyPattern().getCode(), baseDates);

		// convert to map domain update
		Map<GeneralDate, WorkMonthlySetting> mapDomainUpdate = domainUpdates.stream()
				.collect(Collectors.toMap((domainsetting) -> {
					return domainsetting.getYmdk();
				}, Function.identity()));

		// add all domain
		List<WorkMonthlySetting> addAllDomains = new ArrayList<>();

		// update all domain
		List<WorkMonthlySetting> updateAllDomains = new ArrayList<>();

		// domain update all, add all collection
		lstDomain.forEach(domainsetting -> {

			if (StringUtils.isEmpty(domainsetting.getWorkInformation().getWorkTimeCode() == null ? null : domainsetting.getWorkInformation().getWorkTimeCode().v())){
				domainsetting.setWorkInformation(new WorkInformation(domainsetting.getWorkInformation().getWorkTypeCode().v(),null));
			}

			// check exist of domain update
			if (mapDomainUpdate.containsKey(domainsetting.getYmdk())) {
				updateAllDomains.add(domainsetting);
			} else {
				addAllDomains.add(domainsetting);
			}
		});
		// update all list domain

		this.workMonthlySettingRepository.updateAll(updateAllDomains);

		List<GeneralDate> listYmdk = new ArrayList<>();
		updateAllDomains.forEach(x -> {
			listYmdk.add(x.getYmdk());
		});

		List<WorkMonthlySetting> deleteAllDomains = domainUpdates.stream().filter(x -> !listYmdk.contains(x.getYmdk())).collect(Collectors.toList());
		deleteAllDomains.forEach(x -> {
			this.workMonthlySettingRepository.deleteWorkMonthlySettingById(x.getCompanyId().v(),x.getMonthlyPatternCode().v(),x.getYmdk());
		});
		// add all list domain
		this.workMonthlySettingRepository.addAll(addAllDomains);
	}

	/** The Constant NEXT_DAY. */
	public static final int NEXT_DAY = 1;

	/** The Constant BEGIN_END_MONTH. */
	public static final int BEGIN_END_MONTH = 12;

	/** The Constant ZERO_DAY_MONTH. */
	public static final int ZERO_DAY_MONTH = 0;

	/** The Constant YEAR_MUL. */
	public static final int YEAR_MUL = 10000;

	/** The Constant MONTH_MUL. */
	public static final int MONTH_MUL = 100;

	/**
	 * Next day.
	 *
	 * @param day
	 *            the day
	 * @return the date
	 */
	public Date nextDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_MONTH, NEXT_DAY);
		return cal.getTime();
	}

	/**
	 * Gets the year month date.
	 *
	 * @param day
	 *            the day
	 * @return the year month date
	 */
	public int getYearMonthDate(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		return cal.get(Calendar.YEAR) * YEAR_MUL + (cal.get(Calendar.MONTH) + NEXT_DAY) * MONTH_MUL
				+ cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Gets the year month.
	 *
	 * @param day
	 *            the day
	 * @return the year month
	 */
	public int getYearMonth(Date day) {
		return getYearMonthDate(day) / MONTH_MUL;
	}

	/**
	 * To date.
	 *
	 * @param yearMonthDate
	 */
	public Date toDate(int yearMonthDate) {
		Calendar cal = Calendar.getInstance();
		cal.set(yearMonthDate / YEAR_MUL, (yearMonthDate % YEAR_MUL) / MONTH_MUL - NEXT_DAY,
				yearMonthDate % MONTH_MUL, ZERO_DAY_MONTH, ZERO_DAY_MONTH);
		return cal.getTime();
	}
}
