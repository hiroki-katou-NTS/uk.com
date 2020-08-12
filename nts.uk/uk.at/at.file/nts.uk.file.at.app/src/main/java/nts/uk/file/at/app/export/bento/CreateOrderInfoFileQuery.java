package nts.uk.file.at.app.export.bento;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.*;
import nts.uk.ctx.at.record.app.find.reservation.bento.query.ListBentoResevationQuery;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
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

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 注文情報を作る
 * @author Hoang Anh Tuan
 */
@Stateless
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
                                                 List<String> workplaceCodes, Optional<BentoReservationSearchConditionDto> totalExtractCondition,
                                                 Optional<BentoReservationSearchConditionDto> itemExtractCondition, Optional<Integer> frameNo, Optional<String> totalTitle,
                                                 Optional<String> detailTitle, ReservationClosingTimeFrame reservationClosingTimeFrame){
        OrderInfoDto result = new OrderInfoDto();
        // 1. [RQ622]会社IDから会社情報を取得する
        Company company = companyRepository.find(AppContexts.user().companyId()).get();

        // 2. 職場又は場所情報と打刻カードを取得
        boolean isCheckedEmpInfo = detailTitle.isPresent();
        Object[] condition = getWorkPlaceAndStampCard(workplaceId, workplaceCodes, isCheckedEmpInfo, period);
        Map<String, String> map = (Map<String, String>) condition[1];
        List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos = (List<PlaceOfWorkInfoDto>) condition[3];
        // 3. 弁当予約を取得する
        List<BentoReservation> bentoReservations = new ArrayList<>();
        if(totalExtractCondition.isPresent())
            bentoReservations = getListBentoResevation(totalExtractCondition.get(), period,new ArrayList<>(map.values()), workplaceCodes, reservationClosingTimeFrame);
        else
            bentoReservations = getListBentoResevation(itemExtractCondition.get(), period,new ArrayList<>(map.values()), workplaceCodes, reservationClosingTimeFrame);
        if(bentoReservations.size() < 1)
            throw new BusinessException("Msg_1617");
        //4.
        List<BentoMenu> bentoMenuList = getAllBentoMenu(company.getCompanyId(), period);

        //5.
        List<TotalOrderInfoDto> totalOrderInfoDtos = exportTotalOrderInfo(company.getCompanyId(),period, bentoReservations, placeOfWorkInfoDtos);
        List<DetailOrderInfoDto> detailOrderInfoDtos = exportDetailOrderInfo();

        return result;
    }

    /** 2. 職場又は場所情報と打刻カードを取得 */
    public Object[] getWorkPlaceAndStampCard(List<String> workplaceIds, List<String> workLocationCodes, boolean isCheckedEmpInfo, DatePeriod period) {
        //List<WorkplaceInformation> result = new ArrayList<>();
        Object[] result = new Object[4];
        List<String> sIds = new ArrayList<>();
        List<BentoReservationInfoForEmpDto> bentoReservationInfoForEmpDtos = new ArrayList<>();
        List<WorkLocation> workLocations = new ArrayList<>();
        List<WorkplaceInformation> workplaceInformations = new ArrayList<>();
        List<EmployeeBasicInfoExport> empBasicInfoExports = new ArrayList<>();
        if (workplaceIds.size() > 0) {
            workplaceInformations = getWorkplaceInfoById(workplaceIds, AppContexts.user().companyId());
            sIds = getListEmpIdInWorkPlace(workplaceIds, period);
            result[0] = workplaceInformations;

        } else if (workLocationCodes.size() > 0) {
            workLocations = getWorkplaceInfoByCode(workLocationCodes);
            List<AffWorkplaceHistoryItem> affWorkplaceHistoryItems = getListWorkHisItem(period.start(), workLocationCodes);
            sIds = affWorkplaceHistoryItems.stream().map(AffWorkplaceHistoryItem::getEmployeeId).collect(Collectors.toList());
            result[0] = workLocations;
        }

        Map<String, String> mapStampCardInfo = getStampCardFromSID(sIds);
        result[1] = mapStampCardInfo;

        if (isCheckedEmpInfo) {
            empBasicInfoExports = getBasicInfo(sIds);
            for (int i = 0; i < empBasicInfoExports.size(); ++i) {
                String stampCardNo = mapStampCardInfo.get(empBasicInfoExports.get(i).getEmployeeId());
                if (stampCardNo == null | "".equals(stampCardNo))
                    continue;
                bentoReservationInfoForEmpDtos.add(createBentoReservationInfoForEmpDto(empBasicInfoExports.get(i).getEmployeeId(),
                        stampCardNo, empBasicInfoExports.get(i), 0));
            }
        }

        result[2] = bentoReservationInfoForEmpDtos;
        List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos = convertToPlaceOfWorkInfoDto(workplaceInformations, workLocations);
        result[3] = placeOfWorkInfoDtos;
        return result;
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

    /** 勤務場所コードから勤務場所を取得 */
    public List<WorkLocation> getWorkplaceInfoByCode(List<String> workLocationCodes){
        return workLocationRepository.findByCodes(AppContexts.user().companyId(), workLocationCodes);
    }

    /**
     * 勤務場所（List）と基準日から所属職場履歴項目を取得する
     * */
    public List<AffWorkplaceHistoryItem> getListWorkHisItem(GeneralDate startDate, List<String> workLocationCode){
        // ドメインモデル「所属職場履歴項目」を取得
        List<String> sIdFromAffWorkplaceHistoryItems = affWorkplaceHistoryItemRepository.getSIDByListWklocationCode(workLocationCode);
        List<AffWorkplaceHistory> affWorkplaceHistories = affWorkplaceHistoryRepository.getByListSid(sIdFromAffWorkplaceHistoryItems);

        // ドメインモデル「所属職場履歴」を取得
        List<String> hisIDs = new ArrayList<>();
        for(AffWorkplaceHistory item : affWorkplaceHistories){
            hisIDs.addAll(item.getHistoryItems().stream()
                    .filter(hItem -> hItem.start().before(startDate) & hItem.end().after(startDate))
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

    /** 3. 弁当予約を取得する */
    public List<BentoReservation> getListBentoResevation(BentoReservationSearchConditionDto searchCondition,
                                                         DatePeriod period, List<String> stampCardNo,
                                                         List<String> workLocationCodes, ReservationClosingTimeFrame reservationClosingTimeFrame){
        List<ReservationRegisterInfo> reservationRegisterInfoList = stampCardNo.stream().map(x -> new ReservationRegisterInfo(x)).collect(Collectors.toList());
        List<WorkLocationCode> workLocationCodeList = workLocationCodes.stream().map(x -> new WorkLocationCode(x)).collect(Collectors.toList());
        return new ListBentoResevationQuery().getListBentoResevationQuery(searchCondition, period, reservationRegisterInfoList, workLocationCodeList, reservationClosingTimeFrame);
    }

    /** 4. 弁当メニューを取得 */
    public List<BentoMenu> getAllBentoMenu(String companyId, DatePeriod period){
        return bentoMenuRepository.getBentoMenuPeriod(companyId, period);
    }

    /** 5. 注文情報を取得する */
    /** 5.2 */
    public List<DetailOrderInfoDto> exportDetailOrderInfo(List<BentoReservation> reservations, String companiID ,
                                                          List<BentoReservationInfoForEmpDto> bentoReservationInfoForEmpDtos){
        Map<ReservationDate, List<BentoReservation>> map = reservations.stream().collect(
                                Collectors.groupingBy(
                                        BentoReservation::getReservationDate,
                                        Collectors.toList()));
        return null;
    }

    /**
     * an algorithm of super complexity,
     * @param bentoReservationInfoForEmpDtos
     * @param reservations
     * @param companyID
     * @return
     */
    public List<BentoReservedInfoDto> createBentoReservedInfoDto(List<BentoReservationInfoForEmpDto> bentoReservationInfoForEmpDtos, List<BentoReservation> reservations,
                                                                 String companyID){
        List<BentoReservedInfoDto> result = new ArrayList<>();
        Map<Bento, List<BentoReservationInfoForEmpDto>> map = new HashMap<>();
        Iterator reservationInfoForEmpDtoIT = bentoReservationInfoForEmpDtos.iterator();
        while (reservationInfoForEmpDtoIT.hasNext()){
            BentoReservationInfoForEmpDto item = (BentoReservationInfoForEmpDto) reservationInfoForEmpDtoIT.next();
            List<BentoReservation> handlerReservation = new ArrayList<>();
            Iterator it = reservations.iterator();
            while (it.hasNext()){
                BentoReservation temp = (BentoReservation) it.next();
                if(temp.getRegisterInfor().equals(item.getStampCardNo())){
                    handlerReservation.add(temp);
                    it.remove();
                }
            }
            for(BentoReservation reservation : handlerReservation){
                for(BentoReservationDetail detail : reservation.getBentoReservationDetails()){
                    Bento bento = bentoMenuRepository.getBento(companyID, reservation.getReservationDate().getDate(), detail.getFrameNo());
                    if(map.get(bento) == null)
                        map.put(bento,new ArrayList<>(Arrays.asList(item)));
                    else{
                        List<BentoReservationInfoForEmpDto> temp = map.get(bento);
                        temp.add(item);
                        map.put(bento,temp);
                    }
                }
            }
            if(handlerReservation.size() > 0)
                reservationInfoForEmpDtoIT.remove();
        }
        for(Map.Entry me : map.entrySet()){
            Bento bentoTemp = (Bento) me.getKey();
            List<BentoReservationInfoForEmpDto> listTemp = (List<BentoReservationInfoForEmpDto>) me.getValue();
            result.add(new BentoReservedInfoDto(
                    bentoTemp.getName().v(),bentoTemp.getFrameNo(),listTemp
            ));
        }
        return result;
    }

    public BentoReservationInfoForEmpDto createBentoReservationInfoForEmpDto(String sid, String stampCardNo, EmployeeBasicInfoExport employeeBasicInfoExport,
                                                                             int quantity){
        return new BentoReservationInfoForEmpDto(stampCardNo, quantity,
                sid,employeeBasicInfoExport.getEmployeeCode(), employeeBasicInfoExport.getPName());
    }

    /** 5.1 */
    public List<TotalOrderInfoDto> exportTotalOrderInfo(String companyId, DatePeriod period, List<BentoReservation> reservations, List<PlaceOfWorkInfoDto> workInfoDtos){
        List<BentoMenu> bentoMenuLst = getAllBentoMenu( companyId, period);
        List<BentoReservationDetail> reservationDetails = new ArrayList<>();
        List<TotalOrderInfoDto> result = new ArrayList<>();
        String closedName = "";

        if(bentoMenuLst.size() < 1)
            throw new BusinessException("Msg_1640");

        for(BentoReservation item : reservations){
            int totalFee = 0;
            reservationDetails.addAll(item.getBentoReservationDetails());
            BentoMenu bentoMenuList = bentoMenuRepository.getBentoMenu(companyId, item.getReservationDate().getDate());
                List<BentoTotalDto> bentoTotalDtoLst = new ArrayList<>();

                for (Bento bento : bentoMenuList.getMenu())
                    bentoTotalDtoLst.add(createBentoTotalDto(bento, reservationDetails, totalFee));

            result.add(new TotalOrderInfoDto(
                    item.getReservationDate().getDate(), item.getRegisterInfor().getReservationCardNo(), totalFee,
                    bentoTotalDtoLst,closedName,workInfoDtos
            ));
        }
        return result;
    }

    public BentoTotalDto createBentoTotalDto(Bento bento, List<BentoReservationDetail> reservation, int totalFee){
        int quantity = 0;
        Iterator iterator = reservation.iterator();
        while (iterator.hasNext()){
            BentoReservationDetail temp = (BentoReservationDetail) iterator.next();
            if(temp.getFrameNo() == bento.getFrameNo()){
                quantity += temp.getBentoCount().v();
                iterator.remove();
            }
        }
        int amount = (bento.getAmount1().v() + bento.getAmount2().v());
        totalFee += quantity * totalFee;
        return new BentoTotalDto(bento.getUnit().v(),bento.getName().v(), quantity,
                bento.getFrameNo(),amount);
    }


    /** 6. 注文合計書を作成 */


    /** 7. 注文明細書を作成 */


}
