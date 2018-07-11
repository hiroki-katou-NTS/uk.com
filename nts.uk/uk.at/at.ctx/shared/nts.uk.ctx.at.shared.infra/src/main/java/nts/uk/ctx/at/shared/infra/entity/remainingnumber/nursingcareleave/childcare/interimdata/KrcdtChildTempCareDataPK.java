package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.childcare.interimdata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcdtChildTempCareDataPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Column(name="SID")
	public String sid;

	@Column(name="YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate ymd;

}