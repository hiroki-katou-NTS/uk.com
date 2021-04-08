package nts.uk.ctx.at.function.app.command.holidaysremaining;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.ItemOutputForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.Overtime;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.HolidaysRemainingManagementRepository;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.ItemSelectionEnum;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

@Stateless
@Transactional
public class AddHdRemainManageCommandHandler extends CommandHandler<HdRemainManageCommand> {

	@Inject
	private HolidaysRemainingManagementRepository holidaysRemainingManagementRepository;

	@Override
	protected void handle(CommandHandlerContext<HdRemainManageCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		HdRemainManageCommand command = context.getCommand();
		val overTime = new Overtime(
				command.isHD60HItem(),
				command.isHD60HRemain(),
				command.isHD60HUndigested()
		);
		ItemOutputForm itemOutputForm = new ItemOutputForm(
				command.isNursingLeave(),
				command.isRemainingChargeSubstitute(), command.isRepresentSubstitute(), command.isOutputItemSubstitute(),
				command.isOutputHolidayForward(), command.isMonthlyPublic(), command.isOutputItemsHolidays(),
				command.isChildNursingLeave(),
				command.isYearlyHoliday(), command.isInsideHours(), command.isInsideHalfDay(),
				command.isNumberRemainingPause(), command.isUnDigestedPause(), command.isPauseItem(), overTime,
				command.isYearlyReserved(), command.getListSpecialHoliday());

		if (!itemOutputForm.hasOutput()) {
			throw new BusinessException("Msg_880");
		}
		ItemSelectionEnum itemSelectionCategory =
				EnumAdaptor.valueOf(command.getItemSelType(), ItemSelectionEnum.class);
		val layOutId = IdentifierUtil.randomUniqueId();
		Optional<String> employeeIdOpt = Optional.empty();
		if(itemSelectionCategory == ItemSelectionEnum.FREE_SETTING){
			employeeIdOpt = Optional.of(AppContexts.user().employeeId());
		}
		HolidaysRemainingManagement domain = new HolidaysRemainingManagement(
				companyId,
				command.getCd(),
				command.getName(),
				itemOutputForm,
				layOutId,
				itemSelectionCategory,
				employeeIdOpt);

		holidaysRemainingManagementRepository.insert(domain);

	}


}
