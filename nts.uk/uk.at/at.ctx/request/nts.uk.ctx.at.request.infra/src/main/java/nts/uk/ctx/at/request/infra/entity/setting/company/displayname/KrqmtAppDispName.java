package nts.uk.ctx.at.request.infra.entity.setting.company.displayname;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BCMMT_COMPANY")
public class KrqmtAppDispName extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KrqmtAppDispNamePK krqmtAppDispNamePK;
	/** 表示名 */
	@Column(name = "DISP_NAME")
	public String dispName;
	@Override
	protected Object getKey() {
		return krqmtAppDispNamePK;
	}
}
