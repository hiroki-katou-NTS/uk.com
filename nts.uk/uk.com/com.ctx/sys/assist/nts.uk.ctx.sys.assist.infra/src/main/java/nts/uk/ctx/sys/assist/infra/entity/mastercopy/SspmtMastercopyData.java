package nts.uk.ctx.sys.assist.infra.entity.mastercopy;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the SSPMT_MASTERCOPY_DATA database table.
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SSPMT_MASTERCOPY_DATA")
public class SspmtMastercopyData implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private SspmtMastercopyDataPK id;
}