package nts.uk.ctx.basic.infra.entity.organization.classification;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CMNMT_CLASS")
@IdClass(CmnmtClassPK.class)
@AllArgsConstructor
@NoArgsConstructor
public class CmnmtClass implements Serializable {

	private static final long serialVersionUID = 4161327404158928689L;

	@EmbeddedId
	private CmnmtClassPK cmnmtClassPK;

	@Column(name = "CLSNAME")
	private String name;

	@Column(name = "CLS_OUT_CD")
	private String outCode;

	@Column(name = "MEMO")
	private String memo;

	public CmnmtClassPK getCmnmtClassPK() {
		return cmnmtClassPK;
	}

	public void setCmnmtClassPK(CmnmtClassPK cmnmtClassPK) {
		this.cmnmtClassPK = cmnmtClassPK;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
