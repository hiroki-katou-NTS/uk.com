package nts.uk.ctx.at.shared.infra.repository.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWorkRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.OtHdWorkAppSettingRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.otworkapply.OtWorkAppReflectRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class JpaAppReflectOtHdWorkRepository extends JpaRepository implements AppReflectOtHdWorkRepository {
    @Inject
    private OtHdWorkAppSettingRepository appSetRepo;

    @Inject
    private OtWorkAppReflectRepository otWorkReflectRepo;

    @Inject
    private HdWorkAppReflectRepository hdWorkReflectRepo;

    @Override
    public Optional<AppReflectOtHdWork> findByCompanyId(String companyId) {
        Integer nightOvertimeReflectAtr = appSetRepo.getNightOvertimeReflectAtr(companyId);
        Optional<OtWorkAppReflect> optionalOtWorkAppReflect = otWorkReflectRepo.findReflectByCompanyId(companyId);
        Optional<HdWorkAppReflect> optionalHdWorkAppReflect = hdWorkReflectRepo.findReflectByCompany(companyId);
        if (nightOvertimeReflectAtr == null && !optionalHdWorkAppReflect.isPresent() && !optionalOtWorkAppReflect.isPresent())
            return Optional.empty();
        else if (nightOvertimeReflectAtr != null && optionalHdWorkAppReflect.isPresent() && optionalOtWorkAppReflect.isPresent())
            return Optional.of(new AppReflectOtHdWork(
                    companyId,
                    optionalHdWorkAppReflect.get(),
                    optionalOtWorkAppReflect.get(),
                    EnumAdaptor.valueOf(nightOvertimeReflectAtr, NotUseAtr.class)
            ));
        else
            throw new BusinessException("Setting Master Data for Overtime/Holiday Work Application Reflection not found!");
    }
}
