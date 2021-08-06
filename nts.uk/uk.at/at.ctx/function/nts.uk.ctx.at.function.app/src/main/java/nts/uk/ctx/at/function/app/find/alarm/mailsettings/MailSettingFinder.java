package nts.uk.ctx.at.function.app.find.alarm.mailsettings;

import lombok.val;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRoleRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingAutomaticRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormalRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.stream.Collectors;

@Stateless
public class MailSettingFinder {

    @Inject
    private MailSettingAutomaticRepository mailAutomaticRepo;

    @Inject
    private MailSettingNormalRepository mailNormalRepo;

    @Inject
    private AlarmMailSendingRoleRepository mailSendingRoleRepository;

    @Inject
    private AlarmListExecutionMailSettingRepository mailSettingRepository;

    public MailAutoAndNormalDto findMailSet() {
        String companyId = AppContexts.user().companyId();
        if (StringUtil.isNullOrEmpty(companyId, true))
            return null;

        MailSettingAutomaticDto mailSettingAutomaticDto = new MailSettingAutomaticDto(mailAutomaticRepo.findByCompanyId(companyId));
        MailSettingNormalDto mailSettingNormalDto = new MailSettingNormalDto(mailNormalRepo.findByCompanyId(companyId));

        return new MailAutoAndNormalDto(mailSettingAutomaticDto, mailSettingNormalDto);
    }

    public AlarmMailSendingRoleDto getRole(int individualWkpClassify) {
        val role = mailSendingRoleRepository.find(AppContexts.user().companyId(), individualWkpClassify);
        if (!role.isPresent()) {
            return null;
        }
        return new AlarmMailSendingRoleDto(role.get().getIndividualWkpClassify().value,
                role.get().isRoleSetting(),
                role.get().isSendResult(),
                role.get().getRoleIds()
        );
    }

    public MailSettingDto getConfigured(int personalManagerClassify, int individualWRClassification) {
        val alarmListExecutionMailSettingList = mailSettingRepository.getByCompanyId(AppContexts.user().companyId(),
                personalManagerClassify,
                individualWRClassification
        );
        if (alarmListExecutionMailSettingList.isEmpty()) {
            return new MailSettingDto(false, Collections.emptyList());
        }
        boolean isConfigured = alarmListExecutionMailSettingList.stream().anyMatch(x -> x.isAlreadyConfigured(x.getCompanyId(),
                x.getIndividualWkpClassify().value,
                x.getIndividualWkpClassify().value,
                x.getPersonalManagerClassify().value
        ));
        val mailSettins = alarmListExecutionMailSettingList.stream().map(x -> {
                    val mailContents = x.getContentMailSettings();
                    return new RegisterAlarmExecutionMailSettingsDTO(
                            x.getIndividualWkpClassify().value,
                            x.getNormalAutoClassify().value,
                            x.getPersonalManagerClassify().value,
                            mailContents.isPresent() ? new MailSettingsContentDto(
                                    mailContents.get().getSubject().isPresent() ? mailContents.get().getSubject().get().v() : "",
                                    mailContents.get().getText().isPresent() ? mailContents.get().getText().get().v() : "",
                                    mailContents.get().getMailAddressBCC().stream().map(bcc -> bcc.v()).collect(Collectors.toList()),
                                    mailContents.get().getMailAddressCC().stream().map(cc -> cc.v()).collect(Collectors.toList()),
                                    mailContents.get().getMailRely().isPresent() ? mailContents.get().getMailRely().get().v() : ""
                            ) : null,
                            x.getSenderAddress().v(),
                            x.isSendResult()

                    );
                }
        ).collect(Collectors.toList());
        return new MailSettingDto(isConfigured, mailSettins);
    }
}
