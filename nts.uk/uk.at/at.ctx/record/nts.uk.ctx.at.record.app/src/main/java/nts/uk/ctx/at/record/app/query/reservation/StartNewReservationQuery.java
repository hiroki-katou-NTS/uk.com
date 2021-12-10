package nts.uk.ctx.at.record.app.query.reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * @author anhnm
 * UKDesign.UniversalK.オフィス.KMR_予約.KMR003_予約の修正.C:予約の新規注文.メニュー別OCD.予約の新規注文を起動する
 *
 */
@Stateless
public class StartNewReservationQuery {
    
    @Inject
    private IBentoMenuHistoryRepository bentoMenuHistoryRepository;
    
    @Inject
    private BentoMenuRepository bentoMenuRepository;
    
    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;
    
    @Inject
    private GetStampCardQuery getStampCardQuery;
    
    @Inject
    private BentoReservationRepository bentoReservationRepository;
    
    /**
     * 予約の新規注文を起動する
     * @param correctionDate 注文日
     * @param reservationDate 予約日
     * @param frameNo 枠NO
     * @param employeeIds List＜社員ID＞
     */
    public StartReservationCorrectOutput startNewReservation(GeneralDate correctionDate, int frameNo, List<String> employeeIds) {
        // 1: get(会社ID＝ログイン会社ID,期間，開始日＜＝注文日＜＝期間．終了日)
        Optional<BentoMenuHistory> yokakuHistOpt = bentoMenuHistoryRepository.findByCompanyId(AppContexts.user().companyId());
        
        List<Bento> menus = new ArrayList<Bento>();
        if (yokakuHistOpt.isPresent()) {
            Optional<DateHistoryItem> historyItem = yokakuHistOpt.get().getHistoryItems().stream()
                    .filter(x -> x.contains(correctionDate)).findFirst();
            
            if (historyItem.isPresent()) {
                // 1.1: Filter(履歴ID　＝＝　取得した履歴ID)
                BentoMenu bentoMenu = bentoMenuRepository.getBentoMenuByHistId(AppContexts.user().companyId(), historyItem.get().identifier());
                
                // 1.2: Filter(受付時間帯NO　＝＝　Input．受付時間帯NO)
                if (bentoMenu != null) {
                    menus = bentoMenu.getMenu().stream().filter(menu -> menu.getReceptionTimezoneNo().value == frameNo).collect(Collectors.toList());
                }
            }
        }
        
        // 2: 社員情報を取得(List＜社員ID＞　＝　Input．社員一覧)
        List<PersonEmpBasicInfoImport> listPersonEmp = this.empEmployeeAdapter.getPerEmpBasicInfo(employeeIds);
        
        // 3: 社員IDから打刻カード番号を取得(社員ID＝Input．社員一覧)
        Map<String, StampNumber> stampCards = getStampCardQuery.getStampNumberBy(employeeIds);
        
        // 4: 全て予約内容を取得(打刻カード一覧=取得した打刻カード番号,対象日=Input．注文日,  受付時間帯NO＝Input．受付時間帯NO,勤務場所コード＝Empty): 弁当予約
        List<BentoReservation> bentoReservations = bentoReservationRepository.getAllReservationDetail(
                stampCards.values().stream().map(card -> new ReservationRegisterInfo(card.v())).collect(Collectors.toList()), 
                new DatePeriod(correctionDate, correctionDate), 
                EnumAdaptor.valueOf(frameNo, ReservationClosingTimeFrame.class), 
                new ArrayList<WorkLocationCode>());

        // 4.1: 個人情報を消する(社員IDの打刻カード番号がない　OR　弁当予約．打刻カード番号＝個人情報．社員IDの打刻カード番号)
        List<PersonEmpBasicInfoImport> listEmpOutput = listPersonEmp.stream().filter(x -> {
                                                        return stampCards.containsKey(x.getEmployeeId()) 
                                                                && !bentoReservations.stream()
                                                                .filter(y -> y.getRegisterInfor().getReservationCardNo().equals(stampCards.get(x.getEmployeeId()).v()))
                                                                .findFirst()
                                                                .isPresent();
                                                    }).collect(Collectors.toList());
        
//        List<BentoReservationWithFlag> bentoReservationWithFlags = bentoReservations.stream()
//                .map(x -> new BentoReservationWithFlag(BentoReservationDto.fromDomain(x), true))
//                .collect(Collectors.toList());
//        
//        Map<String, BentoReservationWithFlag> bentoReservationMap = new HashMap<String, BentoReservationWithFlag>();
//        
//        bentoReservationWithFlags.forEach(reservation -> {
//                stampCards.forEach((k, v) -> {
//                    if (v.v().equals(reservation.getBentoReservation().getReservationCardNo())) {
//                        bentoReservationMap.put(k, reservation);
//                    }
//                });
//        });
        
        return new StartReservationCorrectOutput(
                menus.stream().map(x -> BentoDto.fromDomain(x)).collect(Collectors.toList()), 
                listEmpOutput.stream().map(x -> PersonEmpBasicInfoImportDto.fromDomain(x)).collect(Collectors.toList()), 
                new HashMap<String, BentoReservationWithFlag>());
    }

}
