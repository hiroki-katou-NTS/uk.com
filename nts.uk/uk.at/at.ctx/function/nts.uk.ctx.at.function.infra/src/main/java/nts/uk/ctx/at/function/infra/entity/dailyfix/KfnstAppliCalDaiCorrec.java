package nts.uk.ctx.at.function.infra.entity.dailyfix;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Setter
@Getter
@Entity
@Table(name = "KFNST_APPLICALL_DAICORREC")
@AllArgsConstructor
@NoArgsConstructor
public class KfnstAppliCalDaiCorrec extends UkJpaEntity implements Serializable{
	@Id
	@Column(name = "CID")
	public String companyId;
	
	@Id
	@Column(name = "APPLICATION_TYPE")
	public int appType;

	@Override
	protected Object getKey() {
		return null;
	}
}
