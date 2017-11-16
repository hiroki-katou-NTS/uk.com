/**
 * 6:06:58 PM Aug 24, 2017
 */
package nts.uk.ctx.at.record.infra.entity.operationrule.businesstype;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
@Setter
@Getter
@Embeddable
public class KrcmtBusinessTypeSyainPK implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	//社員ID
	@Column(name = "SID")
	private String sId;
	
	//開始日
	@Column(name = "START_YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate startYmd;
	
	//終了日
	@Column(name = "END_YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate endYmd;
	
	//勤務種別コード
	@Column(name = "BUSINESS_TYPE_CD")
	private String businessTypeCode;
	
}
