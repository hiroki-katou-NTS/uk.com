package nts.uk.ctx.hr.develop.infra.entity.guidance;

import java.io.Serializable;
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
import nts.uk.ctx.hr.develop.dom.guidance.Guidance;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JOGMT_GUIDE_DISP_SETTING")
public class JogmtGuideDispSetting extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CID")
	public String cId;

	@Column(name = "USAGE_FLG_COMMON")
	public Integer usageFlgCommon;

	@Column(name = "GUIDE_MSG_AREA_ROW")
	public Integer guideMsgAreaRow;
	
	@Column(name = "GUIDE_MSG_MAX_NUM")
	public Integer guideMsgMaxNum;
	
	@JoinTable(name = "JOGMT_GUIDE_MSG")
	@OneToMany(mappedBy = "guideMsgList", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<JogmtGuideMsg> listGuideMsg;

	@Override
	public Object getKey() {
		return cId;
	}

	public Guidance toDomain() {
		return Guidance.createFromJavaType(
				this.cId, 
				this.usageFlgCommon == 1, 
				this.guideMsgAreaRow, 
				this.guideMsgMaxNum, 
				this.listGuideMsg.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
	}

	public JogmtGuideDispSetting(Guidance domain) {
		super();
		this.cId = domain.getCompanyId();
		this.usageFlgCommon = domain.isUsageFlgCommon()?1:0;
		this.guideMsgAreaRow = domain.getGuideMsgAreaRow().v();
		this.guideMsgMaxNum = domain.getGuideMsgMaxNum().v();
		this.listGuideMsg = domain.getGuideMsg().stream().map(c -> new JogmtGuideMsg(domain.getCompanyId(), c)).collect(Collectors.toList());
	}
	
}
