package nts.uk.ctx.exio.infra.entity.input.revise;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRawNumber;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;
import nts.uk.ctx.exio.dom.input.revise.ReviseItem;
import nts.uk.ctx.exio.dom.input.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;
import nts.uk.ctx.exio.dom.input.revise.type.codeconvert.CodeConvertCode;
import nts.uk.ctx.exio.dom.input.revise.type.date.DateRevise;
import nts.uk.ctx.exio.dom.input.revise.type.date.ExternalImportDateFormat;
import nts.uk.ctx.exio.dom.input.revise.type.integer.IntegerRevise;
import nts.uk.ctx.exio.dom.input.revise.type.real.DecimalDigitNumber;
import nts.uk.ctx.exio.dom.input.revise.type.real.RealRevise;
import nts.uk.ctx.exio.dom.input.revise.type.string.FixedLength;
import nts.uk.ctx.exio.dom.input.revise.type.string.FixedLengthReviseMethod;
import nts.uk.ctx.exio.dom.input.revise.type.string.StringRevise;
import nts.uk.ctx.exio.dom.input.revise.type.time.HourlySegment;
import nts.uk.ctx.exio.dom.input.revise.type.time.TimeBase60Delimiter;
import nts.uk.ctx.exio.dom.input.revise.type.time.TimeBaseNumber;
import nts.uk.ctx.exio.dom.input.revise.type.time.TimeRevise;
import nts.uk.ctx.exio.dom.input.revise.type.time.TimeRounding;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *  項目の編集
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "XIMMT_REVISE_ITEM")
public class XimmtReviseItem extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private XimmtReviseItemPK pk;
	
	/*  */
	@Column(name = "ITEM_TYPE")
	private int itemType;
	
	/*  */
	@Column(name = "IS_SPECIFY_RANGE")
	private Integer useSpecifyRange;
	
	/*  */
	@Column(name = "SPECIFY_RANGE_START")
	private Integer startRaw;
	
	/*  */
	@Column(name = "SPECIFY_RANGE_END")
	private Integer endRaw;
	
	/*  */
	@Column(name = "IS_DECIMALIZATION")
	private Integer isDecimalization;
	
	/*  */
	@Column(name = "DECIMAL_DEGIT_NUMBER")
	private Integer decimalLength;
	
	/*  */
	@Column(name = "IS_FIXED_LENGTH")
	private Integer useFixedLength;
	
	/*  */
	@Column(name = "FIXED_LENGTH")
	private Integer fixLength;
	
	/*  */
	@Column(name = "FIXED_LENGTH_METHOD")
	private Integer reviseMethod;
	
	/*  */
	@Column(name = "DATE_FORMAT")
	private Integer dateFormat;
	
	/*  */
	@Column(name = "HOURLY_SEGMENT")
	private Integer hourly;
	
	/*  */
	@Column(name = "TIME_BASE_NUMBER")
	private Integer baseNumber;
	
	/*  */
	@Column(name = "TIME_BASE60_DELIMITER")
	private Integer delimiter;
	
	/*  */
	@Column(name = "TIME_ROUNDING")
	private Integer rounding;
	
	/*  */
	@Column(name = "CODE_CONVERT_CODE")
	private String codeConvertCode;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static final JpaEntityMapper<XimmtReviseItem> MAPPER = new JpaEntityMapper<>(XimmtReviseItem.class);
	
	public ReviseItem toDomain() {
		ReviseValue reviseValue = getReviseValue();
		return new ReviseItem(
				pk.getCompanyId(), 
				new ExternalImportCode(pk.getSettingCode()), 
				pk.getItemNo(), 
				EnumAdaptor.valueOf(itemType, ItemType.class), 
				reviseValue, 
				Optional.ofNullable(codeConvertCode.isEmpty() ? null : new CodeConvertCode(codeConvertCode)));
	}
	
	private ReviseValue getReviseValue() {
		switch(EnumAdaptor.valueOf(itemType, ItemType.class)) {
			case STRING:
				return new StringRevise(
						useSpecifyRange == 1, 
						Optional.ofNullable(useSpecifyRange == 1 ? createRangeOfValue() : null), 
						useFixedLength == 1, 
						Optional.ofNullable(useFixedLength == 1 ? new FixedLength(
								new ExternalImportRawNumber(fixLength), 
								EnumAdaptor.valueOf(reviseMethod, FixedLengthReviseMethod.class)) : null));
			case INT:
				return new IntegerRevise(
						useSpecifyRange == 1, 
						Optional.ofNullable(useSpecifyRange == 1 ? createRangeOfValue() : null));
			case REAL:
				return new RealRevise(
						useSpecifyRange == 1, 
						Optional.ofNullable(useSpecifyRange == 1 ? createRangeOfValue() : null),
						BooleanUtils.toBoolean(isDecimalization), 
						Optional.ofNullable(isDecimalization == 1 ? new DecimalDigitNumber(decimalLength) : null));
			case DATE:
				return new DateRevise(
						EnumAdaptor.valueOf(dateFormat, ExternalImportDateFormat.class));
			case TIME_POINT:
			case TIME_DURATION:
				return new TimeRevise(
						useSpecifyRange == 1, 
						Optional.ofNullable(useSpecifyRange == 1 ? createRangeOfValue() : null), 
						EnumAdaptor.valueOf(hourly, HourlySegment.class),
						EnumAdaptor.valueOf(baseNumber, TimeBaseNumber.class),
						Optional.ofNullable(delimiter != null ? EnumAdaptor.valueOf(delimiter, TimeBase60Delimiter.class) : null), 
						EnumAdaptor.valueOf(rounding, TimeRounding.class));
			default:
				throw new RuntimeException("項目型に対する実装が存在しません。:" + EnumAdaptor.valueOf(itemType, ItemType.class));
		}
	}
	
	private RangeOfValue createRangeOfValue() {
		return new RangeOfValue(
				new ExternalImportRawNumber(startRaw), 
				new ExternalImportRawNumber(endRaw));
	}
}
