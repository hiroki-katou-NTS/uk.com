package nts.uk.ctx.basic.infra.entity.organization.payclassification;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QMNMT_PAYCLASS")
public class QmnmtPayClass implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public QmnmtPayClassPK qmnmtPayClassPK;
	

	@Basic(optional = false)
	@Column(name = "MEMO")
	public String memo;

	
	@Basic(optional = false)
	@Column(name = "PAYCLASS_NAME")
	public String payClassificationName;


	public QmnmtPayClassPK getQmnmtPayClassPK() {
		return qmnmtPayClassPK;
	}


	public void setQmnmtPayClassPK(QmnmtPayClassPK qmnmtPayClassPK) {
		this.qmnmtPayClassPK = qmnmtPayClassPK;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public String getPayClassificationName() {
		return payClassificationName;
	}


	public void setPayClassName(String payClassificationName) {
		this.payClassificationName = payClassificationName;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
}
