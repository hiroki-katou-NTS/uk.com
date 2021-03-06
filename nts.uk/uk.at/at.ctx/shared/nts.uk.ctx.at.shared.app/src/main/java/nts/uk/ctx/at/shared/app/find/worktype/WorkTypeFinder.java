/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktype;

import lombok.val;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeInfor;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguage;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguageRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Class WorkTypeFinder.
 */
@Stateless
public class WorkTypeFinder {

    /**
     * The work type repo.
     */
    @Inject
    private WorkTypeRepository workTypeRepo;

    /**
     * The work type language repo.
     */
    @Inject
    private WorkTypeLanguageRepository workTypeLanguageRepo;


    /**
     * Gets the possible work type.
     *
     * @param lstPossible the lst possible
     * @return the possible work type
     */
    public List<WorkTypeInfor> getPossibleWorkType(List<String> lstPossible) {
        // company id
        String companyId = AppContexts.user().companyId();
        List<WorkTypeInfor> lst = this.workTypeRepo.getPossibleWorkTypeAndOrder(companyId, lstPossible);
        return lst;
    }

    /**
     * Gets the possible work type. with No Master
     *
     * @param lstPossible the lst possible
     * @return the possible work type
     */
    public List<WorkTypeInfor> getPossibleWorkTypeWithNoMaster(List<String> lstPossible) {
        // company id
        String companyId = AppContexts.user().companyId();
        List<WorkTypeInfor> lst = this.workTypeRepo.getPossibleWorkTypeWithNoMasterAndOrder(companyId, lstPossible);
        return lst;
    }

    /**
     * Gets the not remove work type. with No Master
     *
     * @param lstPossible the lst possible
     * @return the possible work type
     */
    public List<WorkTypeInfor> getNotRemoveWorkType(List<String> lstPossible) {
        // company id
        String companyId = AppContexts.user().companyId();
        List<WorkTypeInfor> lst = this.workTypeRepo.getNotRemoveWorkType(companyId, lstPossible);
        return lst;
    }

    /**
     * Find not deprecated by list code.
     *
     * @param codes the codes
     * @return the list
     */
    public List<WorkTypeDto> findNotDeprecatedByListCode(List<String> codes) {
        // company id
        String companyId = AppContexts.user().companyId();
        return this.workTypeRepo.findNotDeprecatedByListCode(companyId, codes).stream()
                .map(dom -> WorkTypeDto.fromDomain(dom)).collect(Collectors.toList());
    }

    /**
     * Find by company id.
     *
     * @return the list
     */
    public List<WorkTypeDto> findByCompanyId() {
        // company id
        String companyId = AppContexts.user().companyId();

        List<WorkTypeDto> listWorktypeDto = this.workTypeRepo.findByCompanyId(companyId).stream().map(c -> {
            List<WorkTypeSetDto> workTypeSetList = c.getWorkTypeSetList().stream()
                    .map(x -> WorkTypeSetDto.fromDomain(x)).collect(Collectors.toList());

            WorkTypeDto workType = WorkTypeDto.fromDomain(c);
            workType.setWorkTypeSets(workTypeSetList);
            return workType;
        }).collect(Collectors.toList());

        // Sorting by workType Code
        Collections.sort(listWorktypeDto, new Comparator<WorkTypeDto>() {
            @Override
            public int compare(WorkTypeDto workTypeDto2, WorkTypeDto workTypeDto1) {
                return workTypeDto2.getWorkTypeCode().compareTo(workTypeDto1.getWorkTypeCode());
            }
        });

        return listWorktypeDto;
    }

    /**
     * Find not deprecated.
     *
     * @return the list
     */
    public List<WorkTypeDto> findNotDeprecated() {
        // company id
        String companyId = AppContexts.user().companyId();
        return this.workTypeRepo.findNotDeprecated(companyId).stream().map(dom -> WorkTypeDto.fromDomain(dom))
                .collect(Collectors.toList());
    }

    /**
     * Find all by order.
     *
     * @return the list
     */
    public List<WorkTypeInfor> findAllByOrder() {
        // company id
        String companyId = AppContexts.user().companyId();
        List<WorkTypeInfor> lst = this.workTypeRepo.findAllByOrder(companyId);
        return lst;
    }

