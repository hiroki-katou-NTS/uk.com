package nts.uk.ctx.at.shared.app.command.outsideot.setting;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.TimeRoundingOfExcessOutsideTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMedRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNote;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Stateless
public class PremiumExtra60HCommandHandler extends CommandHandler<PremiumExtra60HComand> {


    /**
     * The outside OT setting repository.
     */
    @Inject
    private OutsideOTSettingRepository outsideOTSettingRepository;

    /**
     * The super HD 60 H con med repository.
     */
    @Inject
    private SuperHD60HConMedRepository superHD60HConMedRepository;

    @Override
    protected void handle(CommandHandlerContext<PremiumExtra60HComand> context) {
        // get login user
        LoginUserContext loginUserContext = AppContexts.user();
        // get company id
        String companyId = loginUserContext.companyId();
        // get command
        val command = context.getCommand();
        val domainSettingOpt = outsideOTSettingRepository.findById(companyId);
        if (domainSettingOpt.isPresent()) {
            val domainSetting = domainSettingOpt.get();
            OvertimeNote note = domainSetting.getNote();
            OutsideOTCalMed calculationMethod = domainSetting.getCalculationMethod();
            List<Overtime> overtimes = domainSetting.getOvertimes();
            List<OutsideOTBRDItem> breakdownItems = domainSetting.getBreakdownItems()
                    .stream().map(e -> new OutsideOTBRDItem(
                            e.getUseClassification(),
                            e.getBreakdownItemNo(),
                            e.getName(),
                            e.getProductNumber(),
                            e.getAttendanceItemIds(),
                            command.getPremiumExtra60HRates()
                                    .stream().filter(x -> x.getBreakdownItemNo().equals(e.getBreakdownItemNo().value)).map(j -> new PremiumExtra60HRate(
                                    new PremiumRate(j.getPremiumRate()), EnumAdaptor.valueOf(j.getOvertimeNo(), OvertimeNo.class))
                            ).collect(Collectors.toList())
                    )).collect(Collectors.toList());
            Optional<TimeRoundingOfExcessOutsideTime> timeRoundingOfExcessOutsideTime = domainSetting.getTimeRoundingOfExcessOutsideTime();

            val domainNew = new OutsideOTSetting(companyId, note, breakdownItems, calculationMethod, overtimes, timeRoundingOfExcessOutsideTime);
            this.outsideOTSettingRepository.save(domainNew);
        }

        SuperHD60HConMed domainSupper = command.toDomainSuper();
        // save domain
        this.superHD60HConMedRepository.save(domainSupper);
    }

}
