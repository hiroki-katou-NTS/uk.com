package nts.uk.ctx.sys.log.infra.entity.datacorrectionlog;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT
 *
 */

@Entity
@Table(name = "SRCDT_DATA_CORRECTION_LOG")
@NoArgsConstructor
@AllArgsConstructor
public class SrcdtDataCorrectionLog extends UkJpaEntity {

	@EmbeddedId
	SrcdtDataCorrectionLogPk pk;

	@Column(name = "USER_NAME")
	@Basic(optional = false)
	String userName;

	@Column(name = "SID")
	@Basic(optional = false)
	String employeeId;

	@Column(name = "YMD_KEY")
	GeneralDate ymdKey;

	@Column(name = "YM_KEY")
	Integer ymKey;

	@Column(name = "Y_KEY")
	Integer yKey;

	@Column(name = "STRING_KEY")
	String stringKey;

	@Column(name = "CORRECTION_ATTR")
	@Basic(optional = false)
	int correctionAttr;

	@Column(name = "ITEM_NAME")
	@Basic(optional = false)
	String itemName;

	@Column(name = "RAW_VALUE_BEFORE_ID")
	String rawValueBefore;

	@Column(name = "VIEW_VALUE_BEFORE")
	String viewValueBefore;

	@Column(name = "RAW_VALUE_AFTER_ID")
	String rawValueAfter;

	@Column(name = "VIEW_VALUE_AFTER")
	String viewValueAfter;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RAW_VALUE_BEFORE_ID", insertable = false, updatable = false)
	SrcdtDecimalRawValue rawDecimalValueBefore;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RAW_VALUE_AFTER_ID", insertable = false, updatable = false)
	SrcdtDecimalRawValue rawDecimalValueAfter;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RAW_VALUE_BEFORE_ID", insertable = false, updatable = false)
	SrcdtVarcharRawValue rawVarcharValueBefore;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RAW_VALUE_AFTER_ID", insertable = false, updatable = false)
	SrcdtVarcharRawValue rawVarcharValueAfter;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RAW_VALUE_BEFORE_ID", insertable = false, updatable = false)
	SrcdtNvarcharRawValue rawNvarcharValueBefore;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RAW_VALUE_AFTER_ID", insertable = false, updatable = false)
	SrcdtNvarcharRawValue rawNvarcharValueAfter;

	@Column(name = "VALUE_DATA_TYPE")
	Integer valueType;

	@Column(name = "SHOW_ORDER")
	@Basic(optional = false)
	int showOrder;

	@Column(name = "NOTE")
	String note;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	/**
	 * convert data to read only, not to write
	 * 
	 * @return domain DataCorrectionLog
	 */
	public DataCorrectionLog toDomainToView() {
		GeneralDate ymd = this.ymdKey;
		if (this.ymdKey == null) {
			if (this.ymKey != null) {
				YearMonth ym = YearMonth.of(this.ymKey);
				ymd = GeneralDate.ymd(ym.year(), ym.month(), 1);
			} else if (this.yKey != null)
				ymd = GeneralDate.ymd(this.yKey, 1, 1);
		}
		return new DataCorrectionLog(this.pk.operationId, new UserInfo(this.pk.userId, this.employeeId, this.userName),
				TargetDataType.of(this.pk.targetDataType), TargetDataKey.of(ymd, this.stringKey),
				CorrectionAttr.of(this.correctionAttr),
				ItemInfo.createToView(this.pk.itemId, this.itemName, this.viewValueBefore, this.viewValueAfter),
				this.showOrder, this.note);
	}

}