    /**
     * Find by code.
     *
     * @param workTypeCode the work type code
     * @return the work type dto
     */
    public WorkTypeDto findByCode(String workTypeCode) {
        // company id
        String companyId = AppContexts.user().companyId();
        Optional<WorkType> workTypeOpt = this.workTypeRepo.findByPK(companyId, workTypeCode);
        if (!workTypeOpt.isPresent()) {
            return null;
        }

        WorkType workType = workTypeOpt.get();
        WorkTypeDto workTypeDto = WorkTypeDto.fromDomain(workType);
        // set work type setting
        if (workType.getWorkTypeSetList() != null) {
            List<WorkTypeSetDto> workTypeSetList = workType.getWorkTypeSetList().stream()
                    .map(x -> WorkTypeSetDto.fromDomain(x)).collect(Collectors.toList());
            workTypeDto.setWorkTypeSets(workTypeSetList);
        }

        return workTypeDto;
    }


    /**
     * Find work type language.
     *
     * @param langId the lang id
     * @return the list
     */
    public List<WorkTypeDto> findWorkTypeLanguage(String langId) {
        // company id
        String companyId = AppContexts.user().companyId();
        List<WorkTypeLanguage> workTypeLanguage = workTypeLanguageRepo.findByCIdAndLangId(companyId, langId);
        return workTypeLanguage.stream().map(x -> {
            WorkType wT = new WorkType(companyId, x.getWorkTypeCode(), x.getName(), x.getAbbreviationName());
            return WorkTypeDto.fromDomainWorkTypeLanguage(wT);
        }).collect(Collectors.toList());
    }

    /**
     * Find work type by condition.
     *
     * @return the list
     */
    public List<WorkTypeDto> findWorkTypeByCondition() {
        // company id
        String companyId = AppContexts.user().companyId();
        return this.workTypeRepo.findWorkTypeByCondition(companyId).stream().map(dom -> WorkTypeDto.fromDomain(dom))
                .collect(Collectors.toList());
    }

    /**
     * UKDesign.UniversalK.??????.KDL_???????????????.KDL002_??????????????????.A?????????????????????.??????????????????.?????????????????????
     *
     * @param lstPossible
     * @return
     */
    public List<WorkTypeInfor> getPossibleWorkTypeKDL002(List<String> lstPossible) {
        // company id
        String companyId = AppContexts.user().companyId();
        //<<Public>> ????????????????????????????????????????????????
        List<WorkTypeInfor> lst = new ArrayList<>();
        if (!lstPossible.isEmpty()) {
            lst = this.workTypeRepo.getPossibleWorkTypeAndOrder(companyId, lstPossible);
        } else {
            lst = this.workTypeRepo.getAllWorkTypeNotAbolished(companyId).stream().map(e ->
            {
                val listDetail = e.getWorkTypeSetList().stream().map(i ->
                        new nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetDto(
                                i.getWorkTypeCd().toString(),
                                i.getWorkAtr().value,
                                i.getDigestPublicHd().value,
                                i.getHolidayAtr().value,
                                i.getCountHodiday().value,
                                i.getCloseAtr()!=null? i.getCloseAtr().value:null,
                                i.getSumAbsenseNo(),
                                i.getSumSpHodidayNo(),
                                i.getTimeLeaveWork().value,
                                i.getAttendanceTime().value,
                                i.getGenSubHodiday().value,
                                i.getDayNightTimeAsk().value
                        )
                ).collect(Collectors.toList());
                val rs = new WorkTypeInfor(
                        e.getWorkTypeCode().v(),
                        e.getName().v(),
                        e.getAbbreviationName().v(),
                        e.getSymbolicName().v(),
                        e.getDeprecate().value,
                        e.getMemo().v(),
                        e.getDailyWork().getWorkTypeUnit().value,
                        e.getDailyWork().getOneDay().value,
                        e.getDailyWork().getMorning().value,
                        e.getDailyWork().getAfternoon().value,
                        e.getCalculateMethod().value,
                        e.getDispOrder()
                );
                rs.setWorkTypeSets(listDetail);
                return rs;
            }).collect(Collectors.toList());
        }

        //?????????????????????????????????????????????????????????????????????????????????????????????
//		"????????????
//		0: ???????????????
//		1: ????????????"
        return lst.stream().filter(c -> c.getAbolishAtr() == 0).collect(Collectors.toList());
    }
}
