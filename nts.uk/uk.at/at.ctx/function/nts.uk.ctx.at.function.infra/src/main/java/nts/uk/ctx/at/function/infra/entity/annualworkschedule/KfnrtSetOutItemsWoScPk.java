package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 年間勤務表（36チェックリスト）の出力項目設定: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KfnrtSetOutItemsWoScPk implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**
	* 会社ID
	*/
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;
	
	/**
	* コード
	*/
	@Basic(optional = false)
	@Column(name = "CD")
	public String cd;
	
}
