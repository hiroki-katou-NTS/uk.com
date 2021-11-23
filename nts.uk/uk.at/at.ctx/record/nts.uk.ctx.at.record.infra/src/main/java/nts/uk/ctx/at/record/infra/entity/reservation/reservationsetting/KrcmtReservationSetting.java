package nts.uk.ctx.at.record.infra.entity.reservation.reservationsetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.AchievementMethod;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.Achievements;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ContentChangeDeadline;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ContentChangeDeadlineDay;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationOrderMngAtr;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCMT_PREPARATION")
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtReservationSetting extends ContractUkJpaEntity {

    @Id
    @Column(name = "CID")
    public String companyID;

    @Column(name = "OPE_CAT")
    public int operationDistinction;
    
    @Column(name = "MON_RES")
    public int monthlyResults;
    
    @Column(name = "ORDERED_CHA")
    public int contentChangeDeadline;
    
    @Column(name = "ORDERED_CHA_DAY")
    public Integer contentChangeDeadlineDay;
    
    @Column(name = "ORDERED_MNG_ATR")
    public int orderedMngAtr;
    
    @Column(name = "RECTIME2_ATR")
    public int rectime2Atr;
    
    @OneToMany(targetEntity = KrcmtReservationRecTime.class, mappedBy = "krcmtReservationSetting", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "KRCMT_PREPARATION_RECTIME")
	public List<KrcmtReservationRecTime> krcmtReservationRecTimeLst;
    
    @OneToMany(targetEntity = KrcmtPreparationRoles.class, mappedBy = "krcmtReservationSetting", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "KRCMT_PREPARATION_ROLES")
	public List<KrcmtPreparationRoles> krcmtPreparationRolesLst;
    
    @Override
    protected Object getKey() {
        return companyID;
    }
    
    public static ReservationSetting toDomain(KrcmtReservationSetting krcmtReservationSetting) {
    	return new ReservationSetting(
    			krcmtReservationSetting.companyID, 
    			EnumAdaptor.valueOf(krcmtReservationSetting.operationDistinction, OperationDistinction.class), 
    			new CorrectionContent(
    					EnumAdaptor.valueOf(krcmtReservationSetting.contentChangeDeadline, ContentChangeDeadline.class), 
    					krcmtReservationSetting.contentChangeDeadlineDay==null?null:EnumAdaptor.valueOf(krcmtReservationSetting.contentChangeDeadlineDay, ContentChangeDeadlineDay.class), 
    					EnumAdaptor.valueOf(krcmtReservationSetting.orderedMngAtr, ReservationOrderMngAtr.class),
    					krcmtReservationSetting.krcmtPreparationRolesLst.stream().map(x -> x.pk.roleID).collect(Collectors.toList())), 
    			new Achievements(EnumAdaptor.valueOf(krcmtReservationSetting.monthlyResults, AchievementMethod.class)),
    			krcmtReservationSetting.krcmtReservationRecTimeLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()),
    			krcmtReservationSetting.rectime2Atr==0?false:true);
    }
    
    public static KrcmtReservationSetting fromDomain(ReservationSetting reservationSetting) {
    	return new KrcmtReservationSetting(
    			reservationSetting.getCompanyId(), 
    			reservationSetting.getOperationDistinction().value, 
    			reservationSetting.getAchievements().getMonthlyResults().value, 
    			reservationSetting.getCorrectionContent().getContentChangeDeadline().value, 
    			reservationSetting.getCorrectionContent().getContentChangeDeadlineDay()==null?null:reservationSetting.getCorrectionContent().getContentChangeDeadlineDay().value, 
    			reservationSetting.getCorrectionContent().getOrderMngAtr().value, 
    			reservationSetting.isReceptionTimeZone2Use()?1:0, 
    			reservationSetting.getReservationRecTimeZoneLst().stream().map(x -> KrcmtReservationRecTime.fromDomain(x)).collect(Collectors.toList()),
    			reservationSetting.getCorrectionContent().getCanModifiLst().stream().map(x -> KrcmtPreparationRoles.fromDomain(reservationSetting.getCompanyId(), x)).collect(Collectors.toList()));
    }
}
