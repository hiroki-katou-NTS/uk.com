package nts.uk.ctx.at.request.infra.entity.setting.workplace;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.ApplicationDetailSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.ApplicationUseSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.AtWorkAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.DisplayBreakTime;
import nts.uk.ctx.at.request.dom.setting.workplace.InstructionUseSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.Memo;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplace;
import nts.uk.ctx.at.request.dom.setting.workplace.SelectionFlg;
import nts.uk.ctx.at.request.dom.setting.workplace.SettingFlg;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 職場別申請承認設定
 * @author Doan Duy Hung
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_WP_APP_CONFIG")
public class KrqstWpAppConfig extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrqstWpAppConfigPK krqstWpAppConfigPK;
	
	/**
	 * 申請時の承認者の選択
	 */
	@Column(name = "SELECT_OF_APPROVERS_FLG")
	public int selectOfApproversFlg;
	
	@OneToMany(targetEntity=KrqstWpAppConfigDetail.class, cascade = CascadeType.ALL, mappedBy = "krqstWpAppConfig", orphanRemoval = true)
	@JoinTable(name = "KRQST_COM_APP_CF_DETAIL")
	public List<KrqstWpAppConfigDetail> krqstWpAppConfigDetails;
	
	@Override
	protected Object getKey() {
		return krqstWpAppConfigPK;
	}
	
	public RequestOfEachWorkplace toDomain(){
		return new RequestOfEachWorkplace(
				this.krqstWpAppConfigPK.companyId, 
				this.krqstWpAppConfigPK.workplaceId,
				EnumAdaptor.valueOf(this.selectOfApproversFlg, SelectionFlg.class), 
				this.krqstWpAppConfigDetails.stream().map(x -> 
					new ApprovalFunctionSetting(
							SettingFlg.toEnum(x.prerequisiteForpauseFlg), 
							new InstructionUseSetting(
									UseAtr.toEnum(x.instructionAtr), 
									new Memo(x.instructionMemo), 
									UseAtr.toEnum(x.instructionUseAtr)), 
							SettingFlg.toEnum(x.holidayTimeAppCalFlg), 
							SettingFlg.toEnum(x.otAppSettingFlg), 
							SettingFlg.toEnum(x.lateOrLeaveAppCancelFlg), 
							SettingFlg.toEnum(x.lateOrLeaveAppSettingFlg), 
							new ApplicationUseSetting(
									new Memo(x.memo), 
									UseAtr.toEnum(x.useAtr), 
									EnumAdaptor.valueOf(x.krqstWpAppConfigDetailPK.appType, ApplicationType.class)), 
							Optional.of(new ApplicationDetailSetting(
									x.breakInputFieldDisFlg == 1? true : false, 
									x.breakTimeDisFlg == 1? true : false, 
									EnumAdaptor.valueOf(x.atworkTimeBeginDisFlg, AtWorkAtr.class), 
									x.goOutTimeBeginDisFlg == 1 ? true : false, 
									x.requiredInstructionFlg == 1 ? true : false, 
									UseAtr.toEnum(x.timeCalUseAtr), 
									UseAtr.toEnum(x.timeInputUseAtr), 
									EnumAdaptor.valueOf(x.timeEndDispFlg, DisplayBreakTime.class)))))
				.collect(Collectors.toList()));
	}

}
