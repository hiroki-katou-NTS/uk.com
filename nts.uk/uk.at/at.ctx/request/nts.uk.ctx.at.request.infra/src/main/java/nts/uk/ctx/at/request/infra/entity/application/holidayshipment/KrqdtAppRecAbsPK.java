package nts.uk.ctx.at.request.infra.entity.application.holidayshipment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable

/**
 * @author ThanhPV
 */
public class KrqdtAppRecAbsPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String cId;
	
	@Column(name = "APP_ID")
	public String appID;

}
