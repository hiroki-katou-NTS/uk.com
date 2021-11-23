package nts.uk.ctx.at.record.app.query.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ContentChangeDeadlineDay;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.ctx.at.record.dom.workrecord.role.Role;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 * UKDesign.UniversalK.オフィス.KMR_予約.KMR001_予約の前準備.B:予約の設定.予約の設定を取得する
 *
 */
@Stateless
public class ReservationSettingQuery {
    
    @Inject
    public ReservationSettingRepository reservationSettingRepository;
    
    @Inject
    public GetRoleNameQuery getRoleNameQuery;

    public ReservationQueryOuput getReservationSetting() {
        ReservationSetting setting = null;
        
        // 1.会社IDによって予約設定を取得する(会社ID＝ログイン会社ID) : Object＜予約設定＞
        Optional<ReservationSetting> settingOpt = reservationSettingRepository.findByCId(AppContexts.user().companyId());
        
        List<Role> roles = new ArrayList<Role>();
        if (settingOpt.isPresent()) {
            setting = settingOpt.get();
            
            // 2.get(取得したList<ロールID＞): List＜ロール名称＞
            roles = getRoleNameQuery.getRoleName(settingOpt.get().getCorrectionContent().getCanModifiLst());
        }
        
        List<EnumConstant> contentChangeDeadlineDayEnum = new ArrayList<>();
        for(ContentChangeDeadlineDay item : ContentChangeDeadlineDay.values()) {
        	contentChangeDeadlineDayEnum.add(new EnumConstant(item.value, item.toString(), item.name));
        }
        
        // 3.return()
        return new ReservationQueryOuput(
                setting != null ? ReservationSettingDto.fromDomain(setting) : null, 
                roles,
                contentChangeDeadlineDayEnum);
    }
}
