/**
 * 4:35:00 PM Aug 28, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtSyainDpErListPK  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ERROR_CODE")
	public String errorCode;
	
	@Column(name = "SID")
	public String employeeId;
	
	@Column(name = "PROCESSING_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate processingDate;

	@Column(name = "CID")
	public String companyID;
}
