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
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.FetchingPosition;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.ExternalImportCodeConvert;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.date.DateRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.date.ExternalImportDateFormat;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.integer.IntegerRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.real.DecimalDigitNumber;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.real.RealRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.Padding;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.PaddingMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string.StringRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.HourlySegment;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.TimeBase60Delimiter;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.TimeBaseNumber;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.TimeRevise;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time.TimeRounding;
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
	@Column(name = "USE_FETCHING_POSITION")
	private Integer useFetchingPosition;
	
	/*  */
	@Column(name = "FETCHING_START")
	private Integer fetchingStart;
	
	/*  */
	@Column(name = "FETCHING_LENGTH")
	private Integer fetchingLength;
	
	/*  */
	@Column(name = "IS_DECIMALIZATION")
	private Integer isDecimalization;
	
	/*  */
	@Column(name = "DECIMAL_DEGIT_NUMBER")
	private Integer decimalLength;
	
	/*  */
	@Column(name = "USE_PADDING")
	private Integer usePadding;
	
	/*  */
	@Column(name = "PADDING_LENGTH")
	private Integer paddingLength;
	
	/*  */
	@Column(name = "PADDING_METHOD")
	private Integer paddingMethod;
	
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
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static final JpaEntityMapper<XimmtReviseItem> MAPPER = new JpaEntityMapper<>(XimmtReviseItem.class);
	
	public ReviseItem toDomain(Optional<ExternalImportCodeConvert> codeConvert) {
		return new ReviseItem(
				pk.getCompanyId(),
				new ExternalImportCode(pk.getSettingCode()),
				pk.getItemNo(),
				getReviseValue(codeConvert));
	}
	
	private ReviseValue getReviseValue(Optional<ExternalImportCodeConvert> codeConvert) {
		switch(EnumAdaptor.valueOf(itemType, ItemType.class)) {
			case STRING:
				return new StringRevise(
						useFetchingPosition == 1,
						Optional.ofNullable(useFetchingPosition == 1 ? createRangeOfValue() : null),
						usePadding == 1,
						Optional.ofNullable(usePadding == 1 ? new Padding(
								new ExternalImportRowNumber(paddingLength),
								EnumAdaptor.valueOf(paddingMethod, PaddingMethod.class)) : null),
						codeConvert);
			case INT:
				return new IntegerRevise(
						useFetchingPosition == 1,
						Optional.ofNullable(useFetchingPosition == 1 ? createRangeOfValue() : null), 
						codeConvert);
			case REAL:
				return new RealRevise(
						useFetchingPosition == 1,
						Optional.ofNullable(useFetchingPosition == 1 ? createRangeOfValue() : null),
						BooleanUtils.toBoolean(isDecimalization),
						Optional.ofNullable(isDecimalization == 1 ? new DecimalDigitNumber(decimalLength) : null));
			case DATE:
				return new DateRevise(
						EnumAdaptor.valueOf(dateFormat, ExternalImportDateFormat.class));
			case TIME_POINT:
			case TIME_DURATION:
				return new TimeRevise(
						useFetchingPosition == 1,
						Optional.ofNullable(useFetchingPosition == 1 ? createRangeOfValue() : null),
						EnumAdaptor.valueOf(hourly, HourlySegment.class),
						EnumAdaptor.valueOf(baseNumber, TimeBaseNumber.class),
						Optional.ofNullable(delimiter != null ? EnumAdaptor.valueOf(delimiter, TimeBase60Delimiter.class) : null),
						EnumAdaptor.valueOf(rounding, TimeRounding.class));
			default:
				throw new RuntimeException("項目型に対する実装が存在しません。:" + EnumAdaptor.valueOf(itemType, ItemType.class));
		}
	}
	
	private FetchingPosition createRangeOfValue() {
		return new FetchingPosition(
				new ExternalImportRowNumber(fetchingStart),
				new ExternalImportRowNumber(fetchingLength));
	}
}
