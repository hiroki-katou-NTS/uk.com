package nts.uk.ctx.basic.infra.entity.organization.position;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.TableEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="CATMT_AUTH")
public class CatmtAuth extends TableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**主キー*/
	@EmbeddedId
    public CatmtAuthPK catmtAuthPk;

	/**権限名称*/
	@Basic(optional = true)
	@Column(name = "AUTH_NAME")
	public String authName;
	
	/**社員参照範囲区分*/
	@Basic(optional = false)
	@Column(name = "EMP_SCOPE_ATR")
	public int empScopeAtr;
	
	/**担当者区分*/
	@Basic(optional = false)
	@Column(name = "IN_CHARGE_ATR")
	public int inChargeAtr;
	
	/**メモ*/
	@Basic(optional = true)
	@Column(name = "MEMO")
	public String memo;
}
