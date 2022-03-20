package nts.uk.file.at.app.export.bento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.BentoReservationInfoForEmpDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.BentoReservedInfoDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.BentoTotalDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.DetailOrderInfoDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.OrderInfoDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.PlaceOfWorkInfoDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.TotalOrderInfoDto;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationCorrect;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoItemByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.ctx.bs.employee.pub.employee.export.PersonEmpBasicInfoPub;
import nts.uk.ctx.bs.employee.pub.employee.export.dto.PersonEmpBasicInfoDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 注文情報を作る
 */
@Stateless
public class CreateOrderInfoFileQuery {

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private WorkLocationRepository workLocationRepository;

    @Inject
    private BentoMenuHistRepository bentoMenuHistRepository;

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
    private PersonEmpBasicInfoPub personEmpBasicInfoPub;
    
    @Inject
    private ReservationSettingRepository reservationSettingRepository;
    
    @Inject
    private WorkplaceExportService workplaceExportService;

    public OrderInfoDto createOrderInfoFileQuery(DatePeriod period, List<String> workplaceId,
                                                 List<String> workLocationCodes, Optional<ReservationCorrect> totalExtractCondition,
                                                 Optional<ReservationCorrect> itemExtractCondition, Optional<Integer> frameNo, Optional<String> totalTitle,
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
        //4. 
        List<BentoMenuHistory> bentoMenuList = bentoMenuHistRepository.findByCompanyPeriod(companyId, period);
        if (CollectionUtil.isEmpty(bentoMenuList)) {
            throw new BusinessException("Msg_1640");
        }
        List<ReservationRegisterInfo> reservationRegisterInfoLst = new ArrayList<>(map.values()).stream().map(ReservationRegisterInfo::new).collect(Collectors.toList());
        //5.
        List<BentoReservation> bentoReservationLst = bentoReservationRepository.findByExtractionCondition(
        		reservationRegisterInfoLst, period, reservationClosingTimeFrame.value, 
        		totalTitle.isPresent() ? totalExtractCondition.orElse(ReservationCorrect.ALL_RESERVE) : itemExtractCondition.orElse(ReservationCorrect.ALL_RESERVE));
        //5.1
        if(frameNo.isPresent()) {
        	bentoReservationLst = bentoReservationLst.stream().map(x -> {
        		List<BentoReservationDetail> bentoReservationDetails = x.getBentoReservationDetails().stream().filter(y -> y.getFrameNo()==frameNo.get()).collect(Collectors.toList());
        		if(CollectionUtil.isEmpty(bentoReservationDetails)) {
        			return null;
        		}
        		return new BentoReservation(
        				x.getRegisterInfor(), 
        				x.getReservationDate(), 
        				x.isOrdered(), 
        				x.getWorkLocationCode(), 
        				bentoReservationDetails);
        	}).filter(x -> x!=null).collect(Collectors.toList());
        }
        if(CollectionUtil.isEmpty(bentoReservationLst)) {
        	throw new BusinessException("Msg_1617");
        }
        
        //6. 
        ReservationSetting reservationSetting = reservationSettingRepository.findByCId(companyId).orElse(null);
        Optional<String> closingName = Optional.empty();
        if(reservationSetting!=null) {
        	if(ReservationClosingTimeFrame.FRAME1.equals(reservationClosingTimeFrame)) {
                closingName = reservationSetting.getReservationRecTimeZoneLst().stream().filter(x -> x.getFrameNo()==ReservationClosingTimeFrame.FRAME1)
                		.findAny().map(x -> x.getReceptionHours().getReceptionName().v());
                		
            } else if (ReservationClosingTimeFrame.FRAME2.equals(reservationClosingTimeFrame)) {
                closingName = reservationSetting.getReservationRecTimeZoneLst().stream().filter(x -> x.getFrameNo()==ReservationClosingTimeFrame.FRAME2)
                		.findAny().map(x -> x.getReceptionHours().getReceptionName().v());
            }
        }
        Optional<WorkLocationCode> workLocationCode = CollectionUtil.isEmpty(workLocationCodes) ? Optional.empty() : Optional.of(new WorkLocationCode(workLocationCodes.get(0)));
        List<TotalOrderInfoDto> totalOrderInfoDtos = exportTotalOrderInfo(
        		companyId, 
        		totalTitle.isPresent() ? bentoReservationLst : Collections.emptyList(), 
        		placeOfWorkInfoDtos, 
        		frameNo, 
        		closingName, 
        		reservationClosingTimeFrame, 
        		workLocationCode, 
        		reservationSetting);
        List<DetailOrderInfoDto> detailOrderInfoDtos = exportDetailOrderInfo(
        		detailTitle.isPresent() ? bentoReservationLst : Collections.emptyList(), 
        		companyId, 
        		placeOfWorkInfoDtos, 
        		bentoReservationInfoForEmpDtos, 
        		frameNo, 
        		closingName);
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
        List<WorkplaceInforParam> workplaceInformations = new ArrayList<>();
        List<PersonEmpBasicInfoDto> personEmpBasicInfoDtos;
        String contractCode = AppContexts.user().contractCode();
        if (!CollectionUtil.isEmpty(workplaceIds)) {
            workplaceInformations = getWorkplaceInfoById(workplaceIds, companyId, period.start());
            sIds = getListEmpIdInWorkPlace(workplaceIds, period);
            result[0] = workplaceInformations;
        } else if (!CollectionUtil.isEmpty(workLocationCodes)) {
            workLocations = getWorkplaceInfoByCode(workLocationCodes, contractCode);
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
    private List<WorkplaceInforParam> getWorkplaceInfoById(List<String> workplaceIds, String companyId, GeneralDate date){
        return workplaceExportService.getWorkplaceInforFromWkpIds(companyId, workplaceIds, date);
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
    private List<WorkLocation> getWorkplaceInfoByCode(List<String> workLocationCodes, String contractCode){
        return workLocationRepository.findByCodes(contractCode, workLocationCodes);
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
    private List<PlaceOfWorkInfoDto> convertToPlaceOfWorkInfoDto(List<WorkplaceInforParam> workplaceInformations, List<WorkLocation> workLocations){
        List<PlaceOfWorkInfoDto> result = new ArrayList<>();
        if(CollectionUtil.isEmpty(workplaceInformations))
            for(WorkLocation item : workLocations)
                result.add(new PlaceOfWorkInfoDto(item.getWorkLocationCD().v(), item.getWorkLocationName().v()));
        else
            for(WorkplaceInforParam item : workplaceInformations)
                result.add(new PlaceOfWorkInfoDto(item.getWorkplaceCode(), item.getWorkplaceName()));

        if(result.size() > 2)
            return new ArrayList<>(Arrays.asList(result.get(0), result.get(result.size() - 1)));
        return result;
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
                    Bento bento = bentoMenuHistRepository.getBento(companyID, reservation.getReservationDate().getDate(), detail.getFrameNo());

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
                                                         Optional<WorkLocationCode> workLocationCode, ReservationSetting reservationSetting){
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
            reservationDetails = item.getBentoReservationDetails();
            Optional<BentoMenuHistory> opBentoMenuHistory = bentoMenuHistRepository.findByCompanyDate(companyId, item.getReservationDate().getDate());
            if (!opBentoMenuHistory.isPresent()) {
            	continue;
			}
            List<BentoTotalDto> bentoTotalDtoLst = new ArrayList<>();
            String roleID = AppContexts.user().roles().forAttendance();
            Map<ReservationClosingTimeFrame, Boolean> orderAtr = new HashMap<>();
            orderAtr.put(item.getReservationDate().getClosingTimeFrame(), item.isOrdered());
            BentoMenuByClosingTime menu = BentoMenuByClosingTime.createForCurrent(
            		roleID, 
            		reservationSetting, 
            		opBentoMenuHistory.get().getMenu(), 
            		orderAtr, 
            		item.getReservationDate().getDate());
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
        Optional<BentoReservationDetail> opBentoReservationDetail = reservation.stream().filter(x -> x.getFrameNo()==bento.getFrameNo()).findAny();
        if(opBentoReservationDetail.isPresent()) {
        	quantity = opBentoReservationDetail.get().getBentoCount().v();
        }
        return new BentoTotalDto(bento.getUnit().v(),bento.getName().v(), quantity,
                bento.getFrameNo(),bento.getAmount1().v());
    }

    /** 6. 注文合計書を作成 */
    /** 7. 注文明細書を作成 */
    // Handle in export service

}
