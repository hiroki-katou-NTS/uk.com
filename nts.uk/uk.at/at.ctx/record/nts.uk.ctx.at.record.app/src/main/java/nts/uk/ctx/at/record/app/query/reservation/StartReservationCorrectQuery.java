package nts.uk.ctx.at.record.app.query.reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.record.app.command.reservation.bento.RegisterErrorMessage;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationCorrect;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationCorrectNotOrder;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationOrderMngAtr;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author anhnm
 * UKDesign.UniversalK.オフィス.KMR_予約.KMR003_予約の修正.B:予約の修正画面.メニュー別OCD.予約の修正画面を起動する
 *
 */
@Stateless
public class StartReservationCorrectQuery {
    
    @Inject
    private ReservationSettingRepository reservationSettingRepository;

    @Inject
    private BentoMenuHistRepository bentoMenuHistoryRepository;
    
    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;
    
    @Inject
    private GetStampCardQuery getStampCardQuery;
    
    @Inject
    private BentoReservationRepository bentoReservationRepository;

    /**
     * @param correctionDate 注文日
     * @param reservationDate 予約日
     * @param frameNo 枠NO
     * @param extractCondition 抽出条件
     * @param employeeIds List＜社員ID＞
     * @return
     */
    public StartReservationCorrectOutput startReservationCorrection(GeneralDate correctionDate, GeneralDate reservationDate, int frameNo, int extractCondition, List<String> employeeIds) {
        List<RegisterErrorMessage> errorList = new ArrayList<RegisterErrorMessage>();
        
        // 1: get(会社ID=ログイン会社ID): 予約設定
        ReservationSetting setting = reservationSettingRepository.findByCId(AppContexts.user().companyId()).get();
        
        // 1.1: 修正可能な権限があるか(ログイン者のロールID): boolean
        boolean roleFlag = setting.getCorrectionContent().roleCanModifi(AppContexts.user().roles().forAttendance());
        
        // 2: get(期間．開始日＜＝注文日＜＝期間．終了日): 弁当メニュー履歴
        Optional<BentoMenuHistory> yokakuHistOpt = bentoMenuHistoryRepository.findByCompanyDate(AppContexts.user().companyId(), correctionDate);
        
        // 2.1: get(受付時間帯NO = Input．枠NO): 弁当メニュー
        List<Bento> menus = new ArrayList<Bento>();
        if (yokakuHistOpt.isPresent()) {
            menus = yokakuHistOpt.get().getMenu().stream()
                    .filter(menu -> menu.getReceptionTimezoneNo().value == frameNo)
                    .collect(Collectors.toList());
        }
        String correctionDateParam = TextResource.localize("KMR003_53", Arrays.asList(correctionDate.toString("yyyy/MM/dd"), correctionDate.toString("E")));
        Optional<ReservationRecTimeZone> reservationFrameNo = setting.getReservationRecTimeZoneLst().stream().filter(x -> x.getFrameNo().value == frameNo).findFirst();
        String receptionTimeNameParam = "";
        if (reservationFrameNo.isPresent()) {
            receptionTimeNameParam = reservationFrameNo.get().getReceptionHours().getReceptionName() + " " +
                    TextResource.localize("KMR003_52", 
                            Arrays.asList(
                                    new TimeWithDayAttr(setting.getReservationRecTimeZoneLst().stream().filter(x -> x.getFrameNo().value == frameNo).findFirst().get().getReceptionHours().getStartTime().v()).getInDayTimeWithFormat(), 
                                    new TimeWithDayAttr(setting.getReservationRecTimeZoneLst().stream().filter(x -> x.getFrameNo().value == frameNo).findFirst().get().getReceptionHours().getEndTime().v()).getInDayTimeWithFormat()));
            if (menus.isEmpty()) {
                throw new BusinessException("Msg_2255", correctionDateParam, receptionTimeNameParam);
            }
        } else {
            return new StartReservationCorrectOutput();
        }
        
        // 3: 社員情報を取得(List＜社員ID＞　＝　Input．List＜社員ID＞)
        List<PersonEmpBasicInfoImport> listPersonEmp = this.empEmployeeAdapter.getPerEmpBasicInfo(employeeIds);
        
        // 4: 社員IDから打刻カードを取得(List＜社員ID＞=Input．List＜社員ID＞)
        Map<String, StampNumber> stampCards = getStampCardQuery.getStampNumberBy(employeeIds);
        
        List<ReservationRegisterInfo> listCardNo = stampCards.values().stream().map(card -> new ReservationRegisterInfo(card.v())).collect(Collectors.toList());
        // 5: 予約の修正の抽出条件から弁当予約を取得する(打刻カード番号, 年月日, int, 予約修正抽出条件, 勤務場所コード)
        //      (打刻カード一覧=取得した打刻カード番号, 対象日=Input.発注日, 受付時間帯NO=Input.枠NO, 予約修正抽出条件=Input.抽出条件, 勤務場所コード=Empty)
        List<BentoReservation> bentoReservations = new ArrayList<BentoReservation>();
        if (!listCardNo.isEmpty()) {
            bentoReservations = bentoReservationRepository.findByExtractionCondition(
                    listCardNo, 
                    new DatePeriod(correctionDate, correctionDate), 
                    frameNo, 
                    EnumAdaptor.valueOf(extractCondition, ReservationCorrect.class));
        }
        if (bentoReservations.isEmpty()) {
            if (setting.getCorrectionContent().getOrderMngAtr().equals(ReservationOrderMngAtr.CANNOT_MANAGE)) {
                errorList.add(new RegisterErrorMessage("Msg_2256", Arrays.asList(
                        EnumAdaptor.valueOf(extractCondition, ReservationCorrectNotOrder.class).name, 
                        correctionDateParam, 
                        receptionTimeNameParam)));
            } else {
                errorList.add(new RegisterErrorMessage("Msg_2256", Arrays.asList(
                        EnumAdaptor.valueOf(extractCondition, ReservationCorrect.class).name, 
                        correctionDateParam, 
                        receptionTimeNameParam)));
            }
        }
        
        List<BentoReservationWithFlag> bentoReservationWithFlags = new ArrayList<BentoReservationWithFlag>();
        bentoReservations.forEach(s-> {
            // 5.1: 社員は予約内容を修正できるか(char, 年月日, 年月日, 時刻時分, int, boolean, 予約受付時間帯)
            //      (ロールID=ログイン者の就業ロールID, 注文日=弁当予約．注文日, 予約日=システム日付, 予約時刻=システム日付時分, 枠NO=弁当予約.枠NO, 発注区分=弁当予約．発注区分,予約受付時間帯=弁当予約.枠NO)
            boolean flag = setting.getCorrectionContent().canEmployeeChangeReservation(
                    AppContexts.user().roles().forAttendance(), 
                    reservationDate, 
                    ClockHourMinute.now(), 
                    frameNo, 
                    correctionDate, 
                    s.isOrdered(), 
                    setting.getReservationRecTimeZoneLst().stream().filter(x -> x.getFrameNo().value == frameNo).findFirst().get());
            bentoReservationWithFlags.add(new BentoReservationWithFlag(BentoReservationDto.fromDomain(s), flag));
        });
        
        Map<String, BentoReservationWithFlag> bentoReservationMap = new HashMap<String, BentoReservationWithFlag>();
        
        bentoReservationWithFlags.forEach(reservation -> {
                stampCards.forEach((k, v) -> {
                    if (v.v().equals(reservation.getBentoReservation().getReservationCardNo())) {
                        bentoReservationMap.put(k, reservation);
                    }
                });
        });
        
        return new StartReservationCorrectOutput(
                menus.stream().map(x -> BentoDto.fromDomain(x)).collect(Collectors.toList()), 
                listPersonEmp.stream().map(x -> PersonEmpBasicInfoImportDto.fromDomain(x)).collect(Collectors.toList()), 
                bentoReservationMap, 
                stampCards.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().v())), 
                errorList, 
                roleFlag);
    }
}
