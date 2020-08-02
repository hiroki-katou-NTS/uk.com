package nts.uk.file.at.app.export.bento;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.BentoReservationSearchConditionDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.DetailOrderInfoDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.OrderInfoDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.PlaceOfWorkInfoDto;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 注文情報を作る
 * @author Hoang Anh Tuan
 */
public class CreateOrderInfoFileQuery {

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private WorkplaceInformationRepository workplaceInformationRepository;

    @Inject
    private WorkLocationRepository workLocationRepository;

    @Inject
    private BentoMenuRepository bentoMenuRepository;

    public OrderInfoDto createOrderInfoFileQuery(DatePeriod period, List<String> workplaceId,
                    List<String> workplaceCode, Optional<BentoReservationSearchConditionDto> totalExtractCondition,
                    Optional<BentoReservationSearchConditionDto> itemExtractCondition, Optional<Integer> frameNo, Optional<String> totalTitle,
                    Optional<String> detailTitle, ReservationClosingTimeFrame reservationClosingTimeFrame){
        Company company = companyRepository.find(AppContexts.user().companyId()).get();


        return null;
    }

    /** 職場又は場所情報と打刻カードを取得 */
    public List<WorkplaceInformation> getWorkPlaceAndStampCard(List<String> workplaceIds, List<String> workLocationCodes, boolean abc){
        List<WorkplaceInformation> result = new ArrayList<>();
        if(workplaceIds.size() > 0)
            return null;

        if(workLocationCodes.size() > 0)
            return null;

        return Collections.EMPTY_LIST;
    }

    /** 会社IDと所属職場履歴IDから所属している職場情報を取得する */
    public List<WorkplaceInformation> getWorkplaceInfoById(List<String> workplaceIds, String companyId, GeneralDate startDate){
        return workplaceInformationRepository.findByBaseDateWkpIds2(companyId, workplaceIds, startDate);
    }

//    /**
//     * 職場IDから職場の情報をすべて取得する
//     * merge to 会社IDと所属職場履歴IDから所属している職場情報を取得する
//     * */
//    public List<WorkplaceInformation> getWorkplaceInfoByIdAndDate(List<String> workplaceIds, String companyId, GeneralDate startDate){
//        return workplaceInformationRepository.findByBaseDateWkpIds2(companyId, workplaceIds, startDate);
//    }

    /** 勤務場所コードから勤務場所を取得 */
    public List<WorkLocation> getWorkplaceInfoByCode(List<String> workLocationCodes){
        return workLocationRepository.findByCodes(AppContexts.user().companyId(), workLocationCodes);
    }

    /** 職位ID（List）と基準日から該当する社員一覧を取得する */
    public List<String> getListEmpId(GeneralDate startDate, List<String> workplaceId){

        return null;
    }

    /** 勤務場所（List）と基準日から所属職場履歴項目を取得する
     * return list employee id, but described as list working history item in EA
     * */
    public List<String> getListWorkHisItem(GeneralDate startDate, List<String> workLocationCode){

        return null;
    }

    /** convert to DTO::職場又は場所情報 */
    public List<PlaceOfWorkInfoDto> convertToPlaceOfWorkInfoDto(List<WorkplaceInformation> workplaceInformations, List<WorkLocation> workLocations){
        List<PlaceOfWorkInfoDto> result = new ArrayList<>();
        if(workplaceInformations == null){
            for(WorkLocation item : workLocations){
                result.add(new PlaceOfWorkInfoDto(item.getWorkLocationCD().v(), item.getWorkLocationName().v()));
            }
        }else{
            for(WorkplaceInformation item : workplaceInformations){
                result.add(new PlaceOfWorkInfoDto(item.getWorkplaceCode().v(), item.getWorkplaceName().v()));
            }
        }
        return result;
    }

    /** convert to 所属会社履歴（社員別） */
    public AffCompanyHistByEmployee convertToDomainObject(){
        return null;
    }

    /** 4. 弁当メニューを取得 */
    public List<BentoMenu> getAllBentoMenu(String companyId, DatePeriod period){
        return bentoMenuRepository.getBentoMenuPeriod(companyId, period);
    }

    /** 5. 注文情報を取得する */
    /** 5.1 */
    public DetailOrderInfoDto exportDetailOrderInfoDto(){
        return null;
    }


    /** 6. 注文合計書を作成 */


    /** 7. 注文明細書を作成 */


}
