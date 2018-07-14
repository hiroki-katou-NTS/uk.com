package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.specialholiday.GenderAtr;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.ClassificationList;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.EmploymentList;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.FixedDayGrant;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.FixedDayType;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEventRepository;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.AgeLimit;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.AgeRange;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@Transactional
@Stateless
public class SaveSpecialHolidayEventCommandHandler extends CommandHandler<SaveSpecialHolidayEventCommand> {

	@Inject
	private SpecialHolidayEventRepository sHEventRepo;

	@Override
	protected void handle(CommandHandlerContext<SaveSpecialHolidayEventCommand> context) {
		SaveSpecialHolidayEventCommand cmd = context.getCommand();
		String companyId = AppContexts.user().companyId();
		boolean iscreateNew = cmd.isCreateNew();
		SpecialHolidayEvent domain = fromCommandToDomain(cmd);
		// 登録時チェック処理(thực hiện xử lý check khi đăng ký)
		checkReg(companyId, domain, iscreateNew);
		// 登録処理(xử lý đăng ký)
		regProcess(domain, iscreateNew);
	}

	private void regProcess(SpecialHolidayEvent domain, boolean iscreateNew) {
		if (iscreateNew) {
			this.sHEventRepo.insert(domain);
		} else {
			this.sHEventRepo.update(domain);
		}
	}

	private void checkReg(String companyId, SpecialHolidayEvent domain, boolean iscreateNew) {

		int noSelected = domain.getSpecialHolidayEventNo();
		if (iscreateNew) {
			Optional<SpecialHolidayEvent> sHEventOpt = this.sHEventRepo.findByKey(companyId, noSelected);
			// ドメインモデル「事象に対する特別休暇」のエラーチェック(check error domain「事象に対する特別休暇」)
			validateSHEventDomain(domain, sHEventOpt);
		}
		// ドメインモデル「利用制限設定」のエラーチェック(check error domain「利用制限設定」)
		domain.validate();
	}

	private void validateSHEventDomain(SpecialHolidayEvent domain, Optional<SpecialHolidayEvent> sHEventOpt) {
		sHEventOpt.ifPresent(x -> {
			throw new BusinessException("Msg_3");
		});
	}

	private SpecialHolidayEvent fromCommandToDomain(SaveSpecialHolidayEventCommand cmd) {
		return new SpecialHolidayEvent(cmd.getCompanyId(), cmd.getSpecialHolidayEventNo(),
				EnumAdaptor.valueOf(cmd.getLimitFixedDays(), FixedDayType.class), cmd.getRefRelationShip(),
				new FixedDayGrant(cmd.getFixedDayGrant()), EnumAdaptor.valueOf(cmd.getMakeInvitation(), UseAtr.class),
				EnumAdaptor.valueOf(cmd.getIncludeHolidays(), UseAtr.class),
				EnumAdaptor.valueOf(cmd.getAgeLimit(), UseAtr.class),
				EnumAdaptor.valueOf(cmd.getGenderRestrict(), UseAtr.class),
				EnumAdaptor.valueOf(cmd.getRestrictEmployment(), UseAtr.class),
				EnumAdaptor.valueOf(cmd.getRestrictClassification(), UseAtr.class),
				EnumAdaptor.valueOf(cmd.getGender(), GenderAtr.class),
				new AgeRange(new AgeLimit(cmd.getAgeRange().getAgeLowerLimit()),
						new AgeLimit(cmd.getAgeRange().getAgeHigherLimit())),
				cmd.getAgeStandardYear(), cmd.getAgeStandardBaseDate(), new Memo(cmd.getMemo()),
				createClsList(cmd.getClsList()), createEmpList(cmd.getEmpList()));
	}

	private List<EmploymentList> createEmpList(List<EmploymentListCommand> empList) {
		return empList.stream().map(x -> new EmploymentList(x.getCompanyId(), x.getSpecialHolidayEventNo(),
				new EmploymentCode(x.getEmploymentCd()))).collect(Collectors.toList());
	}

	private List<ClassificationList> createClsList(List<ClassificationListCommand> clsList) {
		return clsList.stream().map(
				x -> new ClassificationList(x.getCompanyId(), x.getSpecialHolidayEventNo(), x.getClassificationCd()))
				.collect(Collectors.toList());
	}

}
