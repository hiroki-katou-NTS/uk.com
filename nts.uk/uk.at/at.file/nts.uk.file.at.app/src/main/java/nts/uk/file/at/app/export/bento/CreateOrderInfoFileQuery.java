package nts.uk.file.at.app.export.bento;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

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

    @Inject
    private AffWorkplaceHistoryRepository affWorkplaceHistoryRepository;

    @Inject
    private AffWorkplaceHistoryItemRepository affWorkplaceHistoryItemRepository;

    @Inject
    private AffCompanyHistRepository affCompanyHistRepository;

    @Inject
    private StampCardRepository stampCardRepository;

    @Inject
    private EmployeeDataMngInfoRepository employeeDataMngInfoRepository;

    @Inject
    private SyEmployeePub syEmployeePub;

    public OrderInfoDto createOrderInfoFileQuery(DatePeriod period, List<String> workplaceId,
                                                 List<String> workplaceCode, Optional<BentoReservationSearchConditionDto> totalExtractCondition,
                                                 Optional<BentoReservationSearchConditionDto> itemExtractCondition, Optional<Integer> frameNo, Optional<String> totalTitle,
                                                 Optional<String> detailTitle, ReservationClosingTimeFrame reservationClosingTimeFrame){
        // 1. [RQ622]会社IDから会社情報を取得する
        Company company = companyRepository.find(AppContexts.user().companyId()).get();


        return null;
    }



    /** 2. 職場又は場所情報と打刻カードを取得 */
    public List<WorkplaceInformation> getWorkPlaceAndStampCard(List<String> workplaceIds, List<String> workLocationCodes, boolean isCheckedEmpInfo, DatePeriod period){
        //List<WorkplaceInformation> result = new ArrayList<>();
        List<String> sIds = new ArrayList<>();
        List<WorkLocation> workLocations = new ArrayList<>();
        List<WorkplaceInformation> workplaceInformations = new ArrayList<>();
        List<EmployeeBasicInfoExport> empBasicInfoExports = new ArrayList<>();
        if(workplaceIds.size() > 0){
            workplaceInformations = getWorkplaceInfoById(workplaceIds, AppContexts.user().companyId());
            sIds = getListEmpIdInWorkPlace(workplaceIds, period);

        }else if(workLocationCodes.size() > 0){
            workLocations = getWorkplaceInfoByCode(workLocationCodes);
            List<AffWorkplaceHistoryItem> affWorkplaceHistoryItems = getListWorkHisItem(period.start(), workLocationCodes);
            sIds = affWorkplaceHistoryItems.stream().map(AffWorkplaceHistoryItem::getEmployeeId).collect(Collectors.toList());
        }

        Map<String, String> map = getStampCardFromSID(sIds);
        if(isCheckedEmpInfo) {
            empBasicInfoExports = getBasicInfo(sIds);

        }

        List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos = convertToPlaceOfWorkInfoDto(workplaceInformations,workLocations);

        return Collections.EMPTY_LIST;
    }

    /** 会社IDと所属職場履歴IDから所属している職場情報を取得する */
    public List<WorkplaceInformation> getWorkplaceInfoById(List<String> workplaceIds, String companyId){
        return workplaceInformationRepository.findByWkpIds(companyId, workplaceIds);
    }

    /** 期間内に特定の職場（List）に所属している社員一覧を取得 */
    public List<String> getListEmpIdInWorkPlace(List<String> workplaceId, DatePeriod period){

        // Use Set to remove duplicate sID
        Set<String> sIdFromAffWorkplaceHistorySet = new LinkedHashSet<>();
        // ドメインモデル「所属職場履歴」、「所属職場履歴項目」から、指定期間に存在する所属職場の社員IDを取得する
        sIdFromAffWorkplaceHistorySet.addAll(affWorkplaceHistoryRepository.getByLstWplIdAndPeriod(workplaceId, period.start(), period.end()));
        sIdFromAffWorkplaceHistorySet.addAll(affWorkplaceHistoryRepository.getByLstWplIdAndPeriod(workplaceId, period.start(), period.end())) ;
        //社員IDの重複は除く
        //sIdFromAffWorkplaceHistory = sIdFromAffWorkplaceHistory.stream().distinct().collect(Collectors.toList());
        // ドメインモデル「所属会社履歴（社員別）」をすべて取得する
        List<String> sIdResult =  affCompanyHistRepository.getLstSidByLstSidAndPeriod( new ArrayList<>(sIdFromAffWorkplaceHistorySet), period);
        //return
        return sIdResult;
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

    /**
     * 勤務場所（List）と基準日から所属職場履歴項目を取得する
     * */
    public List<AffWorkplaceHistoryItem> getListWorkHisItem(GeneralDate startDate, List<String> workLocationCode){
        // ドメインモデル「所属職場履歴項目」を取得
        List<String> sIdFromAffWorkplaceHistoryItems = new ArrayList<>(); //= affWorkplaceHistoryItemRepository.getAffWkpHistItemByListWklocationId(workLocationCode);
        List<AffWorkplaceHistory> affWorkplaceHistories = affWorkplaceHistoryRepository.getByListSid(sIdFromAffWorkplaceHistoryItems);
        // ドメインモデル「所属職場履歴」を取得
        List<String> hisIDs = new ArrayList<>();
        for(AffWorkplaceHistory item : affWorkplaceHistories){
            hisIDs.addAll(item.getHistoryItems().stream()
                    .filter(hItem -> hItem.start().after(startDate) | hItem.end().before(startDate))
                    .map(DateHistoryItem::identifier)
                    .collect(Collectors.toList()));
        }
        return affWorkplaceHistoryItemRepository.findByHistIds(hisIDs);
    }

    /** List<社員ID＞から打刻カードを全て取得する */
    public Map<String, String> getStampCardFromSID(List<String> sIds){
        return stampCardRepository.getLstStampCardByLstSid(sIds).stream()
                                                    .sorted(Comparator.comparing(StampCard::getRegisterDate).reversed())
                                                    .collect(Collectors.toMap(item -> item.getEmployeeId(), item -> item.getStampNumber().v()));
    }


    /** 社員ID(List)から個人社員基本情報を取得 */
    public List<EmployeeBasicInfoExport> getBasicInfo(List<String> sIds){
        List<String> personIds = new ArrayList<>();
        //ドメインモデル「社員データ管理情報」を取得する
        List<EmployeeDataMngInfo> employeeDataMngInfos = employeeDataMngInfoRepository.findByListEmployeeId(sIds);
        //ドメインモデル「社員データ管理情報」が取得できたかどうかチェックする
        if(employeeDataMngInfos.size() > 0 ){
            //ドメインモデル「所属会社履歴（社員別）」を取得する
            personIds = employeeDataMngInfos.stream()
                    .map(EmployeeDataMngInfo::getPersonId).collect(Collectors.toList());
        }

        //終了状態：成功
        if(personIds.size() > 0){
            return syEmployeePub.findBySIds(employeeDataMngInfos.stream()
                    .map(EmployeeDataMngInfo::getEmployeeId).collect(Collectors.toList()));
        }

        return Collections.EMPTY_LIST;
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
