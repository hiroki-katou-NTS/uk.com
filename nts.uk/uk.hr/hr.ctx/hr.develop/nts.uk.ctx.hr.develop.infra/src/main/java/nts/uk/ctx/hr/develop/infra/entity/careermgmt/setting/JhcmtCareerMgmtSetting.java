package nts.uk.ctx.hr.develop.infra.entity.careermgmt.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.careermgmt.setting.CareerMgmtSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHCMT_CAREERMGMT_SETTING")
public class JhcmtCareerMgmtSetting extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CID")
	public String cId;

	@Column(name = "CAREER_PATH_FLG")
	public Integer careerPathFlg;

	@Column(name = "CAREER_PLAN_FLG")
	public Integer careerPlanFlg;

	@Column(name = "MAIL_FLG")
	public Integer mailFlg;

	@Column(name = "INFORMATION_FLG")
	public Integer informationFlg;

	@Column(name = "MAX_CLASS_LEVEL")
	public Integer maxClassLevel;
	
	@Override
	public Object getKey() {
		return cId;
	}

	public CareerMgmtSetting toDomain() {
		return CareerMgmtSetting.createFromJavaType(
				this.cId, 
				this.careerPathFlg == 1, 
				this.careerPlanFlg == 1,
				this.mailFlg == 1,
				this.informationFlg == 1,
				this.maxClassLevel);
	}
}
