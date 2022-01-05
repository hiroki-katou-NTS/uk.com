package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationTime;
import nts.uk.ctx.at.record.dom.reservation.bento.rules.BentoReservationTimeName;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.AchievementMethod;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.Achievements;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ContentChangeDeadline;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ContentChangeDeadlineDay;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationOrderMngAtr;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.shr.com.context.AppContexts;

@Value
public class BentoReserveSettingCommand {
	
	private boolean isRegister;

	/**
     * 予約の運用区別
     */
    // operation Distinction.
    private int operationDistinction;

    /**
     * 予約修正内容
     */
    // correction Content.
    private CorrectionContentCmd correctionContent;

    /**
     * 実績集計.月別実績の集計
     */
    // achievements.
    private int monthlyResults;
    
    /**
     * 予約受付時間帯
     */
    private List<ReservationRecTimeZoneCmd> reservationRecTimeZoneLst;
    
    /**
     * 受付時間帯２使用区分
     */
    private boolean receptionTimeZone2Use;
    
    public ReservationSetting toDomain() {
    	return new ReservationSetting(
    			AppContexts.user().companyId(), 
    			EnumAdaptor.valueOf(operationDistinction, OperationDistinction.class), 
    			correctionContent.toDomain(), 
    			new Achievements(EnumAdaptor.valueOf(monthlyResults, AchievementMethod.class)), 
    			CollectionUtil.isEmpty(reservationRecTimeZoneLst) ? Collections.emptyList() : reservationRecTimeZoneLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
    			receptionTimeZone2Use);
    }
    
    @Value
    public static class CorrectionContentCmd {
    	/**
         * 予約済みの内容変更期限
         */
        // Content change deadline.
        private int contentChangeDeadline;

        /**
         * 予約済みの内容変更期限日数
         */
        // content change deadline days.
        private int contentChangeDeadlineDay;

        /**
         * 発注機能管理区分
         */
        // Ordered data.
        private int orderMngAtr;
        
        /**
         * 修正不可の際でも修正可能な権限一覧
         */
        private List<String> canModifiLst;
        
        public CorrectionContent toDomain() {
        	return new CorrectionContent(
        			EnumAdaptor.valueOf(contentChangeDeadline, ContentChangeDeadline.class), 
        			contentChangeDeadlineDay==0 ? null : EnumAdaptor.valueOf(contentChangeDeadlineDay, ContentChangeDeadlineDay.class), 
        			EnumAdaptor.valueOf(orderMngAtr, ReservationOrderMngAtr.class), 
        			canModifiLst);
        }
    }
    
    @Value
    public static class ReservationRecTimeZoneCmd {
    	/**
    	 * 	受付時間帯
    	 */
    	private ReservationRecTimeCmd receptionHours;
    	
    	/**
    	 * 	枠NO
    	 */
    	private int frameNo;
    	
    	public ReservationRecTimeZone toDomain() {
    		return new ReservationRecTimeZone(
    				receptionHours.toDomain(), 
    				EnumAdaptor.valueOf(frameNo, ReservationClosingTimeFrame.class));
    	}
    	
    	@Value
    	public static class ReservationRecTimeCmd {
    		/**
    		 * 	受付名称
    		 */
    		private String receptionName;
    		
    		/**
    		 * 	開始時刻
    		 */
    		private int startTime;
    		
    		/**
    		 * 	終了時刻
    		 */
    		private int endTime;
    		
    		public ReservationRecTime toDomain() {
    			BentoReservationTimeName receptionNameDom = new BentoReservationTimeName(receptionName);
				BentoReservationTime startTimeDom = new BentoReservationTime(startTime);
				BentoReservationTime endTimeDom = new BentoReservationTime(endTime);
				receptionNameDom.validate();
				startTimeDom.validate();
				endTimeDom.validate();
    			return new ReservationRecTime(
    					receptionNameDom, 
    					startTimeDom, 
    					endTimeDom);
    		}
    	}
    }

}
