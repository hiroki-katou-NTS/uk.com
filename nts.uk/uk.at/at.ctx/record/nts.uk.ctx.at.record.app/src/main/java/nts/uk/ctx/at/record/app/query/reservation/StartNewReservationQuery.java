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
import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.command.reservation.bento.RegisterErrorMessage;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 * UKDesign.UniversalK.オフィス.KMR_予約.KMR003_予約の修正.C:予約の新規注文.メニュー別OCD.予約の新規注文を起動する
 *
 */
@Stateless
public class StartNewReservationQuery {
    
    @Inject
    private BentoMenuHistRepository bentoMenuHistoryRepository;
    
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
    public StartReservationCorrectOutput startNewReservation(GeneralDate correctionDate, int frameNo, String frameName, List<String> employeeIds) {
        List<RegisterErrorMessage> listErrors = new ArrayList<RegisterErrorMessage>();
        
        // 1: get(会社ID＝ログイン会社ID,期間，開始日＜＝注文日＜＝期間．終了日)
        Optional<BentoMenuHistory> yokakuHistOpt = bentoMenuHistoryRepository.findByCompanyDate(AppContexts.user().companyId(), correctionDate);
        
        List<Bento> menus = new ArrayList<Bento>();
        if (yokakuHistOpt.isPresent()) {
            menus = yokakuHistOpt.get().getMenu().stream()
                    .filter(menu -> menu.getReceptionTimezoneNo().value == frameNo)
                    .collect(Collectors.toList());
        }
        
        // 2: 社員情報を取得(List＜社員ID＞　＝　Input．社員一覧)
        List<PersonEmpBasicInfoImport> listPersonEmp = this.empEmployeeAdapter.getPerEmpBasicInfo(employeeIds);
        
        // 3: 社員IDから打刻カード番号を取得(社員ID＝Input．社員一覧)
        Map<String, StampNumber> stampCards = getStampCardQuery.getStampNumberBy(employeeIds);
        
        employeeIds.forEach(x -> {
            if (!stampCards.containsKey(x)) {
                PersonEmpBasicInfoImport empInfo = listPersonEmp.stream().filter(y -> y.getEmployeeId().equals(x)).findFirst().get();
                listErrors.add(new RegisterErrorMessage("Msg_2259", Arrays.asList(empInfo.getEmployeeCode() + " " + empInfo.getBusinessName())));
            }
        });
        
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
        
        if (listEmpOutput.isEmpty()) {
            throw new BusinessException("Msg_2282", correctionDate.toString(), frameName);
        }
        
        return new StartReservationCorrectOutput(
                menus.stream().map(x -> BentoDto.fromDomain(x)).collect(Collectors.toList()), 
                listEmpOutput.stream().map(x -> PersonEmpBasicInfoImportDto.fromDomain(x)).collect(Collectors.toList()), 
                new HashMap<String, BentoReservationWithFlag>(), 
                stampCards.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().v())), 
                listErrors, 
                true);
    }

}
