/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.address;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * The Class XxxxxAddress.
 */
@Data
@Entity
@Table(name = "XXXXX_ADDRESS")
public class XxxxxAddress implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/** The zip code. */
	@Column(name = "ZIPCODE")
	private String zipCode;

	/** The prefecture. */
	@Column(name = "PREFECTURE")
	private String prefecture;

	/** The city. */
	@Column(name = "CITY")
	private String city;

	/** The town. */
	@Column(name = "TOWN")
	private String town;

}
