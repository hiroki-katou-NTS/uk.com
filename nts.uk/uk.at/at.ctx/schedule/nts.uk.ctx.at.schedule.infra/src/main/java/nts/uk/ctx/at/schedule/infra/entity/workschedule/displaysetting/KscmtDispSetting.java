package nts.uk.ctx.at.schedule.infra.entity.workschedule.displaysetting;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.InitDispMonth;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.WorkScheDisplaySetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * 勤務予定の表示設定 - KSCMT_DISP_SETTING
 * UKDesign.データベース.ER図.就業.勤務予定.表示設定
 * @author HieuLt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_DISP_SETTING")
public class KscmtDispSetting extends ContractUkJpaEntity{


	@EmbeddedId
	public KscmtDispSettingPK pk;
	
	/** "初期表示の月 " **/								
	@Column(name = "INIT_DISP_MONTH")
	public int initDispMonth;
	
	/** 初期表示期間の終了日.日 **/
	@Column(name = "ENDDAY")
	public int endDay;
	
	/** 初期表示期間の終了日.日 **/
	@Column(name = "IS_LAST_DAY")
	public boolean isLastDay;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static KscmtDispSetting toEntity( WorkScheDisplaySetting workScheDisplaySetting) {
		String CID = AppContexts.user().companyId();
		/*return new KscmtDispSetting(
				new KscmtDispSettingPK(CID),
				workScheDisplaySetting.getInitDispMonth().value,
				workScheDisplaySetting.getEndDay().;*/
		return null;
	}
	public WorkScheDisplaySetting toDomain (){
		return new  WorkScheDisplaySetting(pk.cid,
				EnumAdaptor.valueOf(this.initDispMonth, InitDispMonth.class),
				new OneMonth(new DateInMonth(endDay, isLastDay)));
	}
}
