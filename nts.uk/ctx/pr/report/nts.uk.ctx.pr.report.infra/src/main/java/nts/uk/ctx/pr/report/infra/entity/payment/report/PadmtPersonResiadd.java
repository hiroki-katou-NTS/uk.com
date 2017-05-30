/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.report;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PadmtPersonResiadd.
 */
@Getter
@Setter
@Entity
@Table(name = "PADMT_PERSON_RESIADD")
public class PadmtPersonResiadd implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The padmt person resiadd PK. */
    @EmbeddedId
    private PadmtPersonResiaddPK padmtPersonResiaddPK;
    
    /** The ins date. */
    @Column(name = "INS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;
    
    /** The ins ccd. */
    @Column(name = "INS_CCD")
    private String insCcd;
    
    /** The ins scd. */
    @Column(name = "INS_SCD")
    private String insScd;
    
    /** The ins pg. */
    @Column(name = "INS_PG")
    private String insPg;
    
    /** The upd date. */
    @Column(name = "UPD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    
    /** The upd ccd. */
    @Column(name = "UPD_CCD")
    private String updCcd;
    
    /** The upd scd. */
    @Column(name = "UPD_SCD")
    private String updScd;
    
    /** The upd pg. */
    @Column(name = "UPD_PG")
    private String updPg;
    
    /** The exclus ver. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    
    /** The inv scd. */
    @Column(name = "INV_SCD")
    private String invScd;
    
    /** The str D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date strD;
    
    /** The end D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endD;
    
    /** The exp D. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXP_D")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expD;
    
    /** The postal. */
    @Column(name = "POSTAL")
    private String postal;
    
    /** The nationality. */
    @Column(name = "NATIONALITY")
    private String nationality;
    
    /** The address 1. */
    @Column(name = "ADDRESS1")
    private String address1;
    
    /** The address 2. */
    @Column(name = "ADDRESS2")
    private String address2;
    
    /** The kn address 1. */
    @Column(name = "KN_ADDRESS1")
    private String knAddress1;
    
    /** The kn address 2. */
    @Column(name = "KN_ADDRESS2")
    private String knAddress2;
    
    /** The tel no. */
    @Column(name = "TEL_NO")
    private String telNo;
    
    /** The house condition. */
    @Column(name = "HOUSE_CONDITION")
    private String houseCondition;
    
    /** The rent mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "RENT_MNY")
    private int rentMny;
    
    /** The map file. */
    @Column(name = "MAP_FILE")
    private String mapFile;

    /**
     * Instantiates a new padmt person resiadd.
     */
    public PadmtPersonResiadd() {
    }

    /**
     * Instantiates a new padmt person resiadd.
     *
     * @param padmtPersonResiaddPK the padmt person resiadd PK
     */
    public PadmtPersonResiadd(PadmtPersonResiaddPK padmtPersonResiaddPK) {
        this.padmtPersonResiaddPK = padmtPersonResiaddPK;
    }

    /**
     * Instantiates a new padmt person resiadd.
     *
     * @param padmtPersonResiaddPK the padmt person resiadd PK
     * @param exclusVer the exclus ver
     * @param strD the str D
     * @param endD the end D
     * @param expD the exp D
     * @param rentMny the rent mny
     */
	public PadmtPersonResiadd(PadmtPersonResiaddPK padmtPersonResiaddPK, int exclusVer, Date strD,
		Date endD, Date expD, int rentMny) {
        this.padmtPersonResiaddPK = padmtPersonResiaddPK;
        this.exclusVer = exclusVer;
        this.strD = strD;
        this.endD = endD;
        this.expD = expD;
        this.rentMny = rentMny;
    }

    /**
     * Instantiates a new padmt person resiadd.
     *
     * @param pid the pid
     * @param histId the hist id
     */
    public PadmtPersonResiadd(String pid, String histId) {
        this.padmtPersonResiaddPK = new PadmtPersonResiaddPK(pid, histId);
    }

    /**
     * Gets the padmt person resiadd PK.
     *
     * @return the padmt person resiadd PK
     */
    public PadmtPersonResiaddPK getPadmtPersonResiaddPK() {
        return padmtPersonResiaddPK;
    }

    /**
     * Sets the padmt person resiadd PK.
     *
     * @param padmtPersonResiaddPK the new padmt person resiadd PK
     */
    public void setPadmtPersonResiaddPK(PadmtPersonResiaddPK padmtPersonResiaddPK) {
        this.padmtPersonResiaddPK = padmtPersonResiaddPK;
    }

    /**
     * Gets the ins date.
     *
     * @return the ins date
     */
    public Date getInsDate() {
        return insDate;
    }

    /**
     * Sets the ins date.
     *
     * @param insDate the new ins date
     */
    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    /**
     * Gets the ins ccd.
     *
     * @return the ins ccd
     */
    public String getInsCcd() {
        return insCcd;
    }

    /**
     * Sets the ins ccd.
     *
     * @param insCcd the new ins ccd
     */
    public void setInsCcd(String insCcd) {
        this.insCcd = insCcd;
    }

    /**
     * Gets the ins scd.
     *
     * @return the ins scd
     */
    public String getInsScd() {
        return insScd;
    }

    /**
     * Sets the ins scd.
     *
     * @param insScd the new ins scd
     */
    public void setInsScd(String insScd) {
        this.insScd = insScd;
    }

    /**
     * Gets the ins pg.
     *
     * @return the ins pg
     */
    public String getInsPg() {
        return insPg;
    }

    /**
     * Sets the ins pg.
     *
     * @param insPg the new ins pg
     */
    public void setInsPg(String insPg) {
        this.insPg = insPg;
    }

    /**
     * Gets the upd date.
     *
     * @return the upd date
     */
    public Date getUpdDate() {
        return updDate;
    }

    /**
     * Sets the upd date.
     *
     * @param updDate the new upd date
     */
    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    /**
     * Gets the upd ccd.
     *
     * @return the upd ccd
     */
    public String getUpdCcd() {
        return updCcd;
    }

    /**
     * Sets the upd ccd.
     *
     * @param updCcd the new upd ccd
     */
    public void setUpdCcd(String updCcd) {
        this.updCcd = updCcd;
    }

    /**
     * Gets the upd scd.
     *
     * @return the upd scd
     */
    public String getUpdScd() {
        return updScd;
    }

    /**
     * Sets the upd scd.
     *
     * @param updScd the new upd scd
     */
    public void setUpdScd(String updScd) {
        this.updScd = updScd;
    }

    /**
     * Gets the upd pg.
     *
     * @return the upd pg
     */
    public String getUpdPg() {
        return updPg;
    }

    /**
     * Sets the upd pg.
     *
     * @param updPg the new upd pg
     */
    public void setUpdPg(String updPg) {
        this.updPg = updPg;
    }

    /**
     * Gets the exclus ver.
     *
     * @return the exclus ver
     */
    public int getExclusVer() {
        return exclusVer;
    }

    /**
     * Sets the exclus ver.
     *
     * @param exclusVer the new exclus ver
     */
    public void setExclusVer(int exclusVer) {
        this.exclusVer = exclusVer;
    }

    /**
     * Gets the inv scd.
     *
     * @return the inv scd
     */
    public String getInvScd() {
        return invScd;
    }

    /**
     * Sets the inv scd.
     *
     * @param invScd the new inv scd
     */
    public void setInvScd(String invScd) {
        this.invScd = invScd;
    }

    /**
     * Gets the str D.
     *
     * @return the str D
     */
    public Date getStrD() {
        return strD;
    }

    /**
     * Sets the str D.
     *
     * @param strD the new str D
     */
    public void setStrD(Date strD) {
        this.strD = strD;
    }

    /**
     * Gets the end D.
     *
     * @return the end D
     */
    public Date getEndD() {
        return endD;
    }

    /**
     * Sets the end D.
     *
     * @param endD the new end D
     */
    public void setEndD(Date endD) {
        this.endD = endD;
    }

    /**
     * Gets the exp D.
     *
     * @return the exp D
     */
    public Date getExpD() {
        return expD;
    }

    /**
     * Sets the exp D.
     *
     * @param expD the new exp D
     */
    public void setExpD(Date expD) {
        this.expD = expD;
    }

    /**
     * Gets the postal.
     *
     * @return the postal
     */
    public String getPostal() {
        return postal;
    }

    /**
     * Sets the postal.
     *
     * @param postal the new postal
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     * Gets the nationality.
     *
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the nationality.
     *
     * @param nationality the new nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * Gets the address 1.
     *
     * @return the address 1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the address 1.
     *
     * @param address1 the new address 1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Gets the address 2.
     *
     * @return the address 2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the address 2.
     *
     * @param address2 the new address 2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Gets the kn address 1.
     *
     * @return the kn address 1
     */
    public String getKnAddress1() {
        return knAddress1;
    }

    /**
     * Sets the kn address 1.
     *
     * @param knAddress1 the new kn address 1
     */
    public void setKnAddress1(String knAddress1) {
        this.knAddress1 = knAddress1;
    }

    /**
     * Gets the kn address 2.
     *
     * @return the kn address 2
     */
    public String getKnAddress2() {
        return knAddress2;
    }

    /**
     * Sets the kn address 2.
     *
     * @param knAddress2 the new kn address 2
     */
    public void setKnAddress2(String knAddress2) {
        this.knAddress2 = knAddress2;
    }

    /**
     * Gets the tel no.
     *
     * @return the tel no
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * Sets the tel no.
     *
     * @param telNo the new tel no
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    /**
     * Gets the house condition.
     *
     * @return the house condition
     */
    public String getHouseCondition() {
        return houseCondition;
    }

    /**
     * Sets the house condition.
     *
     * @param houseCondition the new house condition
     */
    public void setHouseCondition(String houseCondition) {
        this.houseCondition = houseCondition;
    }

    /**
     * Gets the rent mny.
     *
     * @return the rent mny
     */
    public int getRentMny() {
        return rentMny;
    }

    /**
     * Sets the rent mny.
     *
     * @param rentMny the new rent mny
     */
    public void setRentMny(int rentMny) {
        this.rentMny = rentMny;
    }

    /**
     * Gets the map file.
     *
     * @return the map file
     */
    public String getMapFile() {
        return mapFile;
    }

    /**
     * Sets the map file.
     *
     * @param mapFile the new map file
     */
    public void setMapFile(String mapFile) {
        this.mapFile = mapFile;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (padmtPersonResiaddPK != null ? padmtPersonResiaddPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PadmtPersonResiadd)) {
            return false;
        }
		PadmtPersonResiadd other = (PadmtPersonResiadd) object;
		if ((this.padmtPersonResiaddPK == null && other.padmtPersonResiaddPK != null)
			|| (this.padmtPersonResiaddPK != null
				&& !this.padmtPersonResiaddPK.equals(other.padmtPersonResiaddPK))) {
			return false;
		}
		return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.PadmtPersonResiadd[ padmtPersonResiaddPK=" + padmtPersonResiaddPK + " ]";
    }
    
}

