package nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogetherCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogetherRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.BusinessDaysCalendarType;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendarClass;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendarWorkplace;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
import org.apache.commons.lang3.tuple.Pair;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class BanHolidayTogetherQuery {
    @Inject
    private SyClassificationAdapter syClassificationAdapter;

    @Inject
    private SyWorkplaceAdapter syWorkplaceAdapter;

    @Inject
    private BanHolidayTogetherRepository BanHolidayTogetherRepo;

    /**
     * 同時休日禁止リストを取得する
     * 同日休日禁止リストを取得する
     *
     * @param unit             対象組織.単位
     * @param workplaceId      対象組織.職場ID
     * @param workplaceGroupId 対象組織.職場グループID
     * @return List<同時休日禁止>
     */
    public List<BanHolidayTogetherCodeNameDto> getAllBanHolidayTogether(int unit, String workplaceId, String workplaceGroupId) {
        TargetOrgIdenInfor targeOrg = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(unit, TargetOrganizationUnit.class),
                Optional.ofNullable(workplaceId),
                Optional.ofNullable(workplaceGroupId)
        );

        String companyId = AppContexts.user().companyId();

        //1. *getAll
        List<BanHolidayTogether> listBanHolidayTogether = BanHolidayTogetherRepo.getAll(companyId, targeOrg);

        List<BanHolidayTogetherCodeNameDto> dataList = new ArrayList<>();

        if (listBanHolidayTogether != null && !listBanHolidayTogether.isEmpty()) {
            dataList = listBanHolidayTogether.stream().map(i -> new BanHolidayTogetherCodeNameDto(
                    i.getBanHolidayTogetherCode().v(),
                    i.getBanHolidayTogetherName().v()
            )).collect(Collectors.toList());
        }

        return dataList;
    }

    /**
     * 同日休日禁止明細を表示する
     * コードを指定して同日休日禁止を取得する
     *
     * @param unit                   対象組織.単位
     * @param workplaceId            対象組織.職場ID
     * @param workplaceGroupId       対象組織.職場グループID
     * @param banHolidayTogetherCode 同日休日禁止コード
     * @return Optional<同時休日禁止>
     */
    public BanHolidayTogetherQueryDto getBanHolidayByCode(int unit, String workplaceId, String workplaceGroupId, String banHolidayTogetherCode) {
        String companyId = AppContexts.user().companyId();

        TargetOrgIdenInfor targeOrg = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(unit, TargetOrganizationUnit.class),
                Optional.ofNullable(workplaceId),
                Optional.ofNullable(workplaceGroupId)
        );

        BanHolidayTogetherCode code = new BanHolidayTogetherCode(banHolidayTogetherCode);

        //1. get
        Optional<BanHolidayTogether> banHolidayTogether = BanHolidayTogetherRepo.get(companyId, targeOrg, code);

        BanHolidayTogetherQueryDto data = new BanHolidayTogetherQueryDto();
        if (banHolidayTogether.isPresent()) {
            data.setBanHolidayTogetherCode(banHolidayTogether.get().getBanHolidayTogetherCode().v());
            data.setBanHolidayTogetherName(banHolidayTogether.get().getBanHolidayTogetherName().v());
            data.setMinOfWorkingEmpTogether(banHolidayTogether.get().getMinOfWorkingEmpTogether());
            data.setEmpsCanNotSameHolidays(banHolidayTogether.get().getEmpsCanNotSameHolidays());

            if (banHolidayTogether.get().getWorkDayReference().isPresent()) {
                //2. 営業日カレンダー種類を取得する()
                BusinessDaysCalendarType daysCalendarType = banHolidayTogether.get().getWorkDayReference().get().getBusinessDaysCalendarType();

                //3. <call>
                //3.1 分類コードより分類名称を取得する(分類コード)
                if (daysCalendarType == BusinessDaysCalendarType.CLASSSICATION) {
                    List<String> listClassCode = new ArrayList<>(Arrays.asList(((ReferenceCalendarClass) banHolidayTogether.get().getWorkDayReference().get()).getClassCode().v()));

                    Map<String, String> listClassification = syClassificationAdapter.getClassificationMapCodeName(companyId, listClassCode);

                    Map.Entry<String, String> entry = listClassification.entrySet().iterator().next();
                    data.setClassificationCode(entry.getKey());
                    data.setClassificationName(entry.getValue());
                }
                //3.2 取得する(会社ID, 職場ID, 年月日)
                else if (daysCalendarType == BusinessDaysCalendarType.WORKPLACE) {
                    List<String> listWorkplaceId = new ArrayList<>(Arrays.asList(((ReferenceCalendarWorkplace) banHolidayTogether.get().getWorkDayReference().get()).getWorkplaceID()));
                    List<GeneralDate> systemDate = new ArrayList<>(Arrays.asList(GeneralDate.today()));

                    Map<String, Pair<String, String>> listWorkplaceIdDateName = syWorkplaceAdapter.getWorkplaceMapCodeBaseDateName(companyId, listWorkplaceId, systemDate);

                    Map.Entry<String, Pair<String, String>> entry = listWorkplaceIdDateName.entrySet().iterator().next();
                    data.setWorkplaceInfoId(entry.getKey());
                    data.setWorkplaceInfoCode(entry.getValue().getKey());
                    data.setWorkplaceInfoName(entry.getValue().getValue());
                }
                data.setCheckDayReference(true);
                data.setSelectedWorkDayReference(daysCalendarType.value);
            }
        }

        return data;
    }
}
