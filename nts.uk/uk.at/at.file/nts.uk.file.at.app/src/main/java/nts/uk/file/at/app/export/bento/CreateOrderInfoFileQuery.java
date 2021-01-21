package nts.uk.file.at.app.export.bento;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.*;
import nts.uk.ctx.at.record.app.find.reservation.bento.query.ListBentoResevationQuery;
import nts.uk.ctx.at.record.app.query.stamp.GetStampCardQuery;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoItemByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.ctx.bs.employee.pub.employee.export.PersonEmpBasicInfoPub;
import nts.uk.ctx.bs.employee.pub.employee.export.dto.PersonEmpBasicInfoDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 注文情報を作る
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
    private BentoReservationRepository bentoReservationRepository;

    @Inject
    private ListBentoResevationQuery query;

    @Inject
    GetStampCardQuery getStampCardQuery;

    @Inject
    private PersonEmpBasicInfoPub personEmpBasicInfoPub;

    public OrderInfoDto createOrderInfoFileQuery(DatePeriod period, List<String> workplaceId,
                                                 List<String> workLocationCodes, Optional<BentoReservationSearchConditionDto> totalExtractCondition,
                                                 Optional<BentoReservationSearchConditionDto> itemExtractCondition, Optional<Integer> frameNo, Optional<String> totalTitle,
                                                 Optional<String> detailTitle, ReservationClosingTimeFrame reservationClosingTimeFrame){
        if (!totalTitle.isPresent() & !detailTitle.isPresent())
            throw new BusinessException("Msg_1642");
        if(CollectionUtil.isEmpty(workplaceId) & CollectionUtil.isEmpty(workLocationCodes))
            throw new BusinessException("Msg_1856");
        OrderInfoDto result = new OrderInfoDto();
        // 1. [RQ622]会社IDから会社情報を取得する
        String companyId = AppContexts.user().companyId();
        Optional<Company> company = companyRepository.find(companyId);
        String companyName = company.isPresent() ? company.get().getCompanyName().v() : "";
        // 2. 職場又は場所情報と打刻カードを取得
        boolean isCheckedEmpInfo = detailTitle.isPresent();
        Object[] condition = getWorkPlaceAndStampCard(workplaceId, workLocationCodes, isCheckedEmpInfo, period, companyId);
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) condition[1];
        @SuppressWarnings("unchecked")
        List<BentoReservationInfoForEmpDto> bentoReservationInfoForEmpDtos = (List<BentoReservationInfoForEmpDto>) condition[2];
        @SuppressWarnings("unchecked")
        List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos = (List<PlaceOfWorkInfoDto>) condition[3];
        // 3. 弁当予約を取得する
        List<BentoReservation> bentoReservationsTotal = new ArrayList<>();
        List<BentoReservation> bentoReservationsDetail = new ArrayList<>();
        if (totalExtractCondition.isPresent())
            bentoReservationsTotal = getListBentoResevation(totalExtractCondition.get(), period, new ArrayList<>(map.values()), workLocationCodes, reservationClosingTimeFrame);
        if (detailTitle.isPresent()) {
            if (frameNo.isPresent())
                bentoReservationsDetail = getListBentoResevation(frameNo.get(), period, new ArrayList<>(map.values()), workLocationCodes, reservationClosingTimeFrame);
            else if(itemExtractCondition.isPresent())
                bentoReservationsDetail = getListBentoResevation(itemExtractCondition.get(), period, new ArrayList<>(map.values()), workLocationCodes, reservationClosingTimeFrame);
        }
        if (CollectionUtil.isEmpty(bentoReservationsTotal) & CollectionUtil.isEmpty(bentoReservationsDetail))
        	if (totalTitle.isPresent()) {
				throw new BusinessException("Msg_6");
			} else {
				throw new BusinessException("Msg_1617");
			}
        //4.
        List<BentoMenu> bentoMenuList = getAllBentoMenu(companyId, period);
        if (CollectionUtil.isEmpty(bentoMenuList))
            throw new BusinessException("Msg_1640");
        //5.
        Optional<String> closingName = Optional.empty();
        if(ReservationClosingTimeFrame.FRAME1.equals(reservationClosingTimeFrame))
            closingName = bentoMenuList.stream().findFirst().map(i -> i.getClosingTime().getClosingTime1().getReservationTimeName().v());
        else if (ReservationClosingTimeFrame.FRAME2.equals(reservationClosingTimeFrame))
            closingName = bentoMenuList.stream().filter(i -> i.getClosingTime().getClosingTime2().isPresent())
                    .findFirst().map(i -> i.getClosingTime().getClosingTime2().get().getReservationTimeName().v());

        Optional<WorkLocationCode> workLocationCode = CollectionUtil.isEmpty(workLocationCodes) ? Optional.empty() : Optional.of(new WorkLocationCode(workLocationCodes.get(0)));
        List<TotalOrderInfoDto> totalOrderInfoDtos = exportTotalOrderInfo(companyId, bentoReservationsTotal, placeOfWorkInfoDtos, frameNo, closingName, reservationClosingTimeFrame, workLocationCode);
        List<DetailOrderInfoDto> detailOrderInfoDtos = exportDetailOrderInfo(bentoReservationsDetail, companyId, placeOfWorkInfoDtos, bentoReservationInfoForEmpDtos, frameNo, closingName);
        result.setCompanyName(companyName);
        result.setDetailOrderInfoDtoList(detailOrderInfoDtos);
        result.setDetailTittle(isCheckedEmpInfo ? detailTitle.get() : "");
        result.setTotalOrderInfoDtoList(totalOrderInfoDtos);
        result.setTotalTittle(totalTitle.orElse(""));
        return result;
    }

    /** 2. 職場又は場所情報と打刻カードを取得 */
    private Object[] getWorkPlaceAndStampCard(List<String> workplaceIds, List<String> workLocationCodes, boolean isCheckedEmpInfo, DatePeriod period, String companyId) {
        Object[] result = new Object[4];
        List<String> sIds = new ArrayList<>();
        Set<BentoReservationInfoForEmpDto> bentoReservationInfoForEmpDtos = new HashSet<>();
        List<WorkLocation> workLocations = new ArrayList<>();
        List<WorkplaceInformation> workplaceInformations = new ArrayList<>();
        List<PersonEmpBasicInfoDto> personEmpBasicInfoDtos;
        if (!CollectionUtil.isEmpty(workplaceIds)) {
            workplaceInformations = getWorkplaceInfoById(workplaceIds, companyId);
            sIds = getListEmpIdInWorkPlace(workplaceIds, period);
            result[0] = workplaceInformations;
        } else if (!CollectionUtil.isEmpty(workLocationCodes)) {
            workLocations = getWorkplaceInfoByCode(workLocationCodes, companyId);
            List<AffWorkplaceHistoryItem> affWorkplaceHistoryItems = getListWorkHisItem(period.start(), workLocationCodes);
            sIds = affWorkplaceHistoryItems.stream().map(AffWorkplaceHistoryItem::getEmployeeId).collect(Collectors.toList());
            result[0] = workLocations;
        }
        Map<String, String> mapStampCardInfo = getStampCardFromSID(sIds);
        result[1] = mapStampCardInfo;
        if (isCheckedEmpInfo) {
            personEmpBasicInfoDtos = getBasicInfo(sIds);
            for (PersonEmpBasicInfoDto personEmpBasicInfoDto : personEmpBasicInfoDtos) {
                String stampCardNo = mapStampCardInfo.get(personEmpBasicInfoDto.getEmployeeId());
                if (stampCardNo == null | "".equals(stampCardNo))
                    continue;
                bentoReservationInfoForEmpDtos.add(createBentoReservationInfoForEmpDto(personEmpBasicInfoDto.getEmployeeId(),
                        stampCardNo, personEmpBasicInfoDto));
            }
        }
        result[2] = new ArrayList<>(bentoReservationInfoForEmpDtos);
        List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos = convertToPlaceOfWorkInfoDto(workplaceInformations, workLocations);
        result[3] = placeOfWorkInfoDtos;
        return result;
    }

    /** 会社IDと所属職場履歴IDから所属している職場情報を取得する */
    private List<WorkplaceInformation> getWorkplaceInfoById(List<String> workplaceIds, String companyId){
        return workplaceInformationRepository.findByWkpIds(companyId, workplaceIds);
    }

    /** 期間内に特定の職場（List）に所属している社員一覧を取得 */
    private List<String> getListEmpIdInWorkPlace(List<String> workplaceId, DatePeriod period){
        // Use Set to remove duplicate sID
        Set<String> sIdFromAffWorkplaceHistorySet = new LinkedHashSet<>();

        // ドメインモデル「所属職場履歴」、「所属職場履歴項目」から、指定期間に存在する所属職場の社員IDを取得する
        sIdFromAffWorkplaceHistorySet.addAll(affWorkplaceHistoryRepository.getByLstWplIdAndPeriod(workplaceId, period.start(), period.end()));

        //社員IDの重複は除く
        // ドメインモデル「所属会社履歴（社員別）」をすべて取得する
        return  affCompanyHistRepository.getLstSidByLstSidAndPeriod( new ArrayList<>(sIdFromAffWorkplaceHistorySet), period);
    }

    /** 勤務場所コードから勤務場所を取得 */
    private List<WorkLocation> getWorkplaceInfoByCode(List<String> workLocationCodes, String companyId){
        return workLocationRepository.findByCodes(companyId, workLocationCodes);
    }

    /**
     * 勤務場所（List）と基準日から所属職場履歴項目を取得する
     * */
    private List<AffWorkplaceHistoryItem> getListWorkHisItem(GeneralDate startDate, List<String> workLocationCode){
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
    private Map<String, String> getStampCardFromSID(List<String> sIds){
        return stampCardRepository.getLstStampCardByLstSid(sIds).stream()
                                                    .sorted(Comparator.comparing(StampCard::getRegisterDate).reversed())
                                                    .collect(Collectors.toMap(StampCard::getEmployeeId, item -> item.getStampNumber().v(), (oldVal, newVal) -> oldVal));
    }

    /** 社員ID(List)から個人社員基本情報を取得 */
    private List<PersonEmpBasicInfoDto> getBasicInfo(List<String> sIds){
        return personEmpBasicInfoPub.getPerEmpBasicInfo(sIds);
    }

    /** convert to DTO::職場又は場所情報 */
    private List<PlaceOfWorkInfoDto> convertToPlaceOfWorkInfoDto(List<WorkplaceInformation> workplaceInformations, List<WorkLocation> workLocations){
        List<PlaceOfWorkInfoDto> result = new ArrayList<>();
        if(CollectionUtil.isEmpty(workplaceInformations))
            for(WorkLocation item : workLocations)
                result.add(new PlaceOfWorkInfoDto(item.getWorkLocationCD().v(), item.getWorkLocationName().v()));
        else
            for(WorkplaceInformation item : workplaceInformations)
                result.add(new PlaceOfWorkInfoDto(item.getWorkplaceCode().v(), item.getWorkplaceName().v()));

        if(result.size() > 2)
            return new ArrayList<>(Arrays.asList(result.get(0), result.get(result.size() - 1)));
        return result;
    }

    /** 3. 弁当予約を取得する */
    private List<BentoReservation> getListBentoResevation(BentoReservationSearchConditionDto searchCondition,
                                                         DatePeriod period, List<String> stampCardNo,
                                                         List<String> workLocationCodes, ReservationClosingTimeFrame reservationClosingTimeFrame){
        List<ReservationRegisterInfo> reservationRegisterInfoList = stampCardNo.stream().map(ReservationRegisterInfo::new).collect(Collectors.toList());
        List<WorkLocationCode> workLocationCodeList = CollectionUtil.isEmpty(workLocationCodes) ? Collections.EMPTY_LIST
                : workLocationCodes.stream().map(WorkLocationCode::new).collect(Collectors.toList());
        return query.getListBentoResevationQuery(searchCondition, period, reservationRegisterInfoList, workLocationCodeList, reservationClosingTimeFrame);
    }

    private List<BentoReservation> getListBentoResevation(int frameNo,
                                                          DatePeriod period, List<String> stampCardNo,
                                                          List<String> workLocationCodes, ReservationClosingTimeFrame reservationClosingTimeFrame){
        List<ReservationRegisterInfo> reservationRegisterInfoList = stampCardNo.stream().map(ReservationRegisterInfo::new).collect(Collectors.toList());
        List<WorkLocationCode> workLocationCodeList = workLocationCodes.stream().map(WorkLocationCode::new).collect(Collectors.toList());
        return bentoReservationRepository.getAllReservationOfBento(frameNo, reservationRegisterInfoList, period, reservationClosingTimeFrame, workLocationCodeList);
    }

    /** 4. 弁当メニューを取得 */
    private List<BentoMenu> getAllBentoMenu(String companyId, DatePeriod period){
        return bentoMenuRepository.getBentoMenuPeriod(companyId, period);
    }

    /** 5. 注文情報を取得する */
    /** 5.2 */

    private List<DetailOrderInfoDto> exportDetailOrderInfo(List<BentoReservation> reservations, String companyID ,List<PlaceOfWorkInfoDto> placeOfWorkInfoDtos,
                                                          List<BentoReservationInfoForEmpDto> bentoReservationInfoForEmpDtos, Optional<Integer> frameNo, Optional<String> closingTimeName){
        List<DetailOrderInfoDto> result = new ArrayList<>();
        String closedName = closingTimeName.orElse("");
        Map<String, List<BentoReservationInfoForEmpDto>> infoForEmpDtoMap = bentoReservationInfoForEmpDtos.stream()
                .collect(Collectors.groupingBy(BentoReservationInfoForEmpDto::getStampCardNo));
        if(frameNo.isPresent()){
            Iterator<BentoReservation> it = reservations.iterator();
            while (it.hasNext()){
                Iterator<BentoReservationDetail> detailIT = it.next().getBentoReservationDetails().iterator();
                while (detailIT.hasNext()){
                    if(detailIT.next().getFrameNo() == frameNo.get())
                        continue;
                    detailIT.remove();
                }
            }
        }

        Map<GeneralDate, Map<String ,List<BentoReservation>>> map = reservations.stream().collect(
                                Collectors.groupingBy(
                                        r -> r.getReservationDate().getDate(),
                                        Collectors.groupingBy(item -> item.getRegisterInfor().getReservationCardNo(),
                                                Collectors.toList())
                                        )
        );
        for(Map.Entry<GeneralDate, Map<String ,List<BentoReservation>>> me : map.entrySet()){
            GeneralDate reservationDate = me.getKey();
            Map<String ,List<BentoReservation>> reservationMap = me.getValue();
            for(Map.Entry<String ,List<BentoReservation>> meItem: reservationMap.entrySet()){
                String registerInfo = meItem.getKey();
                List<BentoReservation> reservationList = meItem.getValue();
                List<BentoReservedInfoDto> bentoReservedInfoDtos = createBentoReservedInfoDto(infoForEmpDtoMap,reservationList,registerInfo, companyID);
                result.add(new DetailOrderInfoDto(bentoReservedInfoDtos,reservationDate,registerInfo,
                        closedName,placeOfWorkInfoDtos));
            }
        }
        return result.stream().sorted(Comparator.comparing(DetailOrderInfoDto::getReservationDate)).collect(Collectors.toList());
    }

    /**
     * an algorithm of super complexity,
     * @param infoForEmpDtoMap
     * @param handlerReservation
     * @param stampCardNo
     * @param companyID
     * @return
     */
    private List<BentoReservedInfoDto> createBentoReservedInfoDto(Map<String, List<BentoReservationInfoForEmpDto>> infoForEmpDtoMap, List<BentoReservation> handlerReservation, String stampCardNo,
                                                                 String companyID){
        List<BentoReservedInfoDto> result = new ArrayList<>();
        Map<Bento, List<BentoReservationInfoForEmpDto>> map = new HashMap<>();
        List<BentoReservationInfoForEmpDto> items = infoForEmpDtoMap.get(stampCardNo);
        if(!CollectionUtil.isEmpty(items)){
            BentoReservationInfoForEmpDto item = items.get(0);
            for(BentoReservation reservation : handlerReservation){
                for(BentoReservationDetail detail : reservation.getBentoReservationDetails()){
                    Bento bento = bentoMenuRepository.getBento(companyID, reservation.getReservationDate().getDate(), detail.getFrameNo());

                    // Bento is deleted
                    if (bento == null) continue;

                    if(map.get(bento) == null){
                        map.put(bento,new ArrayList<BentoReservationInfoForEmpDto>(){{
                            add(BentoReservationInfoForEmpDto.changeQuantity(item, detail.getBentoCount().v()));
                        }});
                    }else{
                        List<BentoReservationInfoForEmpDto> temp = map.get(bento);
                        temp.add(BentoReservationInfoForEmpDto.changeQuantity(item, detail.getBentoCount().v()));
                        map.put(bento,temp);
                    }
                }
            }
            for(Map.Entry<Bento, List<BentoReservationInfoForEmpDto>> me : map.entrySet()){
                Bento bentoTemp = me.getKey();
                List<BentoReservationInfoForEmpDto> listTemp = me.getValue().stream()
                        .sorted(Comparator.comparing(BentoReservationInfoForEmpDto::getEmpCode)).collect(Collectors.toList());
                result.add(new BentoReservedInfoDto(
                        bentoTemp.getName().v(),bentoTemp.getFrameNo(), bentoTemp.getUnit().v(),listTemp
                ));
            }
            return result.stream()
                    .sorted(Comparator.comparing(BentoReservedInfoDto::getFrameNo))
                    .collect(Collectors.toList());
        }
        return result;
    }

    private BentoReservationInfoForEmpDto createBentoReservationInfoForEmpDto(String sid, String stampCardNo, PersonEmpBasicInfoDto personEmpBasicInfoDto){
        final int QUANTITY = 0;
        return new BentoReservationInfoForEmpDto(stampCardNo, QUANTITY,
                sid,personEmpBasicInfoDto.getEmployeeCode(), personEmpBasicInfoDto.getBusinessName());
    }

    /** 5.1 */
    private List<TotalOrderInfoDto> exportTotalOrderInfo(String companyId, List<BentoReservation> reservations,
                                                        List<PlaceOfWorkInfoDto> workInfoDtos, Optional<Integer> frameNo, Optional<String> closingTimeName,
                                                         ReservationClosingTimeFrame reservationClosingTimeFrame,
                                                         Optional<WorkLocationCode> workLocationCode){
        List<BentoReservationDetail> reservationDetails = new ArrayList<>();
        List<TotalOrderInfoDto> result = new ArrayList<>();
        String closedName = closingTimeName.orElse("");
        /*if(frameNo.isPresent()){
            for(BentoReservation item : reservations){
                int totalFee = 0;
                reservationDetails.addAll(item.getBentoReservationDetails());
                Bento bento = bentoMenuRepository.getBento(companyId, item.getReservationDate().getDate(), frameNo.get());
                if (bento == null) continue;
                List<BentoTotalDto> bentoTotalDtoLst = new ArrayList<>();
                    BentoTotalDto bentoTotalDto = createBentoTotalDto(bento, reservationDetails);
                    bentoTotalDtoLst.add(bentoTotalDto);
                    totalFee += bentoTotalDto.getAmount() * bentoTotalDto.getQuantity();
                result.add(new TotalOrderInfoDto(
                        item.getReservationDate().getDate(), item.getRegisterInfor().getReservationCardNo(), totalFee,
                        bentoTotalDtoLst,closedName,workInfoDtos
                ));
            }
        }else{*/
        for(BentoReservation item : reservations){
            int totalFee = 0;
            reservationDetails.addAll(item.getBentoReservationDetails());
            BentoMenu bentoMenuList = bentoMenuRepository.getBentoMenu(companyId, item.getReservationDate().getDate());
            if (bentoMenuList == null) {
            	continue;
			}
            List<BentoTotalDto> bentoTotalDtoLst = new ArrayList<>();
            BentoMenuByClosingTime menu = bentoMenuList.getByClosingTime(workLocationCode);
            List<BentoItemByClosingTime> bentos;
            if (reservationClosingTimeFrame == ReservationClosingTimeFrame.FRAME1){
                bentos = menu.getMenu1();
            } else {
                bentos = menu.getMenu2();
            }
            for (BentoItemByClosingTime bento : bentos) {
                BentoTotalDto bentoTotalDto = createBentoTotalDto(bento, reservationDetails);
                bentoTotalDtoLst.add(bentoTotalDto);
                totalFee += bentoTotalDto.getAmount() * bentoTotalDto.getQuantity();
            }
            bentoTotalDtoLst = bentoTotalDtoLst.stream()
                    .sorted(Comparator.comparing(BentoTotalDto::getFrameNo))
                    .collect(Collectors.toList());
            result.add(new TotalOrderInfoDto(
                    item.getReservationDate().getDate(), item.getRegisterInfor().getReservationCardNo(), totalFee,
                    bentoTotalDtoLst,closedName,workInfoDtos
            ));
        }
        //}
        return result.stream().sorted(Comparator.comparing(TotalOrderInfoDto::getReservationDate)).collect(Collectors.toList());
    }

    private BentoTotalDto createBentoTotalDto(BentoItemByClosingTime bento, List<BentoReservationDetail> reservation){
        int quantity = 0;
        Iterator<BentoReservationDetail> iterator = reservation.iterator();
        while (iterator.hasNext()){
            BentoReservationDetail temp = iterator.next();
            if(temp.getFrameNo() == bento.getFrameNo()){
                quantity += temp.getBentoCount().v();
                iterator.remove();
            }
        }
        return new BentoTotalDto(bento.getUnit().v(),bento.getName().v(), quantity,
                bento.getFrameNo(),bento.getAmount1().v() + bento.getAmount2().v());
    }

    /** 6. 注文合計書を作成 */
    /** 7. 注文明細書を作成 */
    // Handle in export service

}
