package nts.uk.ctx.sys.log.infra.entity.datacorrectionlog;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT -
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

	@Column(name = "CORRECTION_ATR")
	@Basic(optional = false)
	int correctionAttr;

	@Column(name = "ITEM_NAME")
	@Basic(optional = false)
	String itemName;
	
//	@Column(name = "RAW_VALUE_BEFORE")
//	@Basic(optional = true)
//	String rawValueBefore;

	@Column(name = "VIEW_VALUE_BEFORE")
	@Basic(optional = true)
	String viewValueBefore;

//	@Column(name = "RAW_VALUE_AFTER")
//	@Basic(optional = true)
//	String rawValueAfter;

	@Column(name = "VIEW_VALUE_AFTER")
	@Basic(optional = true)
	String viewValueAfter;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({
		@JoinColumn(name = "operationId", referencedColumnName = "operationId", insertable = false, updatable = false),
		@JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false),
		@JoinColumn(name = "targetDataType", referencedColumnName = "targetDataType", insertable = false, updatable = false),
		@JoinColumn(name = "stringKey", referencedColumnName = "stringKey", insertable = false, updatable = false)
	})
	SrcdtDecimalRawValue rawDecimalValueBefore;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({
		@JoinColumn(name = "operationId", referencedColumnName = "operationId", insertable = false, updatable = false),
		@JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false),
		@JoinColumn(name = "targetDataType", referencedColumnName = "targetDataType", insertable = false, updatable = false),
		@JoinColumn(name = "stringKey", referencedColumnName = "stringKey", insertable = false, updatable = false)
	})
	SrcdtDecimalRawValue rawDecimalValueAfter;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({
		@JoinColumn(name = "operationId", referencedColumnName = "operationId", insertable = false, updatable = false),
		@JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false),
		@JoinColumn(name = "targetDataType", referencedColumnName = "targetDataType", insertable = false, updatable = false),
		@JoinColumn(name = "stringKey", referencedColumnName = "stringKey", insertable = false, updatable = false)
	})
	SrcdtVarcharRawValue rawVarcharValueBefore;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({
		@JoinColumn(name = "operationId", referencedColumnName = "operationId", insertable = false, updatable = false),
		@JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false),
		@JoinColumn(name = "targetDataType", referencedColumnName = "targetDataType", insertable = false, updatable = false),
		@JoinColumn(name = "stringKey", referencedColumnName = "stringKey", insertable = false, updatable = false)
	})
	SrcdtVarcharRawValue rawVarcharValueAfter;

	@Column(name = "VALUE_DATA_TYPE")
	@Basic(optional = true)
	Integer valueType;

	@Column(name = "SHOW_ORDER")
	@Basic(optional = false)
	int showOrder;

	@Column(name = "NOTE")
	@Basic(optional = true)
	String note;
	
	

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public DataCorrectionLog toDomain() {
//		return new DataCorrectionLog(pk.operationId, new UserInfo(pk.userId, userName, employeeId),
//				TargetDataType.of(targetDataType),
//				TargetDataKey.of(ymdKey, stringKey),
//				CorrectionAttr.of(correctionAttr),
//				ItemInfo.create(pk.itemName, DataValueAttribute.of(valueType), rawValueBefore, rawValueAfter), showOrder,
//				note);
		return null;
	}

	public static SrcdtDataCorrectionLog fromDomain(DataCorrectionLog domain) {
//		return new SrcdtDataCorrectionLog(
//				new SrcdtDataCorrectionLogPk(domain.getOperationId(), domain.getTargetUser().getUserId(),
//						domain.getCorrectedItem().getName()),
//				domain.getTargetUser().getUserName(), domain.getTargetUser().getEmployeeId(),
//				domain.getTargetDataType().value, domain.getTargetDataKey().getYmdKey().get(),
//				domain.getTargetDataKey().getYmKey().get().v(), domain.getTargetDataKey().getYKey().get().getValue(),
//				domain.getTargetDataKey().getStringKey().get(), domain.getCorrectionAttr().value,
//				domain.getCorrectedItem().getValueBefore().getRawValue().toString(),
//				domain.getCorrectedItem().getValueBefore().getViewValue(),
//				domain.getCorrectedItem().getValueAfter().getRawValue().toString(),
//				domain.getCorrectedItem().getValueAfter().getViewValue(), domain.getShowOrder(),
//				domain.getCorrectedItem().getValueAfter().getRawValue().getType().value, domain.getRemark());
		return null;
	}

}
