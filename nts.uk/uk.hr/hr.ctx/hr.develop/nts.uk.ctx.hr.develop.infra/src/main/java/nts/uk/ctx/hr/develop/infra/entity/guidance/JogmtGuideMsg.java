package nts.uk.ctx.hr.develop.infra.entity.guidance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.develop.dom.guidance.GuideMsg;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JOGMT_GUIDE_MSG")
public class JogmtGuideMsg extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String cId;

	@Id
	@Column(name = "GUIDE_MSG_ID")
	public String guideMsgId;

	@Column(name = "CATEGORY_CODE")
	public String categoryCode;
	
	@Column(name = "CATEGORY_NAME")
	public String categoryName;
	
	@Column(name = "EVENT_CODE")
	public String eventCode;
	
	@Column(name = "EVENT_NAME")
	public String eventName;
	
	@Column(name = "PROGRAM_ID")
	public String programId;
	
	@Column(name = "PROGRAM_NAME")
	public String programName;
	
	@Column(name = "SCREEN_ID")
	public String screenId;
	
	@Column(name = "SCREEN_NAME")
	public String screenName;
	
	@Column(name = "USAGE_FLG_BY_SCREEN")
	public Integer usageFlgByScreen;
	
	@Column(name = "GUIDE_MSG")
	public String guideMsg;
	
	@Column(name = "SCREEN_URL", nullable = true)
	public String screenUrl;
	
	@ManyToOne
	@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
	public JogmtGuideDispSetting guideMsgList;

	@Override
	public Object getKey() {
		return guideMsgId;
	}

	public GuideMsg toDomain() {
		return GuideMsg.createFromJavaType(
				this.guideMsgId,
				this.categoryCode, 
				this.categoryName, 
				this.eventCode, 
				this.eventName, 
				this.programId, 
				this.programName, 
				this.screenId, 
				this.screenName, 
				this.usageFlgByScreen == 1, 
				this.guideMsg, 
				this.screenUrl);
	}

	public JogmtGuideMsg(String cId, GuideMsg domain) {
		super();
		this.cId = cId;
		this.guideMsgId = IdentifierUtil.randomUniqueId();
		this.categoryCode = domain.getCategoryCode();
		this.categoryName = domain.getCategoryName();
		this.eventCode = domain.getEventCode();
		this.eventName = domain.getEventName();
		this.programId = domain.getProgramId();
		this.programName = domain.getProgramName();
		this.screenId = domain.getScreenId();
		this.screenName = domain.getScreenName();
		this.usageFlgByScreen = domain.isUsageFlgByScreen()?1:0;
		this.guideMsg = domain.getGuideMsg();
		this.screenUrl = domain.getScreenPath().orElse(null);
	}
	
}
